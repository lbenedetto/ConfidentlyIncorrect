import {getLobbyDetails, nextQuestion} from "./Api"
import * as $ from "jquery";
import {LobbyDetails} from "./Model";

$(() => {
    let startGameButton = $("#BtnStartGame");
    if (sessionStorage.getItem("isHosting") === "true") {
        startGameButton.on("click", onStartGameClicked)
        startGameButton.show();
    } else {
        startGameButton.hide()
    }
    getLobbyDetails(showLobbyDetails)

})

function onStartGameClicked() {
    let lobbyId = sessionStorage.getItem("lobbyId")
    let accessToken = sessionStorage.getItem("accessToken")
    nextQuestion(lobbyId, accessToken, () => {
        // TODO: Should probably be handled via STOMP notification, just like for all the other clients
        window.location.href = "/question"
    })
}

function showLobbyDetails(lobbyDetails: LobbyDetails) {
    console.log(lobbyDetails)
    let playerList = $("#PlayerList")
    $("#DisplayLobbyCode").text(lobbyDetails.lobby.id)
    lobbyDetails.players.forEach(player => {
        playerList.append(`
<li>
    <i class="material-icons">${player.isParticipating ? "person" : "visibility"}</i>
    <p>${player.name}</p>
</li>
`)
    })

    let capacity = lobbyDetails.lobby.capacity;
    let capacityString = capacity ? capacity.toString() : "∞"
    let playerCount = lobbyDetails.players.length.toString();
    let indicator = $("#TxtWaitingIndicator");

    if (playerCount == capacityString) {
        indicator.text("Lobby full")
    } else {
        indicator.text(`Waiting for other players (${playerCount}/${capacityString})`)
    }
}