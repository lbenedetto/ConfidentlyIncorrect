package com.larsbenedetto.confidentlyincorrect.gateway

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.gateway.model.TblQuestion
import com.larsbenedetto.confidentlyincorrect.domain.QuestionId
import com.larsbenedetto.confidentlyincorrect.domain.entity.Question
import com.larsbenedetto.confidentlyincorrect.web.model.EntityLookupFailedException
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import kotlin.random.Random

@Repository
interface QuestionRepository : CrudRepository<TblQuestion, Long> {
}

@Service
class QuestionGateway(
    val questionRepository: QuestionRepository,
    val estimateGateway: EstimateGateway
) {
    fun getById(id: QuestionId): Question {
        return questionRepository.findById(id.value)
            .map(this::toEntity)
            .orElseThrow { EntityLookupFailedException(TblQuestion::class.simpleName, id) }
    }

    fun getRandomQuestion(lobbyId: LobbyId): Question {
        val size = questionRepository.count().toInt()
        val alreadyAskedQuestions = estimateGateway.getListOfQuestionsFromLobby(lobbyId)

        var questionId: QuestionId
        do {
            questionId = QuestionId(Random.nextInt(1, size + 1).toLong())
        } while (alreadyAskedQuestions.contains(questionId))

        return getById(questionId)
    }

    fun toEntity(tbl: TblQuestion) : Question {
        return Question(
            id = tbl.id,
            scoringType = tbl.scoringType,
            text = tbl.text,
            answer = tbl.answer,
            category = tbl.category,
            expirationDate = tbl.expirationDate
        )
    }
}