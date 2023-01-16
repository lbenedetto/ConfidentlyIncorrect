package com.larsbenedetto.confidentlyincorrect.domain.events

import com.larsbenedetto.confidentlyincorrect.domain.entity.Player

data class PlayerJoinedEvent(
    val player: Player,
    val playerCount: Int,
    val playerLimit: Int?
) : Event {
    override fun getEventType() = EventType.PLAYER_JOINED
}