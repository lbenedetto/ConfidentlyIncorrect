import * as $ from "jquery";
import {joinLobby} from "./Api";

$(() => {
    console.log("document ready")
    registerClickHandler();
});

function registerClickHandler() {
    $("#BtnJoinLobby").on("click", () => {
        console.log("clicked")
        onJoinLobbyClicked();
    });
}

function onJoinLobbyClicked() {
    let playerName = $("#InputUsernameField").val() as string;
    let lobbyId = $("#InputLobbyCode").val() as string;
    let isParticipating = $("#SwitchParticipating").is(':checked');
    joinLobby(lobbyId, playerName, isParticipating, joinedLobby => {
        console.log(joinedLobby)
        let accessToken = joinedLobby.accessToken
        sessionStorage.setItem("accessToken", accessToken)
        sessionStorage.setItem("lobbyId", lobbyId)
        window.location.href = "/lobby"
    })
}