package icesi.edu.co.icesicare.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.model.entity.Psychologist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class PsychologistViewModel : ViewModel() {

    val psychologistLV = MutableLiveData<Psychologist>()



}