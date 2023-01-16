import * as $ from "jquery";
import {hostLobby} from "./Api";
import {CreateLobbyRequest} from "./Model";

$(() => {
    $("#BtnHostLobby").on("click", onHostLobbyClicked);
});

function onHostLobbyClicked() {
    let request = new CreateLobbyRequest(
        $("#InputUsernameField").val() as string,
        $("#SwitchParticipating").is(':checked'),
        $("#InputCapacityField").val() as string,
        $("#InputQuestionLimitField").val() as string
    )
    hostLobby(request, createdLobby => {
        let accessToken = createdLobby.accessToken
        let lobbyId = createdLobby.lobbyId
        sessionStorage.setItem("accessToken", accessToken)
        sessionStorage.setItem("lobbyId", lobbyId)
        sessionStorage.setItem("isHosting", "true")
        sessionStorage.setItem("isParticipating", request.isParticipating ? "true" : "false")
        window.location.href = "lobby"
    })
}

