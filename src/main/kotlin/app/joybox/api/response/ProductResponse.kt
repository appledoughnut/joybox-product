package app.joybox.api.response

import app.joybox.domain.model.Product
import org.springframework.http.ResponseEntity

data class ProductResponse(
    val id: Long,
    val title: String,
    val price: Int,
    val description: String
) {
    companion object {
        fun from(product: Product): ProductResponse {
            return ProductResponse(
                product.id!!,
                product.title,
                product.price,
                product.description
            )
        }
    }
}