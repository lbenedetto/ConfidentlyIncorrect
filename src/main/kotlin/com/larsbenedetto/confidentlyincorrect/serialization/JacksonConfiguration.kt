package com.larsbenedetto.confidentlyincorrect.serialization

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.SerializationFeature
import com.larsbenedetto.confidentlyincorrect.domain.identity.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration
class JacksonConfiguration {
    @Bean
    fun jacksonBuilder(): Jackson2ObjectMapperBuilder {
        return Jackson2ObjectMapperBuilder()
            .serializationInclusion(JsonInclude.Include.NON_ABSENT)
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .serializers(
                IdentitySerializer(Identity::class.java),
            ).deserializers(
                IdentityDeserializer(EstimateId::class.java),
                IdentityDeserializer(LobbyId::class.java),
                IdentityDeserializer(PlayerId::class.java),
                IdentityDeserializer(QuestionId::class.java),
            )
    }
}