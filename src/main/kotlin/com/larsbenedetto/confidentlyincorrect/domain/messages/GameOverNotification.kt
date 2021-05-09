package com.larsbenedetto.confidentlyincorrect.domain.messages

class GameOverNotification : Notification {
    override fun getTopicName() = TOPIC_NAME

    companion object {
        const val TOPIC_NAME = "/topic/lobby/{lobbyId}/gameOver"
    }
}
