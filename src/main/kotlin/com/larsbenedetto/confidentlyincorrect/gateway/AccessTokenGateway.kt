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
    fun findByTokenAndPlayerId(token: String, playerId: Long): Optional<TblAccessToken>
    fun findByToken(token: String): TblAccessToken
}

@Service
class AccessTokenGateway(
    val accessTokenRepository: AccessTokenRepository
) {
    fun createForPlayerId(playerId: PlayerId): AccessToken {
        val tbl = accessTokenRepository.save(
            TblAccessToken(
                playerId = playerId.value,
                token = UUID.randomUUID().toString()
            )
        )
        return AccessToken(tbl.token)
    }

    fun isValid(token: AccessToken, playerId: PlayerId): Boolean {
        return accessTokenRepository.findByTokenAndPlayerId(token.value, playerId.value)
            .isPresent
    }

    fun getPlayerIdByAccessToken(token: AccessToken): PlayerId {
        return PlayerId(accessTokenRepository.findByToken(token.value).playerId)
    }
}