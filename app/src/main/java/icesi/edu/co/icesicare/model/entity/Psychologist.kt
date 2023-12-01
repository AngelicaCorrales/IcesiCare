package icesi.edu.co.icesicare.model.entity

import com.google.firebase.firestore.PropertyName

data class Psychologist(

    var description : String = "",
    var email : String = "",
    var genre : String = "",
    var id : String = "",
    var name : String = "",
    var profileImageId : String? = null,
    var profileImageURL : String? = null,
    var role : String = "",

    var scheduleId : String = "",
    var pendingApproval : Boolean = true,
    var approved : Boolean = false

)
