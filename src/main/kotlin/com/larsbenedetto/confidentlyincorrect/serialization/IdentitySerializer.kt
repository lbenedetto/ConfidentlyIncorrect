package com.larsbenedetto.confidentlyincorrect.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.larsbenedetto.confidentlyincorrect.domain.identity.Identity
import org.springframework.lang.Nullable
import java.io.IOException

class IdentitySerializer(t: Class<Identity<*>>) : StdSerializer<Identity<*>>(t) {
    @Throws(IOException::class)
    override fun serialize(
        @Nullable value: Identity<*>?, @Nullable jgen: JsonGenerator,
        @Nullable provider: SerializerProvider
    ) {
        jgen.writeObject(value!!.value)
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}