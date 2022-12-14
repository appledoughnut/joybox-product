package app.joybox.api

import app.joybox.api.request.AddProductRequest
import app.joybox.api.request.UpdateProductRequest
import app.joybox.api.response.GetSimpleProductResponse
import app.joybox.domain.product.Product
import app.joybox.domain.product.ProductNotFoundException
import app.joybox.domain.product.ProductService
import app.joybox.utils.JsonUtils
import app.joybox.utils.TestDataGenerator
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import java.nio.file.Files
import java.util.*
import kotlin.io.path.toPath

@WebMvcTest
internal class ProductControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @TestConfiguration
    class Config {
        @Bean
        fun productService() = mockk<ProductService>()
    }

    @Autowired
    private lateinit var productService: ProductService
    @Test
    fun `Should return status code 200 and product details when get product`() {
        val product = TestDataGenerator.product(1L)
        every { productService.getProduct(any()) } returns product

        mvc.get("/api/1")
            .andExpect {
                status { isOk() }
                this.jsonPath("$.id") { value(product.id) }
                this.jsonPath("$.title") { value(product.title) }
                this.jsonPath("$.price") { value(product.price) }
                this.jsonPath("$.description") { value(product.description) }
                this.jsonPath("$.images") {
                    if (product.images.size > 0) {
                        value(product.images)
                    } else {
                        doesNotExist()
                    }
                }
            }
    }

    @ParameterizedTest
    @ValueSource(longs = [0L, 3L])
    fun `Should return status code 200 when get all simple products`(length: Long) {
        val products = TestDataGenerator.products(length)

        every { productService.getProducts() } returns products

        mvc.get("/api")
            .andExpect {
                status { isOk() }
                jsonPath("$.length()") { value(length) }
            }
    }

    @Test
    fun `Should return status code 201 when add product`() {
        val request = AddProductRequest("title", 10000, "description", emptyList())
        val content = JsonUtils.toJson(request)
        every { productService.addProduct(any()) } returns Unit

        mvc.post("/api") {
            this.contentType = MediaType.APPLICATION_JSON
            this.content = content
        }.andExpect {
            status { isCreated() }
        }
    }

    @Test
    fun `Should return status code 400 when delete product with id of not existing product`() {

        every { productService.deleteProduct(any()) } throws ProductNotFoundException()

        mvc.delete("/api/1")
            .andExpect {
                status { isBadRequest() }
            }
    }

    @Test
    fun `Should return status code 200 when update product`() {
        val request = UpdateProductRequest("title", 10000, "description", emptyList())
        val content = JsonUtils.toJson(request)
        every { productService.updateProduct(any()) } returns Unit

        mvc.put("/api/1") {
            this.contentType = MediaType.APPLICATION_JSON
            this.content = content
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `Should return status code 400 when update product with id of not existing product`() {
        val request = UpdateProductRequest("title", 10000, "description", emptyList())
        val content = JsonUtils.toJson(request)
        every { productService.updateProduct(any()) } throws ProductNotFoundException()

        mvc.put("/api/1") {
            this.contentType = MediaType.APPLICATION_JSON
            this.content = content
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun addImage() {
        val imageResource = ClassPathResource("images/pooh.png")
        val file = Files.readAllBytes(imageResource.uri.toPath())

        val uuid = UUID.randomUUID()

        every { productService.addImage(any()) } returns uuid

        mvc.multipart("/api/image") {
            this.file("image", file)
        }.andExpect {
            status { isCreated() }
            content { jsonPath("$.id") { this.value(uuid.toString()) } }
        }
    }

    @Test
    fun mapTest() {
        val products = emptyList<Product>()
        val productResponseList = products.map {
            GetSimpleProductResponse.from(it)
        }
    }
}