package app.joybox.utils

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper

class JsonUtils {
    companion object {
        fun toJson(obj: Any?): String {
            val mapper = ObjectMapper()
            return try {
                mapper.writeValueAsString(obj)
            } catch (e: JsonProcessingException) {
                throw RuntimeException("Failed to convert object to JSON string", e)
            }
        }
    }
}