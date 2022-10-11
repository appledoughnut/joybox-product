package app.joybox.domain.product

import app.joybox.config.JPAConfig
import app.joybox.domain.image.Image
import app.joybox.domain.image.ImageRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import java.time.LocalDateTime
import java.util.*

@DataJpaTest
@Import(JPAConfig::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductRepositoryTest {

    @Autowired
    lateinit var productRepository: ProductRepository
    @Autowired
    lateinit var imageRepository: ImageRepository

    @BeforeAll
    fun beforeAll() {
        productRepository.deleteAll()
        imageRepository.deleteAll()
    }

    @Test
    fun `Product should have createdAt with not null when getting product`() {
        val id = 1L
        val product = Product(id)
        product.title = "title"
        product.price = 10000
        product.description = "description"

        productRepository.saveAndFlush(product)

        val getProduct = productRepository.findById(id).get()

        assertNotEquals(LocalDateTime.MIN, getProduct.createdAt)
    }

    @Test
    fun `Image should have createdAt with not null when getting image`() {

        val uuid = UUID.randomUUID()
        val image = Image.create(uuid, "")

        imageRepository.saveAndFlush(image)

        val getProduct = imageRepository.findById(uuid).get()

        assertNotEquals(LocalDateTime.MIN, getProduct.createdAt)
    }
}