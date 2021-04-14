package com.larsbenedetto.confidentlyincorrect.web.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class SpringMVCConfiguration : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry
            .addResourceHandler(
                "/static/js/**",
                "/static/css/**"
            )
            .addResourceLocations(
                "classpath:/static/js/",
                "classpath:/static/css/"
            )
    }
}