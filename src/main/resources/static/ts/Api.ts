import * as $ from "jquery";
import {CreateLobbyResponse, JoinLobbyResponse, LobbyDetails, QuestionResults} from "./Model"

export function hostLobby(playerName: string, capacity: string, questionLimit: string, resultHandler: (createdLobby: CreateLobbyResponse) => void) {
    post("/api/lobby/v1/",
        {
            "hostName": playerName,
            "isParticipating": true,
            "capacity": capacity,
            "questionLimit": questionLimit
        },
        result => {
            let createdLobby = new CreateLobbyResponse(result.data)
            resultHandler(createdLobby)
        }
    )
}

export function joinLobby(playerName: string, lobbyId: string, resultHandler: (joinedLobby: JoinLobbyResponse) => void) {
    post("/api/lobby/v1/" + lobbyId + "/join",
        {"playerName": playerName},
        result => {
            let joinedLobby = new JoinLobbyResponse(result)
            resultHandler(joinedLobby)
        }
    )
}

function nextQuestion() {
    let lobbyId = sessionStorage.getItem("lobbyId")
    let accessToken = sessionStorage.getItem("accessToken")
    post("/api/lobby/v1/" + lobbyId + "/nextQuestion",
        {"accessToken": accessToken},
        result => {
            // There is no response, instead client will receive a NextQuestionNotification
            // at which point we should navigate
        }
    )
}

export function getLobbyDetails(resultHandler: (lobbyDetails: LobbyDetails) => void) {
    let lobbyId = sessionStorage.getItem("lobbyId")
    post("/api/lobby/v1/" + lobbyId + "/",
        {},
        result => {
            let lobbyDetails = new LobbyDetails(result.data)
            resultHandler(lobbyDetails)
        }
    )
}

function getQuestionResults() {
    let lobbyId = sessionStorage.getItem("lobbyId")
    let questionId = 1 // TODO: Get this from session
    post("/api/lobby/v1/" + lobbyId + "/results/" + questionId + "/",
        {},
        result => {
            let questionResults = new QuestionResults(result.data)
            // TODO: Display question results
        }
    )
}

// function connect() {
//     var socket = new SockJS('/chat');
//     stompClient = Stomp.over(socket);
//     stompClient.connect({}, function (frame) {
//         setConnected(true);
//         console.log('Connected: ' + frame);
//         stompClient.subscribe('/topic/messages', function (messageOutput) {
//             showMessageOutput(JSON.parse(messageOutput.body));
//         });
//     });
// }

function leaveLobby() {
    window.location.href = "/";
}

export function post(url: string, data: object, onSuccess: (data: any) => void) {
    console.log("POST " + url)
    $.ajax({
        type: "POST",
        url: url,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: onSuccess,
        dataType: "json"
    });
}