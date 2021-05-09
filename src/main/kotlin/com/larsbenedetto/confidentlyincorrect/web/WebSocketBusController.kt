package com.larsbenedetto.confidentlyincorrect.web

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.messages.GameOverNotification
import com.larsbenedetto.confidentlyincorrect.domain.messages.NextQuestionNotification
import com.larsbenedetto.confidentlyincorrect.domain.messages.PlayerAnsweredNotification
import com.larsbenedetto.confidentlyincorrect.domain.messages.PlayerJoinedNotification
import com.larsbenedetto.confidentlyincorrect.web.model.ApiResponse
import com.larsbenedetto.confidentlyincorrect.web.model.EmptyResponse
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.stereotype.Controller

@Controller
class WebSocketBusController() {

    @SubscribeMapping(NextQuestionNotification.TOPIC_NAME)
    fun nextQuestion(
        @DestinationVariable("lobbyId") lobbyId: LobbyId
    ): ResponseEntity<ApiResponse<EmptyResponse>> {
        return ApiResponse.ok()
    }

    @SubscribeMapping(PlayerAnsweredNotification.TOPIC_NAME)
    fun playerAnswered(
        @DestinationVariable("lobbyId") lobbyId: LobbyId
    ): ResponseEntity<ApiResponse<EmptyResponse>> {
        return ApiResponse.ok()
    }

    @SubscribeMapping(PlayerJoinedNotification.TOPIC_NAME)
    fun playerJoined(
        @DestinationVariable("lobbyId") lobbyId: LobbyId
    ): ResponseEntity<ApiResponse<EmptyResponse>> {
        return ApiResponse.ok()
    }

    @SubscribeMapping(GameOverNotification.TOPIC_NAME)
    fun gameOver(
        @DestinationVariable("lobbyId") lobbyId: LobbyId
    ): ResponseEntity<ApiResponse<EmptyResponse>> {
        return ApiResponse.ok()
    }
}