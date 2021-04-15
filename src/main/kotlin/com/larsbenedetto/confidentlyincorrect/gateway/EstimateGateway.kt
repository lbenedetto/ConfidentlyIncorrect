package com.larsbenedetto.confidentlyincorrect.gateway

import com.larsbenedetto.confidentlyincorrect.domain.database.Estimate
import com.larsbenedetto.confidentlyincorrect.domain.identity.EstimateId
import com.larsbenedetto.confidentlyincorrect.domain.identity.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.identity.PlayerId
import com.larsbenedetto.confidentlyincorrect.domain.identity.QuestionId
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.*

@Repository
interface EstimateRepository : CrudRepository<Estimate, EstimateId> {
    @Query(
        "SELECT e FROM Estimate e" +
                " WHERE e.lobbyId = :lobbyId" +
                " AND e.questionId = :questionId" +
                " ORDER BY e.score DESC"
    )
    fun findAllByLobbyIdAndQuestionId(
        @Param("lobbyId") lobbyId: LobbyId,
        @Param("questionId") questionId: QuestionId
    ): List<Estimate>

    @Query(
        "SELECT DISTINCT(questionId) FROM Estimate WHERE lobbyId = :lobbyId"
    )
    fun isQuestionUniqueToLobby(
        @Param("lobbyId") lobbyId: LobbyId
    ): Set<QuestionId>

    @Query(
        "SELECT e FROM Estimate e" +
                " WHERE e.lobbyId = :lobbyId" +
                " AND e.questionId = :questionId" +
                " AND e.playerId = :playerId"
    )
    fun findByLobbyIdAndQuestionIdAndPlayerId(
        @Param("lobbyId") lobbyId: LobbyId,
        @Param("questionId") questionId: QuestionId,
        @Param("playerId") playerId: PlayerId
    ): Optional<Estimate>

}

//this is a test for pull requests

@Service
class EstimateGateway(
    var estimateRepository: EstimateRepository
) {

    fun submit(estimate: Estimate) {
        estimateRepository.save(estimate)
    }

    //Still trying to wrap my head around how Springboot and Kotlin work. Probably wrong. But we'll see
    fun getListOfQuestionsFromLobby(lobbyId: LobbyId): Set<QuestionId>{
        return estimateRepository.isQuestionUniqueToLobby(lobbyId)
    }

    fun findByLobbyAndQuestion(lobbyId: LobbyId, questionId: QuestionId): List<Estimate> {
        return estimateRepository.findAllByLobbyIdAndQuestionId(lobbyId, questionId)
    }

    fun findByLobbyAndQuestionAndPlayer(
        lobbyId: LobbyId,
        questionId: QuestionId,
        playerId: PlayerId
    ): Optional<Estimate> {
        return estimateRepository.findByLobbyIdAndQuestionIdAndPlayerId(lobbyId, questionId, playerId)
    }

}