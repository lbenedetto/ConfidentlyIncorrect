import * as $ from "jquery";
import {
    CreateLobbyRequest,
    CreateLobbyResponse,
    JoinLobbyResponse,
    LobbyDetails,
    QuestionResults,
    SubmitEstimateRequest,
    SubmitEstimateResponse
} from "./Model"

export function hostLobby(request: CreateLobbyRequest, resultHandler: (createdLobby: CreateLobbyResponse) => void) {
    post("/api/lobby/v1/",
        {
            "hostName": request.playerName,
            "isParticipating": request.isParticipating,
            "capacity": request.capacity,
            "questionLimit": request.questionLimit
        },
        result => {
            let createdLobby = new CreateLobbyResponse(result.data)
            resultHandler(createdLobby)
        }
    )
}

export function joinLobby(lobbyId: string, playerName: string, isParticipating: boolean, resultHandler: (joinedLobby: JoinLobbyResponse) => void) {
    post(`/api/lobby/v1/${lobbyId}/join`,
        {
            "playerName": playerName,
            "isParticipating": isParticipating
        },
        result => {
            let joinedLobby = new JoinLobbyResponse(result.data)
            resultHandler(joinedLobby)
        }
    )
}

export function nextQuestion() {
    let lobbyId = sessionStorage.getItem("lobbyId")
    let accessToken = sessionStorage.getItem("accessToken")
    post(`/api/lobby/v1/${lobbyId}/nextQuestion`,
        {"accessToken": accessToken},
        result => {
            // There is nothing in the response, instead client will receive a NextQuestionNotification
            // at which point we should navigate
        }
    )
}

export function getLobbyDetails(resultHandler: (lobbyDetails: LobbyDetails) => void) {
    let lobbyId = sessionStorage.getItem("lobbyId")
    post(`/api/lobby/v1/${lobbyId}/`,
        {},
        result => {
            let lobbyDetails = new LobbyDetails(result.data)
            resultHandler(lobbyDetails)
        }
    )
}

export function getQuestionResults(resultHandler: (result: QuestionResults) => void) {
    let lobbyId = sessionStorage.getItem("lobbyId")
    let questionId = sessionStorage.getItem("questionId")
    post(`/api/lobby/v1/${lobbyId}/results/${questionId}`,
        {},
        result => {
            let questionResults = new QuestionResults(result.data)
            resultHandler(questionResults)
        }
    )
}

export function submitEstimate(request: SubmitEstimateRequest, responseHandler: (response: SubmitEstimateResponse) => void) {
    post("/api/lobby/v1/" + request.lobbyId + "/submitEstimate",
        {
            "accessToken": request.accessToken,
            "lowerBound": request.lowerBound,
            "upperBound": request.upperBound
        },
        result => {
            responseHandler(new SubmitEstimateResponse(result.data))
        }
    )
}

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
        success: data => {
            console.log(data);
            onSuccess(data);
        },
        error: data => {
            console.log(data.responseJSON.error);
            alert(data.responseJSON.error.message);
        },
        dataType: "json"
    });
}