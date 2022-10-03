package app.joybox.api.request

import app.joybox.domain.product.AddProductCommand
import java.util.*

data class AddProductRequest(
    val title: String,
    val price: Int,
    val description: String,
    val imagesIds: List<UUID>
) {
    fun toCommand(): AddProductCommand {
        return AddProductCommand(title, price, description, imagesIds)
    }
}