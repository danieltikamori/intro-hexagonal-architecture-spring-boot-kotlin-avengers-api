package cc.tkmr.avengers.avengersapi.domain.avenger

data class Avenger(
    val nick: String,
    val person: String,
    val description: String?, // ? means nullable
    val history: String?
)
