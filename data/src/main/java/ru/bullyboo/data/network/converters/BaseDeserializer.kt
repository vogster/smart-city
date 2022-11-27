package ru.bullyboo.data.network.converters

import com.google.gson.*
import ru.bullyboo.core.enums.DateMask
import ru.bullyboo.core.utils.DateConverter
import java.lang.reflect.Type
import java.util.*
import kotlin.reflect.KClass

abstract class BaseDeserializer<Model> : JsonDeserializer<Model> {

    private lateinit var context: JsonDeserializationContext

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Model {
        this.context = context

        return deserialize(json.asJsonObject)
    }

    abstract fun deserialize(
        json: JsonObject
    ): Model

    fun getString(jsonObject: JsonObject, key: String): String {
        val jsonElement = jsonObject[key]
        return if (jsonElement is JsonPrimitive) {
            jsonElement.getAsString()
        } else {
            ""
        }
    }

    fun getNullableString(jsonObject: JsonObject, key: String): String? {
        val jsonElement = jsonObject[key]
        return if (jsonElement is JsonPrimitive) {
            jsonElement.getAsString()
        } else {
            null
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

    fun getNullableDouble(jsonObject: JsonObject, key: String): Double? {
        val jsonElement = jsonObject[key]
        return (jsonElement as? JsonPrimitive)?.asDouble
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

    fun getNullableBoolean(jsonObject: JsonObject, key: String): Boolean? {
        val jsonElement = jsonObject[key]
        return (jsonElement as? JsonPrimitive)?.asBoolean
    }

    fun <M : Any> getObject(
        classM: KClass<M>,
        jsonObject: JsonObject,
        key: String
    ): M {
        return Deserializer(classM.java).deserialize(context, jsonObject[key])!!
    }

    fun <M : Enum<M>> getEnum(
        classM: KClass<M>,
        jsonObject: JsonObject,
        key: String,
        defaultValue: M
    ): M {
        return getNullableObject(classM, jsonObject, key)
            ?: defaultValue
    }

    fun <M : Any> getList(
        classM: KClass<M>,
        jsonObject: JsonObject,
        key: String
    ): List<M> {
        return try {
            jsonObject[key].asJsonArray.map {
                Deserializer(classM.java).deserialize(context, it)!!
            }
        } catch (e: Exception) {
            listOf()
        }
    }

    fun getListString(
        jsonObject: JsonObject,
        key: String
    ): List<String> {
        return try {
            jsonObject[key].asJsonArray.map {
                it.asString
            }
        } catch (e: Exception) {
            listOf()
        }
    }

    fun <M : Any> getNullableObject(
        classM: KClass<M>,
        jsonObject: JsonObject,
        key: String?
    ): M? {
        return Deserializer(classM.java).deserialize(context, jsonObject[key])
    }

    fun getCalendar(
        jsonObject: JsonObject,
        key: String,
        mask: DateMask
    ): Calendar {
        val date = getString(jsonObject, key)

        val millis = try {
            DateConverter.parse(date, mask)
        } catch (e: Exception) {
            0L
        }

        return Calendar.getInstance().apply {
            timeInMillis = millis
        }
    }

    fun getNullableCalendar(
        jsonObject: JsonObject,
        key: String,
        mask: DateMask
    ): Calendar? {
        val date = getString(jsonObject, key)

        if (date.isEmpty()) {
            return null
        }

        val millis = try {
            DateConverter.parse(date, mask)
        } catch (e: Exception) {
            0L
        }

        return Calendar.getInstance().apply {
            timeInMillis = millis
        }
    }

    fun getCurrency(jsonObject: JsonObject, key: String): Currency {
        return try {
            Currency.getInstance(getString(jsonObject, key))
        } catch (e: Exception) {
            Currency.getInstance(Locale.getDefault())
        }
    }

    internal inner class Deserializer<O>(private val classO: Class<O>) {

        fun deserialize(context: JsonDeserializationContext, element: JsonElement?): O? {
            return context.deserialize(element, classO)
        }
    }
}