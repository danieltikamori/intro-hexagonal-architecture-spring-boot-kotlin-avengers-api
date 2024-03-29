package cc.tkmr.avengers.api.service.implemented

import cc.tkmr.avengers.api.domain.avenger.Avenger
import cc.tkmr.avengers.api.repository.AvengerRepository
import cc.tkmr.avengers.api.resource.avenger.AvengerEntity
import cc.tkmr.avengers.api.service.AvengerServiceInterface
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AvengerService(
    private val avengerRepository: AvengerRepository,
    private val repository: AvengerRepository
) : AvengerServiceInterface{
    override fun getDetail(id: Long): Avenger? =
        repository.findByIdOrNull(id)?.toAvenger()

    override fun getAvengers(): List<Avenger> =
        repository.findAll().map { it.toAvenger() }

    override fun createAvenger(avenger: Avenger): Avenger =
        repository.save(AvengerEntity.from(avenger)).toAvenger()

    override fun deleteById(id: Long) = repository.deleteById(id)

    override fun update(avenger: Avenger): Avenger =
        repository.save(AvengerEntity.from(avenger)).toAvenger()

}