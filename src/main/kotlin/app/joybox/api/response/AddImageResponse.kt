package app.joybox.api.response

import java.util.*

data class AddImageResponse(
    val uuid: UUID
) {
    companion object {
        fun from(uuid: UUID): AddImageResponse {
            return AddImageResponse(uuid)
        }
    }
}