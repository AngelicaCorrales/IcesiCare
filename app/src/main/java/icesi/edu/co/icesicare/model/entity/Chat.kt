package icesi.edu.co.icesicare.model.entity

data class Chat(
    var messages: List<Message>? = emptyList(),
    var id: String = ""
)