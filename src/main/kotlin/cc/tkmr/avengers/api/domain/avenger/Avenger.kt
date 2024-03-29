package cc.tkmr.avengers.api.domain.avenger

// Model class
data class Avenger(
    val id: Long? = null,
    var nick: String,
    var person: String,
    var description: String?,
    var history: String?
)