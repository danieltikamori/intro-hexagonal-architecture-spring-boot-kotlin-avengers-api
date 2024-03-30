package cc.tkmr.avengers.api.resource.avenger

import cc.tkmr.avengers.api.domain.avenger.Avenger
import jakarta.persistence.*

// Entity class
@Entity
@Table(name = "avenger")
class AvengerEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false, unique = true)
    var nick: String = "",
    @Column(nullable = false)
    var person: String = "",
    var description: String? = "",
    var history: String? = ""
) {
//    constructor(
//        nick: String,
//        person: String,
//        desc: String?,
//        history: String?
//    ) : this (null, nick = nick, person = person, description = desc, history = history)

    fun toAvenger() = Avenger(
        id, nick, person, description, history)

    companion object {
        fun from(avenger: Avenger) = AvengerEntity(
            id = avenger.id,
            nick = avenger.nick,
            description = avenger.description,
            history = avenger.history,
            person = avenger.person
        )
    }
}