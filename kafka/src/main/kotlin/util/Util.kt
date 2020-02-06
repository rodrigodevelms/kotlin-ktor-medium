package util

import com.fasterxml.jackson.databind.*


fun objectToJson(objects: Any): JsonNode {
    val string = ObjectMapper().writeValueAsString(objects)
    return ObjectMapper().readTree(string)
}

fun <T> toCommnad(json: String, clazz: Class<T>): T {
    return ObjectMapper().readValue(json, clazz)
}
