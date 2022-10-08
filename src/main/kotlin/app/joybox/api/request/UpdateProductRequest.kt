package app.joybox.api.request

import app.joybox.api.command.UpdateProductCommand
import java.util.*

class UpdateProductRequest(
    val title: String,
    val price: Int,
    val description: String,
    val imageIds: List<UUID>
) {
    fun toCommand(id: Long): UpdateProductCommand {
        return UpdateProductCommand(id, title, price, description, imageIds)

    }

}
