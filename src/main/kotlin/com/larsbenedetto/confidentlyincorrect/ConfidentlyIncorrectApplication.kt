package com.larsbenedetto.confidentlyincorrect

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ConfidentlyIncorrectApplication

fun main(args: Array<String>) {
	runApplication<ConfidentlyIncorrectApplication>(*args)
}
