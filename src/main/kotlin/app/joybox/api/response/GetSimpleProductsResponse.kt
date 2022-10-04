package app.joybox.api.response

import app.joybox.domain.product.Product

class GetSimpleProductResponse(
    val id: Long,
    val title: String,
    val price: Int
) {
    companion object {
        fun from(products: List<Product>): List<GetSimpleProductResponse> {
            return products.map {
                GetSimpleProductResponse(
                    it.id!!,
                    it.title,
                    it.price
                )
            }
        }

    }
}