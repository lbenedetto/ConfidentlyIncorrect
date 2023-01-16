package com.larsbenedetto.confidentlyincorrect.web.lobby

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.PlayerId
import com.larsbenedetto.confidentlyincorrect.domain.QuestionId
import com.larsbenedetto.confidentlyincorrect.domain.events.Subscriber
import com.larsbenedetto.confidentlyincorrect.usecase.*
import com.larsbenedetto.confidentlyincorrect.usecase.service.EventDispatcher
import com.larsbenedetto.confidentlyincorrect.web.lobby.model.*
import com.larsbenedetto.confidentlyincorrect.web.model.*
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.time.Duration

@Controller
@RequestMapping("api/lobby/v1")
class LobbyController(
    val createLobby: CreateLobby,
    val getLobbyDetails: GetLobbyDetails,
    val getQuestionsResults: GetQuestionsResults,
    val joinLobby: JoinLobby,
    val nextQuestion: NextQuestion,
    val submitEstimate: SubmitEstimate,
    val eventDispatcher: EventDispatcher
) {
    @PostMapping("/")
    fun createLobby(
        @RequestBody request: CreateLobbyRequest
    ): ResponseEntity<ApiResponse<CreateLobbyResponse>> {
        return ApiResponse.ok(createLobby.execute(request))
    }

    @PostMapping("/{lobbyId}/join")
    fun joinLobby(
        @PathVariable("lobbyId") lobbyId: LobbyId,
        @RequestBody request: JoinLobbyRequest
    ): ResponseEntity<ApiResponse<JoinLobbyResponse>> {
        val response = joinLobby.execute(lobbyId, request)
        return ApiResponse.ok(response)
    }

    @PostMapping("/{lobbyId}")
    fun getLobby(
        @PathVariable("lobbyId") lobbyId: LobbyId
    ): ResponseEntity<ApiResponse<LobbyDetails>> {
        return ApiResponse.ok(getLobbyDetails.execute(lobbyId))
    }

    @PostMapping("/{lobbyId}/results/{questionId}")
    fun getQuestionsResults(
        @PathVariable("lobbyId") lobbyId: LobbyId,
        @PathVariable("questionId") questionId: QuestionId
    ): ResponseEntity<ApiResponse<QuestionResults>> {
        return ApiResponse.ok(getQuestionsResults.execute(lobbyId, questionId))
    }

    @PostMapping("/{lobbyId}/nextQuestion")
    fun nextQuestion(
        @PathVariable("lobbyId") lobbyId: LobbyId,
        @RequestBody request: NextQuestionRequest
    ): ResponseEntity<ApiResponse<EmptyResponse>> {
        nextQuestion.execute(lobbyId, request)
        return ApiResponse.ok()
    }

    @PostMapping("/{lobbyId}/submitEstimate")
    fun submitEstimate(
        @PathVariable("lobbyId") lobbyId: LobbyId,
        @RequestBody request: SubmitEstimateRequest
    ): ResponseEntity<ApiResponse<SubmitEstimateResponse>> {
        val score = submitEstimate.execute(lobbyId, request)
        return ApiResponse.ok(score)
    }

    @GetMapping("/{lobbyId}/subscribe/{playerId}")
    fun subscribe(
        @PathVariable("lobbyId") lobbyId: LobbyId,
        @PathVariable("playerId") playerId: PlayerId,
        response: HttpServletResponse
    ): SseEmitter {
        val emitter = SseEmitter(Duration.ofHours(1).toMillis())
        emitter.onCompletion {
        }
        emitter.onTimeout {
            eventDispatcher.unsubscribe(playerId)
        }
        emitter.onError {
            eventDispatcher.unsubscribe(playerId)
        }
        val subscriber = Subscriber(playerId, emitter)

        eventDispatcher.subscribe(lobbyId, subscriber)

        // Fix to allow SSE through nginx
        response.addHeader("X-Accel-Buffering", "no")
        return emitter
    }
}