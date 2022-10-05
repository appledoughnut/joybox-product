package app.joybox.api.response

import app.joybox.domain.product.Product

class GetSimpleProductResponse(
    val id: Long,
    val title: String,
    val price: Int
) {
    companion object {
        fun from(product: Product): GetSimpleProductResponse {
            return GetSimpleProductResponse(
                product.id!!,
                product.title,
                product.price
            )
        }

    }
}