package cc.tkmr.avengers.api.application.web.resource.request

import cc.tkmr.avengers.api.domain.avenger.Avenger
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AvengerRequest(
    // Forces validation for nick and person
    @field:NotBlank
    @field:Size(max = 128)
    val nick: String,
    @field:NotBlank
    @field:Size(max = 128)
    val person: String,
    @field:Size(max = 255)
    val description: String? = null, // Optional. Default is ""
    @field:Size(max = 1000) // Maximum 1000 characters as we must limit the text size. The table uses text type which is limited to 65535.
    val history: String? = null
) {

    fun toAvenger() = Avenger(
        nick = nick,
        person = person,
        description = description,
        history = history
    )

    companion object {
        fun to(id: Long, request: AvengerRequest) = Avenger(
            id = id,
            nick = request.nick,
            person = request.person,
            description = request.description,
            history = request.history

        )
    }
}
