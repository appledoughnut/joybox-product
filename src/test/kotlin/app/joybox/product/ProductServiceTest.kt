package app.joybox.product

import app.joybox.api.command.UpdateProductCommand
import app.joybox.domain.image.ImageRepository
import app.joybox.domain.image.ImageStorage
import app.joybox.domain.product.ProductNotFoundException
import app.joybox.domain.product.ProductRepository
import app.joybox.domain.product.ProductService
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@ExtendWith(MockKExtension::class)
class ProductServiceTest {

    @MockK
    lateinit var productRepository: ProductRepository

    @MockK
    lateinit var imageRepository: ImageRepository

    @MockK
    lateinit var imageStorage: ImageStorage

    @Autowired
    lateinit var productService: ProductService

    @Test
    fun `Should throw ProductNotFoundException when get product with id of not existing product`() {
        every { productRepository.existsById(any()) } returns false

        assertThrows<ProductNotFoundException> { productService.getProduct(0L) }
    }

    @Test
    fun `Should throw ProductNotFoundException when update product with id of not existing product`() {
        every { productRepository.existsById(any()) } returns false
        val command = UpdateProductCommand(0L, "title", 10000, "description", emptyList())

        assertThrows<ProductNotFoundException> { productService.updateProduct(command) }
    }

    @Test
    fun `Should throw ProductNotFoundException when delete product with id of not existing product`() {
        every { productRepository.existsById(any()) } returns false

        assertThrows<ProductNotFoundException> { productService.deleteProduct(0) }
    }
}