package app.joybox.image

import app.joybox.config.AWSTestConfig
import app.joybox.domain.image.ImageStorage
import com.amazonaws.services.s3.AmazonS3
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource
import java.util.*
import kotlin.io.path.toPath

@SpringBootTest(
    classes = [AWSTestConfig::class]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ImageStorageTests {

    @Autowired
    lateinit var imageStorage: ImageStorage

    @Autowired
    lateinit var s3client: AmazonS3

    @Value("\${cloud.aws.bucket}")
    lateinit var bucket: String

    @BeforeAll
    fun beforeAll() {
        this.s3client.createBucket(bucket)
    }

    @Test
    fun `Should return same image keys when delete images`() {
        val imageResource = ClassPathResource("images/pooh.png")
        val file = FileSystemResource(imageResource.uri.toPath())

        val ids = mutableListOf<UUID>()
        for (i in 0 until 3) {
            ids.add(imageStorage.save(file.inputStream))
        }

        assertDoesNotThrow {
            imageStorage.deleteWithUuids(ids)
        }
    }
}
