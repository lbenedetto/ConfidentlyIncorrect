package com.larsbenedetto.confidentlyincorrect.domain.messages

import com.larsbenedetto.confidentlyincorrect.domain.entity.Player

data class PlayerJoinedNotification(
    val player: Player,
    val playerCount: Int,
    val playerLimit: Int?
) : Notification {
    override fun getTopicName() = TOPIC_NAME
    companion object {
        const val TOPIC_NAME = "/topic/lobby/{lobbyId}/playerJoined"
    }
}