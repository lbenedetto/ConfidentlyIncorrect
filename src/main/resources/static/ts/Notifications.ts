import {Client, IFrame, Stomp} from "@stomp/stompjs";
import {
    GameOverNotification,
    NextQuestionNotification,
    PlayerAnsweredNotification,
    PlayerJoinedNotification
} from "./Model";

let stompClient: Client;

export function connect(onConnect: () => void) {
    let hostname = window.location.hostname
    stompClient = new Client({
        brokerURL: `ws://${hostname}:8080/ws`,
        onConnect: (receipt: IFrame) => { onConnect() }
    })
    stompClient.activate();
}

export function subscribeToPlayerJoined(notificationHandler: (playerJoined: PlayerJoinedNotification) => void) {
    let lobbyId = sessionStorage.getItem("lobbyId")
    let endpoint = `/topic/lobby/${lobbyId}/playerJoined`;
    stompClient.subscribe(endpoint, callback => {
        console.log(callback)
        notificationHandler(new PlayerJoinedNotification(JSON.parse(callback.body)));
    });
}

export function subscribeToPlayerAnswered(notificationHandler: (playerAnswered: PlayerAnsweredNotification) => void) {
    let lobbyId = sessionStorage.getItem("lobbyId")
    let endpoint = `/topic/lobby/${lobbyId}/playerAnswered`;
    stompClient.subscribe(endpoint, callback => {
        console.log(callback)
        notificationHandler(new PlayerAnsweredNotification(JSON.parse(callback.body)));
    });
}

export function subscribeToNextQuestion(notificationHandler: (nextQuestion: NextQuestionNotification) => void) {
    let lobbyId = sessionStorage.getItem("lobbyId")
    let endpoint = `/topic/lobby/${lobbyId}/nextQuestion`;
    stompClient.subscribe(endpoint, callback => {
        console.log(callback)
        notificationHandler(new NextQuestionNotification(JSON.parse(callback.body)));
    });
}

export function subscribeToGameOver(notificationHandler: (gameOver: GameOverNotification) => void) {
    let lobbyId = sessionStorage.getItem("lobbyId")
    let endpoint = `/topic/lobby/${lobbyId}/gameOver`;
    stompClient.subscribe(endpoint, callback => {
        console.log(callback)
        notificationHandler(new GameOverNotification(JSON.parse(callback.body)));
    });
}