package com.larsbenedetto.confidentlyincorrect.gateway

import com.larsbenedetto.confidentlyincorrect.domain.entity.Lobby
import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.gateway.model.TblLobby
import com.larsbenedetto.confidentlyincorrect.web.model.EntityLookupFailedException
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Repository
interface LobbyRepository : CrudRepository<TblLobby, String> {
}

@Service
class LobbyGateway(
    val lobbyRepository: LobbyRepository,
) {

    fun save(lobby: Lobby): Lobby {
        val tbl = lobbyRepository.save(fromEntity(lobby))
        return toEntity(tbl)
    }

    fun getById(id: LobbyId): Lobby {
        return lobbyRepository.findById(id.value)
            .map(this::toEntity)
            .orElseThrow { EntityLookupFailedException(Lobby::class.simpleName, id) }
    }

    private fun fromEntity(entity: Lobby): TblLobby {
        return TblLobby(
            id = entity.id.value,
            ownerId = entity.ownerId,
            questionId = entity.questionId,
            capacity = entity.capacity,
            questionCount = entity.questionCount,
            questionLimit = entity.questionLimit,
            questionExpiresAt = entity.questionExpiresAt
        )
    }

    private fun toEntity(tbl: TblLobby): Lobby {
        return Lobby(
            id = LobbyId(tbl.id),
            ownerId = tbl.ownerId,
            questionId = tbl.questionId,
            capacity = tbl.capacity,
            questionCount = tbl.questionCount,
            questionLimit = tbl.questionLimit,
            questionExpiresAt = tbl.questionExpiresAt
        )
    }
}