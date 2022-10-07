package app.joybox.product

import app.joybox.config.JPAConfig
import app.joybox.domain.product.Product
import app.joybox.domain.product.ProductRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(JPAConfig::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductRepositoryTest {

    @Autowired
    lateinit var productRepository: ProductRepository

    @BeforeAll
    fun beforeAll() {
        productRepository.deleteAll()
    }

    @Test
    fun `Product should have createdAt with not null when getting product`() {

        val product = Product()
        product.title = "title"
        product.price = 10000
        product.description = "description"

        productRepository.saveAndFlush(product)

        val getProduct = productRepository.findAll()[0]

        assertNotNull(getProduct.createdAt)
    }
}