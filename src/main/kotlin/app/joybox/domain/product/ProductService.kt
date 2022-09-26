package app.joybox.domain.product

import app.joybox.domain.image.Image
import app.joybox.domain.image.ImageStorage
import app.joybox.domain.product.Product
import app.joybox.domain.product.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val imageStorage: ImageStorage
) {

    fun getProduct(id: Long): Product? {
        return this.productRepository.findById(id)
            .orElse(null)
    }
}