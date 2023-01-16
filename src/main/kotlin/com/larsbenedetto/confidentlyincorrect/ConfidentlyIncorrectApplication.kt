package com.larsbenedetto.confidentlyincorrect

import org.springframework.boot.ResourceBanner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.io.ClassPathResource

@SpringBootApplication
class ConfidentlyIncorrectApplication

fun main(args: Array<String>) {
	val app = SpringApplication(ConfidentlyIncorrectApplication::class.java)
	val context: ConfigurableApplicationContext = app.run(*args)

	ResourceBanner(ClassPathResource("splash.txt")).printBanner(
		context.environment,
		ConfidentlyIncorrectApplication::class.java,
		System.out
	)
}
