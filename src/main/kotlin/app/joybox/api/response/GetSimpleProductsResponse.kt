package app.joybox.api.response

import app.joybox.domain.product.Product

class SimpleProduct(
    val id: Long,
    val title: String,
    val price: Int
) {
    companion object {
        fun from(product: Product): SimpleProduct {
            return SimpleProduct(
                product.id!!,
                product.title,
                product.price
            )
        }
    }
}

class GetSimpleProductsResponse(
    val simpleProducts: MutableList<SimpleProduct> = mutableListOf()
) {
    companion object {
        fun from(products: List<Product>): GetSimpleProductsResponse {
            val response = GetSimpleProductsResponse()
            products.map {
                response.simpleProducts.add(SimpleProduct.from(it))
            }
            return response
        }
    }
}