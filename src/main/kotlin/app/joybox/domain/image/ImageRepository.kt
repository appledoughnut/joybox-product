package app.joybox.domain.image

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface ImageRepository: JpaRepository<Image, UUID> {
    fun findByUuidIn(uuids: List<UUID>): List<Image>
    fun findByProductNullAndCreatedAtLessThan(createdAt: LocalDateTime): List<Image>
}