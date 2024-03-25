package cc.tkmr.avengers.avengersapi.application.web.resource

import cc.tkmr.avengers.avengersapi.application.web.resource.request.AvengerRequest
import cc.tkmr.avengers.avengersapi.application.web.resource.response.AvengerResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/v1/avenger"])
class AvengerResource {

    @GetMapping
    fun getAvengers() = ResponseEntity.ok().body(emptyList<AvengerResponse>())

    @GetMapping("{id}")
    fun getAvengersDetails(@PathVariable("id") id: Long) = ResponseEntity.ok().build<AvengerResponse>()

    // @Valid will execute validation described in AvengerRequest. See AvengerRequest.kt
    // @RequestBody will bind request body to AvengerRequest object
    @PostMapping
    fun createAvenger(@Valid @RequestBody request: AvengerRequest) = ResponseEntity.ok().build<AvengerResponse>()
}