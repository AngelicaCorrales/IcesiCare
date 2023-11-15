package icesi.edu.co.icesicare.viewmodel

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

class AuthViewModel : ViewModel() {

    val authStateLV = MutableLiveData<AuthState>()
    val errorLV = MutableLiveData<ErrorMessage>()

    private var studentRepository: StudentRepository = StudentRepository()

    fun signUp(fullName : String, email : String, password: String) {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val result = Firebase.auth.createUserWithEmailAndPassword(email, password).await()
                val psychologistId = Firebase.auth.currentUser?.uid

                val psychologist = Psychologist("", email, "", psychologistId.toString(),
                    fullName, "","", "psychologist", "")

                Firebase.firestore.collection("psychologists").document(psychologistId!!)
                    .set(psychologist).await()

                withContext(Dispatchers.Main){
                    authStateLV.value = AuthState(result.user?.uid, true)
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

    fun getRoleOfLoggedStudent(userId : String) : Any? {

        var student: Student? = null
        var psychologist: Psychologist? = null
        var validator = 0

        viewModelScope.launch(Dispatchers.IO) {
            student = studentRepository.getStudent(userId)

            if (student!!.age == -1){
                psychologist = PsychRepository.getPsychologist(userId)

                if (psychologist!!.description != "-1"){
                    validator = -1
                }

            }else{
                validator = 1
            }
        }

        return if (validator == 1){
            student

        }else{
            psychologist
        }
    }
}