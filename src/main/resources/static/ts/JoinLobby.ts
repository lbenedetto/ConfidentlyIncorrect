import * as $ from "jquery";
import {joinLobby} from "./Api";

$(() => {
    $("#BtnJoinLobby").on("click", onJoinLobbyClicked);
});

function onJoinLobbyClicked() {
    let playerName = $("#InputUsernameField").val() as string;
    let lobbyId = $("#InputLobbyCode").val() as string;
    let isParticipating = $("#SwitchParticipating").is(':checked');
    joinLobby(lobbyId, playerName, isParticipating, joinedLobby => {
        let accessToken = joinedLobby.accessToken
        sessionStorage.setItem("accessToken", accessToken)
        sessionStorage.setItem("lobbyId", lobbyId)
        sessionStorage.setItem("isParticipating", isParticipating ? "true" : "false")
        window.location.href = "/lobby"
    })
}