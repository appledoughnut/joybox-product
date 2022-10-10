package app.joybox.domain.image

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import kotlin.io.path.toPath

@SpringBootTest
internal class ImageDeletionSchedulerTest {

    @Autowired
    lateinit var imageRepository: ImageRepository

    @Autowired
    lateinit var imageStorage: ImageStorage

    lateinit var imageDeletionScheduler: ImageDeletionScheduler

    @Autowired
    lateinit var em: EntityManager

    @BeforeEach
    fun beforeEach() {
        val images = imageRepository.findAll()
        val ids = images.map { it.uuid!! }
        imageRepository.deleteAll()
        imageStorage.deleteWithUuids(ids)
    }

    @Test
    fun `Should not delete image created within a day`() {
        val imageResource = ClassPathResource("images/pooh.png")
        val file = FileSystemResource(imageResource.uri.toPath())
        val id = imageStorage.save(file.inputStream)

        val image = Image.create(id, "")
        imageRepository.save(image)

        val standardDateTerm = 1L
        imageDeletionScheduler = ImageDeletionScheduler(standardDateTerm, imageRepository, imageStorage)
        imageDeletionScheduler.executeScheduler()
        assertEquals(1, imageRepository.findAll().size)
    }

    @Test
    @Transactional
    fun `Should delete image created in a day`() {
        val imageResource = ClassPathResource("images/pooh.png")
        val file = FileSystemResource(imageResource.uri.toPath())
        val id = imageStorage.save(file.inputStream)

        val image = Image.create(id, "")
        imageRepository.save(image)

        val standardDateTerm = 0L
        imageDeletionScheduler = ImageDeletionScheduler(standardDateTerm, imageRepository, imageStorage)
        imageDeletionScheduler.executeScheduler()

        val found = imageRepository.findAll()
        assertEquals(0, found.size)
    }
}