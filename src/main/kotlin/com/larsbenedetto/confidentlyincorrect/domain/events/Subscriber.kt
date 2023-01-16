package com.larsbenedetto.confidentlyincorrect.domain.events

import com.larsbenedetto.confidentlyincorrect.domain.PlayerId
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

data class Subscriber(
    val playerId: PlayerId,
    val emitter: SseEmitter
)
