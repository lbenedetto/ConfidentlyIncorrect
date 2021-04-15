package com.larsbenedetto.confidentlyincorrect.web.config

import org.springframework.beans.factory.InjectionPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import java.util.logging.Logger

@Configuration
class LoggingConfiguration {
    @Bean
    @Scope("prototype")
    fun logger(injectionPoint: InjectionPoint): Logger {
        return Logger.getLogger(
            injectionPoint.methodParameter?.containingClass?.name
                ?: injectionPoint.field?.declaringClass?.name
        )
    }
}