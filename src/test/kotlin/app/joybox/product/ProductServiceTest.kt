package app.joybox.product

import app.joybox.domain.image.ImageRepository
import app.joybox.domain.image.ImageStorage
import app.joybox.domain.product.ProductRepository
import app.joybox.domain.product.ProductService
import io.mockk.mockk
import io.mockk.spyk
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ProductServiceTest {

    @Autowired
    val productRepository: ProductRepository = mockk()
    val imageRepository: ImageRepository = mockk()
    val imageStorage: ImageStorage = mockk()
    val productService: ProductService = spyk(ProductService(productRepository, imageRepository, imageStorage))


}