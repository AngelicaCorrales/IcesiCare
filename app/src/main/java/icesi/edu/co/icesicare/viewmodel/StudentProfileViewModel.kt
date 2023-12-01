package icesi.edu.co.icesicare.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import icesi.edu.co.icesicare.model.entity.Student
import icesi.edu.co.icesicare.model.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

class StudentProfileViewModel : ViewModel() {

    val studentLV = MutableLiveData<Student>()
    private var studentRepository: StudentRepository = StudentRepository()

    fun getStudent(studentId : String){

        viewModelScope.launch(Dispatchers.IO) {
            val student = studentRepository.getStudent(studentId)

            withContext(Dispatchers.Main){
                studentLV.value = student
            }
        }
    }

    fun uploadImage(uri: Uri, studentId: String){
        viewModelScope.launch(Dispatchers.IO) {

            val imageId = UUID.randomUUID().toString()

            try {
                Firebase.storage.reference
                    .child("users")
                    .child("profileImages")
                    .child(imageId)
                    .putFile(uri).await()

                Firebase.firestore.collection("students")
                    .document(studentId)
                    .update("profileImageId",imageId).await()

            }catch (ex:Exception){
                Log.e(">>>", ex.message.toString())
            }
        }
    }
}