package cc.tkmr.avengers.api.application.web.resource

import cc.tkmr.avengers.api.application.web.resource.request.AvengerRequest
import cc.tkmr.avengers.api.application.web.resource.request.AvengerUpdate
import cc.tkmr.avengers.api.application.web.resource.response.AvengerResponse
import cc.tkmr.avengers.api.service.implemented.AvengerService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val API_PATH = "v1/api/avenger"

@RestController
@RequestMapping(API_PATH)
class AvengerResource (
    private val avengerService: AvengerService
) {

    @GetMapping
    fun getAvengers() =
        this.avengerService.getAvengers()
            .map { AvengerResponse.from(it) }
        .let {
            // if it is empty, return empty list
            if (it.isNotEmpty()) ResponseEntity.ok().body(it) else ResponseEntity.ok().body(emptyList<AvengerResponse>())
        }

    // @PathVariable will bind path variable to id
    @GetMapping("{id}/details")
    fun getAvengerDetails(@PathVariable("id") id: Long) =
        this.avengerService.getDetail(id)
            .let { ResponseEntity.ok().body(AvengerResponse.from(it)) }

    // @Valid will execute validation described in AvengerRequest. See AvengerRequest.kt
    // @RequestBody will bind request body to AvengerRequest object
    @PostMapping
    fun postAvenger(@Valid @RequestBody avenger: AvengerRequest) =

        avenger.toAvenger().run {
            avengerService.createAvenger(this)
        }.let {
            ResponseEntity
                .created(URI("$API_PATH/${it.id}"))
                .body(AvengerResponse.from(it))
        }

    @PutMapping("{id}")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody @Valid avengerUpdate: AvengerUpdate
    ): ResponseEntity<AvengerResponse> {
        val avenger = this.avengerService.getDetail(id)
        val avengerToUpdate = avengerUpdate.toEntity(avenger)
        val avengerUpdated = avengerToUpdate?.let { this.avengerService.createAvenger(it) }
        return ResponseEntity.status(HttpStatus.OK).body(AvengerResponse.from(avengerUpdated))
    }

    @DeleteMapping("{id}")
    fun deleteById(@PathVariable("id") id: Long) =
        this.avengerService.deleteById(id).let {
            ResponseEntity.accepted().build<Unit>()
        }
}
