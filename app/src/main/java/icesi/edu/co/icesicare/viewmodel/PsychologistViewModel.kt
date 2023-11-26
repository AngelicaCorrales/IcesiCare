package icesi.edu.co.icesicare.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icesi.edu.co.icesicare.model.entity.Psychologist
import icesi.edu.co.icesicare.model.repository.PsychRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PsychologistViewModel:ViewModel() {

    val psychologistLV = MutableLiveData<Psychologist>()

    fun getPsychologist(psychologistId : String){

        viewModelScope.launch(Dispatchers.IO) {
            val psychologist = PsychRepository.getPsychologist(psychologistId)

            withContext(Dispatchers.Main){
                psychologistLV.value = psychologist
            }
        }
    }
}