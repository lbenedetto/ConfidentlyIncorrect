package com.larsbenedetto.confidentlyincorrect.gateway

import com.larsbenedetto.confidentlyincorrect.domain.database.Question
import com.larsbenedetto.confidentlyincorrect.domain.identity.QuestionId
import com.larsbenedetto.confidentlyincorrect.web.model.EntityLookupFailedException
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import kotlin.random.Random

@Repository
interface QuestionRepository : CrudRepository<Question, QuestionId> {
}

@Service
class QuestionGateway(
    val questionRepository: QuestionRepository
) {
    fun getById(id: QuestionId): Question {
        return questionRepository.findById(id)
            .orElseThrow { EntityLookupFailedException(Question::class.simpleName, id) }
    }

    fun getRandomQuestion(): Question {
        val size = questionRepository.count().toInt()
        val questionId = QuestionId(Random.nextInt(1, size + 1).toLong())
        return getById(questionId)
    }
}