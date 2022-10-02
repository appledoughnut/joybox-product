package app.joybox.api.response

import java.util.UUID

data class AddImageResponse(
    val imageUUIDString: String
) {
    companion object {
        fun from(uuid: UUID): AddImageResponse {
            return AddImageResponse(uuid.toString())
        }
    }
}