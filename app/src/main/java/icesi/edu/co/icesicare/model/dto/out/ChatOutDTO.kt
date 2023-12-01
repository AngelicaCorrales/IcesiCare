package icesi.edu.co.icesicare.model.dto.out

data class ChatOutDTO(
    var usrName : String = "",
    var usrImage : String = "",
    var lastMessage : String = "",
    var lastMessHour : String = "",
    var chatId : String = ""
)
