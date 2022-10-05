package app.joybox

import app.joybox.api.request.AddProductRequest
import app.joybox.domain.image.Image
import app.joybox.domain.product.Product
import java.util.*

class TestDataGenerator {
    companion object {
        fun product(
            id: Long? = null,
            title: String = "title",
            price: Int = 10000,
            description: String = "description",
            images: MutableList<Image> = mutableListOf()
        ): Product {
            val product = Product(id)
            product.title = title
            product.price = price
            product.description = description
            product.images = images

            return product
        }

        fun products(
            len: Long = 3L
        ): List<Product> {
            val products = mutableListOf<Product>()
            for (i: Long in 1L..len)
                products.add(this.product(i))
            return products.toList()
        }

        fun addProductRequest(
            title: String = "title",
            price: Int = 10000,
            description: String = "description",
            imageIds: List<UUID> = emptyList()
        ): AddProductRequest {
            return AddProductRequest(title, price, description, imageIds)
        }
    }
}