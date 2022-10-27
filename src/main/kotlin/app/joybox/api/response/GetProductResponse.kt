package app.joybox.api.response

import app.joybox.domain.product.Product
import java.util.*

data class GetProductResponse(
    val id: Long,
    val title: String,
    val price: Int,
    val description: String?,
    val imageUrls: List<UUID>
) {
    companion object {
        fun from(product: Product): GetProductResponse {
            return GetProductResponse(
                product.id!!,
                product.title,
                product.price,
                product.description,
                product.images.map { it.uuid!! }
            )
        }
    }
}