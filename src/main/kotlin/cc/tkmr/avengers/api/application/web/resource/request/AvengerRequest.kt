package cc.tkmr.avengers.api.application.web.resource.request

import cc.tkmr.avengers.api.domain.avenger.Avenger
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class AvengerRequest(
    // Forces validation for nick and person
    @field:NotNull
    @field:NotBlank
    @field:NotEmpty
    val nick: String,
    @field:NotNull
    @field:NotBlank
    @field:NotEmpty
    val person: String,
    val description: String? = "", // Optional. Default is ""
    val history: String? = ""
) {

    fun toAvenger() = Avenger(
        nick = nick,
        person = person,
        description = description,
        history = history
    )

//    companion object {
//        fun to(id: Long, request: AvengerRequest) = Avenger(
//            id = id,
//            nick = request.nick,
//            person = request.person,
//            description = request.description,
//            history = request.history
//
//        )
//        const val API_PATH = "/avengers"
//    }
}
