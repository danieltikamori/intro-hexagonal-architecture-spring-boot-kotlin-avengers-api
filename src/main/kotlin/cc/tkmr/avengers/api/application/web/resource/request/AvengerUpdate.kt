package cc.tkmr.avengers.api.application.web.resource.request

import cc.tkmr.avengers.api.domain.avenger.Avenger
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AvengerUpdate (
    @field:NotBlank
    @field:Size(max = 128)
    val nick: String,
    @field:NotBlank
    @field:Size(max = 128)
    val person: String,
    @field:Size(max = 255)
    val description: String?,
    @field:Size(max = 1000)
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
            avenger.description = description
        }
        if (avenger != null) {
            avenger.history = history
        }

        return avenger
    }
}