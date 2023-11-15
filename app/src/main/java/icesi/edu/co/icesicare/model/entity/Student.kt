package icesi.edu.co.icesicare.model.entity

data class Student (

    var age : Int = 0,
    var career : String = "",
    var code : String = "",
    var genre : String = "",
    var id : String = "",
    var lastname : String = "",
    var name : String = "",
    var profileImageId : String? = null,
    var profileImageURL: String? = null,
    var role: String = "",
    var email: String = ""
)