package com.larsbenedetto.confidentlyincorrect.gateway

import com.larsbenedetto.confidentlyincorrect.domain.database.Lobby
import com.larsbenedetto.confidentlyincorrect.domain.identity.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.identity.PlayerId
import com.larsbenedetto.confidentlyincorrect.web.model.EntityLookupFailedException
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.function.Supplier
import javax.persistence.EntityNotFoundException

@Repository
interface LobbyRepository : CrudRepository<Lobby, LobbyId> {
}

@Service
class LobbyGateway(
    val lobbyRepository: LobbyRepository
) {

    fun createLobby(lobbyId: LobbyId, ownerId: PlayerId): Lobby {
        return lobbyRepository.save(Lobby(lobbyId, ownerId, questionId = null))
    }

    fun save(lobby: Lobby) {
        lobbyRepository.save(lobby)
    }

    fun getById(id: LobbyId): Lobby {
        return lobbyRepository.findById(id)
            .orElseThrow { EntityLookupFailedException(Lobby::class.simpleName, id) }
    }
}