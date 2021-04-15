package com.larsbenedetto.confidentlyincorrect.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.larsbenedetto.confidentlyincorrect.domain.Identity
import org.springframework.lang.Nullable
import java.io.IOException
import java.lang.reflect.InvocationTargetException

class IdentityDeserializer<V, ID : Identity<V>>(id: Class<ID>) :
    StdDeserializer<ID>(id) {
    @Nullable
    @Throws(IOException::class)
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ID {
        if (p.currentToken == JsonToken.START_OBJECT) {
            val map = p.readValueAs(
                LinkedHashMap::class.java
            )
            if (map.containsKey("value")) {
                return newInstance(map["value"].toString())
            }
        }
        val idValue = p.valueAsString
        return newInstance(idValue)
    }

    private fun newInstance(value: String): ID {
        return try {
            val constructor = (_valueClass as Class<ID>).getConstructor(
                String::class.java
            )
            constructor.newInstance(value)
        } catch (e: NoSuchMethodException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        } catch (e: InstantiationException) {
            throw RuntimeException(e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException(e)
        }
    }
}