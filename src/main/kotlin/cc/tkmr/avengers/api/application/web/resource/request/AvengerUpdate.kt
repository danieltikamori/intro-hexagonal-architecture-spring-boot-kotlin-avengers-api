package cc.tkmr.avengers.api.application.web.resource.request

import cc.tkmr.avengers.api.domain.avenger.Avenger
import jakarta.validation.constraints.NotEmpty

data class AvengerUpdate (
    @field:NotEmpty
    val nick: String,
    @field:NotEmpty
    val person: String,
    val desc: String?,
    val history: String?
) {
    fun toEntity(avenger: Avenger): Avenger {
        avenger.nick = nick
        avenger.person = person
        avenger.description = desc
        avenger.history = history

        return avenger
    }
}