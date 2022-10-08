package app.joybox.api.command

import java.util.*

class UpdateProductCommand(
    val id: Long,
    val title: String,
    val price: Int,
    val description: String,
    val imageIds: List<UUID>)
