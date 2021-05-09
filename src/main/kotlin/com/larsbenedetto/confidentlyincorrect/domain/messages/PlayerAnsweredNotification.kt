package com.larsbenedetto.confidentlyincorrect.domain.messages

data class PlayerAnsweredNotification(
    val playerCount: Int,
    val answerCount: Int
) : Notification {
    override fun getTopicName() = TOPIC_NAME
    companion object {
        const val TOPIC_NAME = "/topic/lobby/{lobbyId}/playerAnswered"
    }
}
