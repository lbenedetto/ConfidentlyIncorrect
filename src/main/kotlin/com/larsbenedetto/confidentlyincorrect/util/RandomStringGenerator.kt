package com.larsbenedetto.confidentlyincorrect.util

import kotlin.random.Random

object RandomStringGenerator {
    fun generate(codeLength: Int, allowedChars: String): String {
        val builder = StringBuilder()
        val allowedCharsLength = allowedChars.length
        for (i in 0 until codeLength) {
            val index = Random.nextInt(allowedCharsLength)
            builder.append(allowedChars[index])
        }
        return builder.toString()
    }
}