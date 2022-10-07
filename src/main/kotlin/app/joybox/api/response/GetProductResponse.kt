package app.joybox.api.response

import app.joybox.domain.product.Product

data class GetProductResponse(
    val id: Long,
    val title: String,
    val price: Int,
    val description: String?
) {
    companion object {
        fun from(product: Product): GetProductResponse {
            return GetProductResponse(
                product.id!!,
                product.title,
                product.price,
                product.description
            )
        }
    }
}