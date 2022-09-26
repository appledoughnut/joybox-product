package app.joybox.domain.service

import app.joybox.domain.model.Product
import app.joybox.domain.model.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {

    fun getProduct(id: Long): Product? {
        return this.productRepository.findById(id)
            .orElse(null)
    }
}