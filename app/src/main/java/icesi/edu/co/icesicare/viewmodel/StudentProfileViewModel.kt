package icesi.edu.co.icesicare.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icesi.edu.co.icesicare.model.entity.Student
import icesi.edu.co.icesicare.model.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
}