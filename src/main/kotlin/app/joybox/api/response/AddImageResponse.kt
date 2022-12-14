package app.joybox.api.response

import java.util.*

data class AddImageResponse(
    val id: UUID
) {
    companion object {
        fun from(id: UUID): AddImageResponse {
            return AddImageResponse(id)
        }
    }
}