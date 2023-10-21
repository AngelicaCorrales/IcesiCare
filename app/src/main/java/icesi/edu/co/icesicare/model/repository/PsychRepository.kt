package icesi.edu.co.icesicare.model.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import icesi.edu.co.icesicare.model.entity.Appointment
import icesi.edu.co.icesicare.model.entity.Psychologist
import kotlinx.coroutines.tasks.await

object PsychRepository {

    private val psychs = arrayListOf<Psychologist>()
    val psychsLiveData = MutableLiveData<ArrayList<Psychologist>>(psychs)

    suspend fun fetchAllPsychs(){
        psychs.clear()
        val result = Firebase.firestore
            .collection("psychologists")
            .get().await()

        result.documents.forEach{document ->
            val psych = document.toObject(Psychologist::class.java)

            psych?.let {
                Log.e("DEV",psych.name)
                psych.profileImageId?.let { it1 -> Log.e("DEV", it1) }

                if(psych.profileImageId != null && psych.profileImageId != "" ){
                    val url= Firebase.storage.reference
                        .child("users")
                        .child("profileImages")
                        .child(psych.profileImageId!!).downloadUrl.await()
                    Log.e(">>>",url.toString())
                    psych.profileImageURL = url.toString()
                }
                psychs.add(psych)
            }
        }
        psychsLiveData.postValue(psychs)
    }
}