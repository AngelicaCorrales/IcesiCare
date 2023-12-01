package icesi.edu.co.icesicare.model.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import icesi.edu.co.icesicare.model.entity.Psychologist
import icesi.edu.co.icesicare.model.entity.Student
import kotlinx.coroutines.tasks.await

object PsychRepository {

    private val psychs = arrayListOf<Psychologist>()
    private val filteredPsychs = arrayListOf<Psychologist>()
    val psychsLiveData = MutableLiveData<ArrayList<Psychologist>>(psychs)
    val singlePsychLiveData = MutableLiveData<Psychologist?>()

    fun filterPsychsByName(name:String){
        filteredPsychs.clear()
        for(p in psychs){
            if(p.name.contains(name,true))
                filteredPsychs.add(p)
        }
        psychsLiveData.postValue(filteredPsychs)
    }

    fun clearFilter(){
        psychsLiveData.postValue(psychs)
        filteredPsychs.clear()
    }

    suspend fun fetchAllPsychs(){
        psychs.clear()
        val result = Firebase.firestore
            .collection("psychologists")
            .get().await()

        result.documents.forEach{document ->
            val psych = document.toObject(Psychologist::class.java)

            psych?.let {
                if(psych.profileImageId != null && psych.profileImageId != "" ){
                    val url= Firebase.storage.reference
                        .child("users")
                        .child("profileImages")
                        .child(psych.profileImageId!!).downloadUrl.await()
                    psych.profileImageURL = url.toString()
                }
                psychs.add(psych)
            }
        }
        psychsLiveData.postValue(psychs)
    }

    suspend fun getPsychologist(psychologistId : String) : Psychologist? {

        try {
            val docStudent = Firebase.firestore.collection("psychologists")
                .document(psychologistId).get().await()

            val psychologist = docStudent.toObject(Psychologist::class.java)

            psychologist?.let {
                if (it.profileImageId != ""){
                    val url = Firebase.storage.reference
                        .child("users")
                        .child("profileImages")
                        .child(it.profileImageId.toString()).downloadUrl.await()

                    it.profileImageURL = url.toString()
                }
                return it
            }
            return null

        }catch (e : Exception){
            Log.e("PsychRepository","Error while fetching psychologist.")
            return null
        }
    }

    suspend fun fetchOnePsy(psyId: String): Psychologist? {
        try {
            val documentSnapshot = Firebase.firestore
                .collection("psychologists")
                .document(psyId)
                .get()
                .await()

            val psy = documentSnapshot.toObject(Psychologist::class.java)

            psy?.let {

                if (psy.profileImageId != null && psy.profileImageId != "") {
                    val url = Firebase.storage.reference
                        .child("users")
                        .child("profileImages")
                        .child(psy.profileImageId!!).downloadUrl.await()
                    psy.profileImageURL = url.toString()
                }
            }

            singlePsychLiveData.postValue(psy)
            return psy
        } catch (e: Exception) {
            Log.e("PsychRepository", "Error fetching psychologist", e)
            return null
        }
    }

    suspend fun updatePsy(psyId: String, updatedPsy: Psychologist) {
        try {
            val currentPsy = getPsychologist(psyId)

            val oldImageId = currentPsy?.profileImageId

            Firebase.firestore.collection("psychologists")
                .document(psyId)
                .set(updatedPsy)
                .await()

            if (!oldImageId.isNullOrEmpty() && oldImageId != updatedPsy.profileImageId) {
                try {
                    val imageRef = Firebase.storage.reference.child("users").child("profileImages").child(oldImageId)
                    imageRef.delete().await()
                } catch (e: Exception) {
                    Log.e("PsychRepository", "Error deleting old profile image", e)
                }
            }

        } catch (e: Exception) {
            Log.e("PsychRepository", "Error saving psychologist changes", e)
        }
    }

    suspend fun getPsychologistsPendingForApproval() : MutableList<Psychologist>{
        val result = Firebase.firestore
            .collection("psychologists")
            .whereEqualTo("pendingApproval",true).get().await()

        val psychsList = arrayListOf<Psychologist>()

        result.documents.forEach{document ->
            val psych = document.toObject(Psychologist::class.java)

            psych?.let {
                if(psych.profileImageId != null && psych.profileImageId != "" ){
                    val url= Firebase.storage.reference
                        .child("users")
                        .child("profileImages")
                        .child(psych.profileImageId!!).downloadUrl.await()
                    psych.profileImageURL = url.toString()
                }
                psychsList.add(psych)
            }
        }
        return psychsList
    }

    suspend fun updatePsychStatus(isAccepted: Boolean, psychId: String){
        val psych = getPsychologist(psychId)

        psych?.let {
            psych.pendingApproval = false
            psych.approved = isAccepted

            updatePsy(psychId,psych)
        }
    }
}