package com.larsbenedetto.confidentlyincorrect.usecase.service

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.PlayerId
import com.larsbenedetto.confidentlyincorrect.domain.events.Event
import com.larsbenedetto.confidentlyincorrect.domain.events.Subscriber
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.*
import java.util.concurrent.ConcurrentHashMap


@Service
class EventDispatcher {
    private val subscriberMap = ConcurrentHashMap<PlayerId, Subscriber>()
    private val lobbyMap = ConcurrentHashMap<LobbyId, Set<PlayerId>>()

    fun <T : Event> notify(lobbyId: LobbyId, event: T) : T {
        val lobby = lobbyMap[lobbyId] ?: return event;

        val sse = SseEmitter.event()
            .data(event)
            .name(event.getEventType().name)

        lobby.mapNotNull { subscriberMap[it] }
            .map { it.emitter }
            .forEach { emitter -> emitter.send(sse) }
        return event
    }

    fun subscribe(lobbyId: LobbyId, subscriber: Subscriber) {
        val oldSubscriber = subscriberMap[subscriber.playerId]
        if (oldSubscriber != null) unsubscribe(oldSubscriber.playerId)
        subscriberMap[subscriber.playerId] = subscriber



        lobbyMap.computeIfAbsent(lobbyId) { _ -> Collections.synchronizedSet(HashSet())
         }
        lobbyMap.computeIfPresent(lobbyId) { _, value ->
            (value as MutableSet).add(subscriber.playerId)
            value
        }
    }

    fun unsubscribe(playerId: PlayerId): Boolean {
        lobbyMap.forEach { (_, value) -> (value as MutableSet).remove(playerId) }
        subscriberMap[playerId]?.emitter?.complete()
        return subscriberMap.remove(playerId) != null
    }
}