package cc.tkmr.avengers.api.repository

import cc.tkmr.avengers.api.resource.avenger.AvengerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

// By hexagonal convention, This interface is our port that will be exposed to the outside world
@Repository
interface AvengerRepository: JpaRepository<AvengerEntity, Long> {

}