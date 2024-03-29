package cc.tkmr.avengers.api.domain.avenger

// Model class
data class Avenger(
    val id: Long? = null,
    val nick: String,
    val person: String,
    val description: String?,
    val history: String?
)