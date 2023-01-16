import {
    GameOverEvent,
    NextQuestionEvent,
    PlayerAnsweredEvent,
    PlayerJoinedEvent
} from "./Model";

let eventSource: EventSource;

export function connect(onConnect: () => void) {
    let playerId = sessionStorage.getItem("playerId")
    let lobbyId = sessionStorage.getItem("lobbyId")
    eventSource = new EventSource(`api/lobby/v1/${lobbyId}/subscribe/${playerId}`)
    onConnect()
}

export function subscribeToPlayerJoined(eventHandler: (playerJoined: PlayerJoinedEvent) => void) {
    eventSource.addEventListener("PLAYER_JOINED", (event) => {
        console.log(event)
        eventHandler(new PlayerJoinedEvent(JSON.parse(event.data)))
    })
}

export function subscribeToPlayerAnswered(eventHandler: (playerAnswered: PlayerAnsweredEvent) => void) {
    eventSource.addEventListener("PLAYER_ANSWERED", (event) => {
        console.log(event)
        eventHandler(new PlayerAnsweredEvent(JSON.parse(event.data)))
    })
}

export function subscribeToNextQuestion(eventHandler: (nextQuestion: NextQuestionEvent) => void) {
    eventSource.addEventListener("NEXT_QUESTION", (event) => {
        console.log(event)
        eventHandler(new NextQuestionEvent(JSON.parse(event.data)))
    })
}

export function subscribeToGameOver(eventHandler: (gameOver: GameOverEvent) => void) {
    eventSource.addEventListener("GAME_OVER", (event) => {
        console.log(event)
        eventHandler(new GameOverEvent(JSON.parse(event.data)))
    })
}