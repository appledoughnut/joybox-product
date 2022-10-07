package app.joybox.api.request

import app.joybox.api.command.AddProductCommand
import java.util.*

data class AddProductRequest(
    val title: String,
    val price: Int,
    val description: String,
    val imageIds: List<UUID>
) {
    fun toCommand(): AddProductCommand {
        return AddProductCommand(title, price, description, imageIds)
    }
}
