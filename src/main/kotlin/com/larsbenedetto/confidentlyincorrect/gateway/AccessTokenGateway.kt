package com.larsbenedetto.confidentlyincorrect.gateway

import com.larsbenedetto.confidentlyincorrect.domain.AccessToken
import com.larsbenedetto.confidentlyincorrect.domain.PlayerId
import com.larsbenedetto.confidentlyincorrect.gateway.model.TblAccessToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.*

@Repository
interface AccessTokenRepository : CrudRepository<TblAccessToken, Long> {
    fun findByTokenAndPlayerId(token: AccessToken, playerId: PlayerId): Optional<TblAccessToken>
    fun findByToken(token: AccessToken): TblAccessToken
}

@Service
class AccessTokenGateway(
    val accessTokenRepository: AccessTokenRepository
) {
    fun createForPlayerId(playerId: PlayerId): AccessToken {
        val tbl = accessTokenRepository.save(
            TblAccessToken(
                playerId = playerId,
                token = AccessToken(UUID.randomUUID().toString())
            )
        )
        return tbl.token
    }

    fun isValid(token: AccessToken, playerId: PlayerId): Boolean {
        return accessTokenRepository.findByTokenAndPlayerId(token, playerId)
            .isPresent
    }

    fun getPlayerIdByAccessToken(token: AccessToken): PlayerId {
        return accessTokenRepository.findByToken(token).playerId
    }
}