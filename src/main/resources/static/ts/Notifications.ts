import {Client, IFrame, Stomp} from "@stomp/stompjs";
import {
    NextQuestionNotification,
    PlayerAnsweredNotification,
    PlayerJoinedNotification
} from "./Model";

let stompClient: Client;

export function connect(onConnect: () => void) {
    stompClient = new Client({
        brokerURL: `ws://localhost:8080/ws`,
        onConnect: (receipt: IFrame) => { onConnect() }
    })
    stompClient.activate();
}

export function subscribeToPlayerJoined(notificationHandler: (playerJoined: PlayerJoinedNotification) => void) {
    let lobbyId = sessionStorage.getItem("lobbyId")
    let endpoint = `/topic/lobby/${lobbyId}/playerJoined`;
    stompClient.subscribe(endpoint, playerJoined => {
        notificationHandler(new PlayerJoinedNotification(playerJoined));
    });
}

export function subscribeToPlayerAnswered(notificationHandler: (playerAnswered: PlayerAnsweredNotification) => void) {
    let lobbyId = sessionStorage.getItem("lobbyId")
    let endpoint = `/topic/lobby/${lobbyId}/playerAnswered`;
    stompClient.subscribe(endpoint, playerAnswered => {
        notificationHandler(new PlayerAnsweredNotification(playerAnswered));
    });
}

export function subscribeToNextQuestion(notificationHandler: (nextQuestion: NextQuestionNotification) => void) {
    let lobbyId = sessionStorage.getItem("lobbyId")
    let endpoint = `/topic/lobby/${lobbyId}/nextQuestion`;
    stompClient.subscribe(endpoint, nextQuestion => {
        notificationHandler(new NextQuestionNotification(nextQuestion));
    });
}