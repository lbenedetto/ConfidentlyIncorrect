package com.larsbenedetto.confidentlyincorrect.web

import com.larsbenedetto.confidentlyincorrect.domain.identity.LobbyId
import com.larsbenedetto.confidentlyincorrect.web.model.ApiResponse
import com.larsbenedetto.confidentlyincorrect.web.model.EmptyResponse
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.stereotype.Controller

@Controller
class WebSocketBusController() {

    @SubscribeMapping("/topic/estimates/{lobbyId}")
    fun estimatesTopic(
        @DestinationVariable lobbyId: LobbyId
    ): ResponseEntity<ApiResponse<EmptyResponse>> {
        return ApiResponse.ok()
    }
}