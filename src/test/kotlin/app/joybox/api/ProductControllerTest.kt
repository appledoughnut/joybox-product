package app.joybox.api

import app.joybox.domain.product.ProductService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.multipart
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
    fun getProduct() {
    }

    @Test
    fun addProduct() {
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
            status { isOk() }
            content { jsonPath("$.uuid") { this.value(uuid.toString())} }
        }
    }
}