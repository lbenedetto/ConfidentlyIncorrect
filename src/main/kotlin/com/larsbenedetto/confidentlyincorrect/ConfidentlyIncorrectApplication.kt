package com.larsbenedetto.confidentlyincorrect

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker

@SpringBootApplication
@EnableWebSocketMessageBroker
class ConfidentlyIncorrectApplication

fun main(args: Array<String>) {
	runApplication<ConfidentlyIncorrectApplication>(*args)
}
