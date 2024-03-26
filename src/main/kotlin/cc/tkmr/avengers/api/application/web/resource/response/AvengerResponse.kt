package cc.tkmr.avengers.api.application.web.resource.response

import cc.tkmr.avengers.api.domain.avenger.Avenger

data class AvengerResponse(
    val id: Long?, // ? means nullable
    val nick: String,
    val person: String,
    val description: String?, // ? means nullable
    val history: String?
)
// Static factory method
{
    companion object {
        fun from(avenger: Avenger) = AvengerResponse(
            id = avenger.id,
            nick = avenger.nick,
            person = avenger.person,
            description = avenger.description,
            history = avenger.history
        )
    }
}
