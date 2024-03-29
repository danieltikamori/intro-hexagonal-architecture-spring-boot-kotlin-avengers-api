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
    fun toEntity(avenger: Avenger?): Avenger? {
        if (avenger != null) {
            avenger.nick = nick
        }
        if (avenger != null) {
            avenger.person = person
        }
        if (avenger != null) {
            avenger.description = desc
        }
        if (avenger != null) {
            avenger.history = history
        }

        return avenger
    }
}