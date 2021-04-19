import * as $ from "jquery";
import { joinLobby } from "./Api";

$("#BtnJoinLobby").on("click", () => {
    let playerName = $("#InputUsernameField").text();
    let lobbyId = $("#InputLobbyCode").text();
    joinLobby(lobbyId, playerName, joinedLobby => {
        let accessToken = joinedLobby.accessToken
        sessionStorage.setItem("accessToken", accessToken)
        sessionStorage.setItem("lobbyId", lobbyId)
        window.location.href = "/lobby"
    })
});