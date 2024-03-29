package cc.tkmr.avengers.api.service

import cc.tkmr.avengers.api.domain.avenger.Avenger

interface AvengerServiceInterface {
    // This interface will return the object Avenger with the given id
    fun getDetail(id: Long): Avenger?
    fun getAvengers(): List<Avenger>
    fun createAvenger(avenger: Avenger): Avenger
    fun deleteById(id: Long)
    fun update(avenger: Avenger): Avenger
}