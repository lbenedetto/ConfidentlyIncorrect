package com.larsbenedetto.confidentlyincorrect.gateway

import com.larsbenedetto.confidentlyincorrect.domain.EstimateId
import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.PlayerId
import com.larsbenedetto.confidentlyincorrect.domain.QuestionId
import com.larsbenedetto.confidentlyincorrect.domain.entity.Estimate
import com.larsbenedetto.confidentlyincorrect.gateway.model.TblEstimate
import org.springframework.stereotype.Service
import java.util.*

@Service
class EstimateGateway(
    var estimateRepository: EstimateRepository
) {

    fun submit(estimate: Estimate) {
        estimateRepository.save(fromEntity(estimate))
    }

    fun getListOfQuestionsFromLobby(lobbyId: LobbyId): Set<QuestionId> {
        return estimateRepository.isQuestionUniqueToLobby(lobbyId.value)
            .map { QuestionId(it) }
            .toSet()
    }

    fun findByLobbyAndQuestion(lobbyId: LobbyId, questionId: QuestionId): List<Estimate> {
        return estimateRepository.findAllByLobbyIdAndQuestionId(lobbyId.value, questionId.value)
            .map(this::toEntity)
    }

    fun findPlayersEstimateForQuestionInLobby(
        lobbyId: LobbyId,
        questionId: QuestionId,
        playerId: PlayerId
    ): Optional<Estimate> {
        return estimateRepository.findByLobbyIdAndQuestionIdAndPlayerId(
            lobbyId.value,
            questionId.value,
            playerId.value
        ).map(this::toEntity)
    }

    private fun fromEntity(entity: Estimate): TblEstimate {
        return TblEstimate(
            id = entity.id?.value,
            lobbyId = entity.lobbyId.value,
            playerId = entity.playerId.value,
            questionId = entity.questionId.value,
            lowerBound = entity.lowerBound,
            upperBound = entity.upperBound,
            score = entity.score
        )
    }

    private fun toEntity(tbl: TblEstimate): Estimate {
        return Estimate(
            id = EstimateId(tbl.id!!),
            lobbyId = LobbyId(tbl.lobbyId),
            playerId = PlayerId(tbl.playerId),
            questionId = QuestionId(tbl.questionId),
            lowerBound = tbl.lowerBound,
            upperBound = tbl.upperBound,
            score = tbl.score
        )
    }

}