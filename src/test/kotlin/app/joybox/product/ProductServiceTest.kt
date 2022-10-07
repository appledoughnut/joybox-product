package app.joybox.product

import app.joybox.domain.image.ImageRepository
import app.joybox.domain.image.ImageStorage
import app.joybox.domain.product.ProductNotFoundException
import app.joybox.domain.product.ProductRepository
import app.joybox.domain.product.ProductService
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.spyk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
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


    @Test
    fun `Should throw ProductNotFoundException when delete with id of not existing product`() {
        val productService: ProductService = spyk(ProductService(productRepository, imageRepository, imageStorage))

        every { productRepository.existsById(any()) } returns false

        assertThrows<ProductNotFoundException> { productService.deleteProduct(0) }
    }
}