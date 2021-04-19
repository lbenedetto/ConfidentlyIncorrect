import * as $ from "jquery";
import {hostLobby} from "./Api";

$(() => {
    registerClickHandler();
});

function registerClickHandler() {
    $("#BtnHostLobby").on("click", () => {
        onHostLobbyClicked();
    });
}

function onHostLobbyClicked() {
    let playerName = $("#InputUsernameField").val() as string;
    let participating = $("#SwitchParticipating").is(':checked');
    let capacity = $("#InputCapacityField").val() as string
    if (capacity === "") {
        capacity = "10";
    }
    let questionLimit = $("#InputQuestionLimitField").val() as string
    if (questionLimit === "") {
        questionLimit = "20";
    }
    hostLobby(playerName, capacity, questionLimit, createdLobby => {
        let accessToken = createdLobby.accessToken
        let lobbyId = createdLobby.lobbyId
        sessionStorage.setItem("accessToken", accessToken)
        sessionStorage.setItem("lobbyId", lobbyId)
        sessionStorage.setItem("isHosting", "true")
        window.location.href = "/lobby"
    })
}

