package ru.bullyboo.data.network.converters

import com.google.gson.*
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseSerializer<Model> : JsonSerializer<Model> {

    companion object {
        private const val DATE_MASK = "yyyy-MM-dd'T'HH:mm:ss"
    }

    private val dateFormatter = SimpleDateFormat(DATE_MASK, Locale.getDefault())

    private lateinit var context: JsonSerializationContext

    override fun serialize(
        src: Model,
        typeOfSrc: Type?,
        context: JsonSerializationContext
    ): JsonElement {
        this.context = context

        return serialize(src)
    }

    abstract fun serialize(
        model: Model
    ): JsonElement

    fun getString(jsonObject: JsonObject, key: String): String {
        val jsonElement = jsonObject[key]
        return if (jsonElement is JsonPrimitive) {
            jsonElement.getAsString()
        } else {
            ""
        }
    }

    fun getInteger(jsonObject: JsonObject, key: String): Int {
        val jsonElement = jsonObject[key]
        return (jsonElement as? JsonPrimitive)?.asInt ?: 0
    }

    fun getIntegerFromString(jsonObject: JsonObject, key: String): Int {
        val jsonElement = jsonObject[key]
        if (jsonElement is JsonPrimitive) {
            val asString = jsonElement.getAsString()
            return if (asString.isEmpty()) {
                0
            } else {
                try {
                    asString.toInt()
                } catch (e: NumberFormatException) {
                    0
                }
            }
        }
        return 0
    }

    fun getFloat(jsonObject: JsonObject, key: String): Float {
        val jsonElement = jsonObject[key]
        return (jsonElement as? JsonPrimitive)?.asFloat ?: 0f
    }

    fun getFloatFromString(jsonObject: JsonObject, key: String): Float {
        val jsonElement = jsonObject[key]
        if (jsonElement is JsonPrimitive) {
            val asString = jsonElement.getAsString()
            return if (asString.isEmpty()) {
                0f
            } else {
                try {
                    asString.replace(",", ".").toFloat()
                } catch (e: NumberFormatException) {
                    0f
                }
            }
        }
        return 0f
    }

    fun getFloatOrNullFromString(jsonObject: JsonObject, key: String): Float? {
        if (!jsonObject.has(key)) {
            return null
        }

        val jsonElement = jsonObject[key]
        if (jsonElement is JsonPrimitive) {
            val asString = jsonElement.getAsString()
            return if (asString.isEmpty()) {
                null
            } else {
                try {
                    asString.replace(",", ".").toFloat()
                } catch (e: NumberFormatException) {
                    null
                }
            }
        }
        return null
    }

    fun getDouble(jsonObject: JsonObject, key: String): Double {
        val jsonElement = jsonObject[key]
        return (jsonElement as? JsonPrimitive)?.asDouble ?: 0.0
    }

    fun getDoubleFromString(jsonObject: JsonObject, key: String): Double {
        val jsonElement = jsonObject[key]
        if (jsonElement is JsonPrimitive) {
            val asString = jsonElement.getAsString()
            return if (asString.isEmpty()) {
                0.0
            } else {
                try {
                    asString.replace(",", ".").toDouble()
                } catch (e: NumberFormatException) {
                    0.0
                }
            }
        }
        return 0.0
    }

    fun getBoolean(jsonObject: JsonObject, key: String): Boolean {
        val jsonElement = jsonObject[key]
        return (jsonElement as? JsonPrimitive)?.asBoolean ?: false
    }

    fun getCalendar(jsonObject: JsonObject, key: String): Calendar {
        val date = getString(jsonObject, key)

        val millis = try {
            dateFormatter.parse(date)?.time ?: 0L
        } catch (e: Exception) {
            0L
        }

        return Calendar.getInstance().apply {
            timeInMillis = millis
        }
    }
}