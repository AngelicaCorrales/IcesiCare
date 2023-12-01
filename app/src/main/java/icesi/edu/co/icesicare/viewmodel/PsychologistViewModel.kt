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

    val errorLD = MutableLiveData<Exception>()
    val psychsPendingApprovalLD = MutableLiveData<MutableList<Psychologist>>()
    private var psychsPendingApproval : MutableList<Psychologist> = arrayListOf<Psychologist>()
    val psychologistLV = MutableLiveData<Psychologist?>()

    fun getPsychologist(psychologistId : String){

        viewModelScope.launch(Dispatchers.IO) {
            val psychologist = PsychRepository.getPsychologist(psychologistId)

            withContext(Dispatchers.Main){
                psychologistLV.value = psychologist
            }
        }
    }

    fun updatePsychStatus(isAccepted: Boolean, psychId: String) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                PsychRepository.updatePsychStatus(isAccepted,psychId)
                getPsychologistsPendingForApproval()
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    errorLD.value = e
                }
            }
        }
    }

    fun getPsychologistsPendingForApproval() {
        viewModelScope.launch (Dispatchers.IO){
            try {
                val psychs = PsychRepository.getPsychologistsPendingForApproval()
                psychsPendingApproval = psychs

                withContext(Dispatchers.Main){
                    psychsPendingApprovalLD.value = psychsPendingApproval
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    errorLD.value = e
                }
            }
        }
    }


}