package cc.tkmr.avengers.api.service.implemented

import cc.tkmr.avengers.api.domain.avenger.Avenger
import cc.tkmr.avengers.api.repository.AvengerRepository
import cc.tkmr.avengers.api.service.AvengerServiceInterface
import org.springframework.stereotype.Service

@Service
class AvengerService(
    private val avengerRepository: AvengerRepository
) : AvengerServiceInterface{
    override fun getDetail(id: Long): Avenger =
        this.avengerRepository.findById(id).orElseThrow {
            throw RuntimeException("Avenger with id $id not found")
        }

    override fun getAvengers(): List<Avenger> =
        this.avengerRepository.findAll()

    override fun createAvenger(avenger: Avenger): Avenger =
        this.avengerRepository.save(avenger)

    override fun deleteById(id: Long) =
        this.avengerRepository.deleteById(id)

    override fun update(avenger: Avenger): Avenger =
        this.avengerRepository.save(avenger)
}