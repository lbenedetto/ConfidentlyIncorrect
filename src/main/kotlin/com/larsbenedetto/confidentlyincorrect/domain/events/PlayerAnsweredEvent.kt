package com.larsbenedetto.confidentlyincorrect.domain.events

data class PlayerAnsweredEvent(
    val playerCount: Int,
    val answerCount: Int
) : Event {
    override fun getEventType() = EventType.PLAYER_ANSWERED
}
