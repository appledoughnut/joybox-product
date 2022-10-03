package app.joybox.e2e

import app.joybox.api.response.AddImageResponse
import app.joybox.config.AWSTestConfig
import app.joybox.config.JPAConfig
import com.amazonaws.services.s3.AmazonS3
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import kotlin.io.path.toPath

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [AWSTestConfig::class, JPAConfig::class]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //TODO
class ProductE2ETest {

    @Value("\${cloud.aws.bucket}")
    private lateinit var bucket: String

    @Autowired
    private lateinit var template: TestRestTemplate

    @Autowired
    private lateinit var s3client: AmazonS3

    @BeforeAll
    fun beforeAll() {
        this.s3client.createBucket(bucket)
    }

    @Test
    fun `Should return status code 201 and uuid when adding product image`() {
        val imageResource = ClassPathResource("images/pooh.png")
        val file = FileSystemResource(imageResource.uri.toPath())

        val body = LinkedMultiValueMap<String, Any>()
        body.add("image", file)

        val headers = HttpHeaders()
        headers.contentType = MediaType.MULTIPART_FORM_DATA

        val httpEntity = HttpEntity<MultiValueMap<String, Any>>(body, headers)
        val response = template.postForEntity("/api/image", httpEntity, AddImageResponse::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.body)

        val uuid = response.body!!.id
        assertNotNull(uuid)
    }

//    @Test
//    fun `Should return status code 201 when adding product`() {
//        val request = AddProductRequest(
//
//        )
//        val response = template.postForEntity("/api", request, Any::class.java)
//        assertEquals(HttpStatus.CREATED, response.statusCode)
//    }
}