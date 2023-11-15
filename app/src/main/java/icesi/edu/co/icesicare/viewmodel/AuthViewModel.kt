package icesi.edu.co.icesicare.viewmodel

import android.util.Log
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthViewModel : ViewModel() {

    val authStateLV = MutableLiveData<AuthState>()
    val errorLV = MutableLiveData<ErrorMessage>()

    fun signup(fullname:String,genre:String,email: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val result = Firebase.auth.createUserWithEmailAndPassword(email, pass).await()
                val user= hashMapOf(
                    "name" to fullname,
                    "genre" to genre,
                    "email" to email,
                    "id" to Firebase.auth.currentUser?.uid,
                    "description" to "",
                    "profileImageId" to "",
                    "role" to "psychologist",
                    "scheduleId" to ""
                )

                Firebase.firestore.collection("psychologists").document(user["id"]!!).set(user).await()
                withContext(Dispatchers.Main){ authStateLV.value = AuthState(result.user?.uid, true)}
                Log.e(">>>", "Registrado")
                Firebase.auth.currentUser
            }catch (e: FirebaseAuthInvalidCredentialsException) {
                withContext(Dispatchers.Main){errorLV.value = ErrorMessage("El correo está mal formado")}
                Log.e(">>>", "Mal formado")
            } catch (e: FirebaseAuthUserCollisionException) {
                withContext(Dispatchers.Main){errorLV.value = ErrorMessage("El correo está repetido")}
                Log.e(">>>", "Repetido")
            } catch (e: FirebaseAuthWeakPasswordException) {
                withContext(Dispatchers.Main){errorLV.value = ErrorMessage("La clave es muy debil")}
                Log.e(">>>", "Clave muy corta")
            }
        }
    }

    fun signin(email: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = Firebase.auth.signInWithEmailAndPassword(email, pass).await()
                withContext(Dispatchers.Main){authStateLV.value = AuthState(result.user?.uid, true)}
                Log.e(">>>", "Loggeado")
            } catch (e: FirebaseAuthException) {
                withContext(Dispatchers.Main){errorLV.value = ErrorMessage("Error de autenticación")}
                Log.e(">>>", "Error de autenticación")
            }
        }

    }
}

data class AuthState(
    var userID: String? = null,
    var isAuth: Boolean
)

data class ErrorMessage(
    var message: String
)