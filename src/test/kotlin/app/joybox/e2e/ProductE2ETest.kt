package app.joybox.e2e

import app.joybox.api.response.AddImageResponse
import app.joybox.api.response.AddProductResponse
import app.joybox.api.response.GetProductResponse
import app.joybox.api.response.GetSimpleProductResponse
import app.joybox.config.AWSTestConfig
import app.joybox.config.JPAConfig
import app.joybox.utils.TestDataGenerator
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
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource
import org.springframework.http.*
import org.springframework.test.annotation.DirtiesContext.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.util.*
import kotlin.io.path.toPath

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [AWSTestConfig::class, JPAConfig::class]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
    fun `Should return status code 201 when add product`() {
        val request = TestDataGenerator.addProductRequest()
        val response = template.postForEntity("/api", request, AddProductResponse::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)
    }

    @Test
    fun `Should return status code 200 when delete product`() {
        val addProductRequest = TestDataGenerator.addProductRequest()
        template.postForEntity("/api", addProductRequest, AddProductResponse::class.java)

        val getSimpleProductResponses: ResponseEntity<Array<GetSimpleProductResponse>> =
            template.getForEntity("/api", Array<GetSimpleProductResponse>::class)

        val id = getSimpleProductResponses.body!![0].id

        val response = template.exchange("/api/$id", HttpMethod.DELETE, null, Any::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun `Should return status code 400 when delete not existing product`() {
        val response = template.exchange("/api/93485713495", HttpMethod.DELETE, null, Any::class.java)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    fun `Should return status code 200 and simple products list when get simple products`() {
        val addProductRequest = TestDataGenerator.addProductRequest()
        template.postForEntity("/api", addProductRequest, AddProductResponse::class.java)

        val responses =
            template.exchange(
                "/api",
                HttpMethod.GET,
                null,
                object : ParameterizedTypeReference<List<GetSimpleProductResponse>>() {})
        assertEquals(HttpStatus.OK, responses.statusCode)
    }

    @Test
    fun `Should return status code 200 and product details when get product`() {
        val addProductRequest = TestDataGenerator.addProductRequest()
        template.postForEntity("/api", addProductRequest, AddProductResponse::class.java)

        val getSimpleProductResponses =
            template.exchange(
                "/api",
                HttpMethod.GET,
                null,
                object : ParameterizedTypeReference<List<GetSimpleProductResponse>>() {})
        val ids = getSimpleProductResponses.body!!.map { it.id }

        val getProductResponse: ResponseEntity<GetProductResponse> =
            template.getForEntity("/api/${ids[0]}", GetProductResponse::class.java)
        assertEquals(HttpStatus.OK, getProductResponse.statusCode)
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
}

