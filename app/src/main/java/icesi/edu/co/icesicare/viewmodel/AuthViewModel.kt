package icesi.edu.co.icesicare.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import icesi.edu.co.icesicare.model.entity.AuthState
import icesi.edu.co.icesicare.model.entity.ErrorMessage
import icesi.edu.co.icesicare.model.entity.Psychologist
import icesi.edu.co.icesicare.model.entity.Student
import icesi.edu.co.icesicare.model.repository.PsychRepository
import icesi.edu.co.icesicare.model.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

class AuthViewModel : ViewModel() {

    val authStateLV = MutableLiveData<AuthState>()
    val errorLV = MutableLiveData<ErrorMessage>()
    val user = MutableLiveData<Any?>()
    val isAdmin = MutableLiveData<Boolean?>()

    private var studentRepository: StudentRepository = StudentRepository()

    fun signupPsych(fullName:String, genre:String,email: String, password: String, confirmPass: String) {

        viewModelScope.launch(Dispatchers.IO) {

            try {

                val result = Firebase.auth.createUserWithEmailAndPassword(email, password).await()
                val psychologistId = Firebase.auth.currentUser?.uid

                if (validatePasswords(password, confirmPass)){

                    val psychologist = Psychologist("", email, genre, psychologistId.toString(),
                        fullName, "","", "psychologist", "")

                    Firebase.firestore.collection("psychologists").document(psychologistId!!)
                        .set(psychologist).await()

                    withContext(Dispatchers.Main){
                        authStateLV.value = AuthState(result.user?.uid, true)
                    }

                }else{
                    withContext(Dispatchers.Main){
                        errorLV.value = ErrorMessage("Las contraseñas no coinciden")
                    }
                }

            }catch (e: FirebaseAuthInvalidCredentialsException) {
                withContext(Dispatchers.Main){
                    errorLV.value = ErrorMessage("El correo está mal formado")
                }

            }catch (e: FirebaseAuthUserCollisionException) {
                withContext(Dispatchers.Main){
                    errorLV.value = ErrorMessage("El correo está repetido")
                }

            } catch (e: FirebaseAuthWeakPasswordException) {
                withContext(Dispatchers.Main){
                    errorLV.value = ErrorMessage("La clave es muy debil")
                }
            }
        }
    }

    fun signupStud(name:String,lastname:String,career:String,code:String,age:Int,genre:String,email: String, password: String, confirmPass : String) {

        viewModelScope.launch(Dispatchers.IO) {

            try {

                val result = Firebase.auth.createUserWithEmailAndPassword(email, password).await()
                val studentId = Firebase.auth.currentUser?.uid

                val student = Student(age, career, code, genre, studentId.toString(),
                    lastname, name, "", "", "student", email)

                if (validatePasswords(password, confirmPass)){
                    Firebase.firestore.collection("students").document(studentId!!)
                        .set(student).await()

                    withContext(Dispatchers.Main){
                        authStateLV.value = AuthState(result.user?.uid, true)
                    }

                }else{
                    withContext(Dispatchers.Main){
                        errorLV.value = ErrorMessage("Las contraseñas no coinciden")
                    }
                }

            }catch (e: FirebaseAuthInvalidCredentialsException) {
                withContext(Dispatchers.Main){
                    errorLV.value = ErrorMessage("El correo está mal formado")
                }

            } catch (e: FirebaseAuthUserCollisionException) {
                withContext(Dispatchers.Main){
                    errorLV.value = ErrorMessage("El correo está repetido")
                }

            } catch (e: FirebaseAuthWeakPasswordException) {
                withContext(Dispatchers.Main){
                    errorLV.value = ErrorMessage("La clave es muy debil")
                }
            }
        }
    }

    fun uploadDefaultImagePsy(uri: Uri, userId: String){

        viewModelScope.launch(Dispatchers.IO) {
            val uuid = UUID.randomUUID().toString()

            Firebase.storage.reference
                .child("users")
                .child("profileImages")
                .child(uuid)
                .putFile(uri).await()

            Firebase.firestore.collection("psychologists")
                .document(userId)
                .update("profileImageId",uuid).await()
        }
    }

    fun uploadDefaultImageStud(uri: Uri, userId: String){

        viewModelScope.launch(Dispatchers.IO) {
            val uuid = UUID.randomUUID().toString()

            Firebase.storage.reference
                .child("users")
                .child("profileImages")
                .child(uuid)
                .putFile(uri).await()

            Firebase.firestore.collection("students")
                .document(userId)
                .update("profileImageId",uuid).await()
        }
    }

    fun signIn(email: String, pass: String) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = Firebase.auth.signInWithEmailAndPassword(email, pass).await()
                withContext(Dispatchers.Main){
                    authStateLV.value = AuthState(result.user?.uid, true)
                }

            }catch (e: FirebaseAuthException) {
                withContext(Dispatchers.Main){
                    errorLV.value = ErrorMessage("Error de autenticación")
                }

            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    errorLV.value = ErrorMessage("Credenciales inválidas")
                }
            }
        }
    }

    private fun validatePasswords(password: String, confirmPass : String) : Boolean{
        return password == confirmPass
    }

    fun getRoleOfLoggedUser(userId : String) {
        viewModelScope.launch(Dispatchers.IO) {

            val student: Student? = studentRepository.getStudent(userId)

            if(student != null){
                withContext(Dispatchers.Main){
                    user.value = student
                }
            }
            else{
                val psychologist: Psychologist? = PsychRepository.getPsychologist(userId)

                if (psychologist != null){
                    withContext(Dispatchers.Main){
                        user.value = psychologist
                    }
                }
                else{
                    authStateLV.value?.isAuth?.let{isAuth ->
                        if(isAuth){
                            withContext(Dispatchers.Main){
                                isAdmin.value = true
                            }
                        }
                    }
                }
            }
        }
    }
}