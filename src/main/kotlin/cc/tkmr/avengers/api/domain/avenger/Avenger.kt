package cc.tkmr.avengers.api.domain.avenger

import jakarta.persistence.*

// Model class
@Entity
class Avenger(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false, unique = true)
    var nick: String = "",
    @Column(nullable = false)
    var person: String = "",
    var description: String? = "",
    var history: String? = ""
) {
    constructor(
        nick: String,
        person: String,
        desc: String?,
        history: String?
    ) : this (null, nick = nick, person = person, description = desc, history = history)
}