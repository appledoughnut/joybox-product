package app.joybox.domain.product

import java.util.*

data class AddProductCommand(
    val title: String,
    val price: Int,
    val description: String,
    val imageIds: List<UUID>
)