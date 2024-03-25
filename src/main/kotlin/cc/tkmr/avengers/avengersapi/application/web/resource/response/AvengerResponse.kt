package cc.tkmr.avengers.avengersapi.application.web.resource.response

import cc.tkmr.avengers.avengersapi.domain.avenger.Avenger

data class AvengerResponse(
    val nick: String,
    val person: String,
    val description: String?, // ? means nullable
    val history: String?
)
// Static factory method
{
    companion object {
        fun from(avenger: Avenger) = AvengerResponse(
            nick = avenger.nick,
            person = avenger.person,
            description = avenger.description,
            history = avenger.history
        )
    }
}
