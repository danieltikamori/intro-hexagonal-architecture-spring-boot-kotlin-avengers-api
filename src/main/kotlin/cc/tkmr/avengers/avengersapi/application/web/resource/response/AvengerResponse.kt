package cc.tkmr.avengers.avengersapi.application.web.resource.response

data class AvengerResponse(
    val nick: String,
    val person: String,
    val description: String,
    val history: String
)