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
        sessionStorage.setItem("playerId", joinedLobby.playerId)
        sessionStorage.setItem("accessToken", joinedLobby.accessToken)
        sessionStorage.setItem("lobbyId", lobbyId)
        sessionStorage.setItem("isParticipating", isParticipating ? "true" : "false")
        sessionStorage.setItem("isHosting", "false")
        window.location.href = "lobby"
    })
}