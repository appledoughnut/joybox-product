package app.joybox.api.command

import java.util.*

data class AddProductCommand(
    val title: String,
    val price: Int,
    val description: String,
    val imagesIds: List<UUID>
)