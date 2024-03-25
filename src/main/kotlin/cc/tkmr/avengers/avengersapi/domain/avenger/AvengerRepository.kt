package cc.tkmr.avengers.avengersapi.domain.avenger


// By hexagonal convention, This interface is our port that will be exposed to the outside world
interface AvengerRepository {

    // This interface will return the object Avenger with the given id
    fun getDetails(id: Long): Avenger
    fun getAvengers(): List<Avenger>
    fun createAvenger(avenger: Avenger): Avenger
    fun delete(id: Long)
    fun update(avenger: Avenger): Avenger
}