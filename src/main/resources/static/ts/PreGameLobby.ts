import {getLobbyDetails, nextQuestion} from "./Api"
import * as $ from "jquery";
import {LobbyDetails, NextQuestionEvent, Player, PlayerJoinedEvent} from "./Model";

import * as Events from "./Events";

$(() => {
    let startGameButton = $("#BtnStartGame");
    if (sessionStorage.getItem("isHosting") === "true") {
        startGameButton.on("click", onStartGameClicked)
        startGameButton.show();
    } else {
        startGameButton.hide()
    }
    getLobbyDetails(showLobbyDetails)

    Events.connect(() => {
        console.log("Connection successful, subscribing");
        Events.subscribeToPlayerJoined(onPlayerJoined);
        Events.subscribeToNextQuestion(onNextQuestion);
    })
})

function onPlayerJoined(playerJoined: PlayerJoinedEvent) {
    $("#PlayerList").append(getPlayerHtml(playerJoined.player));
    updatePlayerCount(playerJoined.playerCount, playerJoined.playerLimit)
}

function onNextQuestion(nextQuestion: NextQuestionEvent) {
    sessionStorage.setItem("questionText", nextQuestion.nextQuestionText)
    sessionStorage.setItem("questionId", nextQuestion.nextQuestionId.toString())
    window.location.href = "question"
}

function onStartGameClicked() {
    nextQuestion();
}

function showLobbyDetails(lobbyDetails: LobbyDetails) {
    console.log(lobbyDetails)
    let playerList = $("#PlayerList")
    $("#DisplayLobbyCode").text(lobbyDetails.lobby.id)
    lobbyDetails.players.forEach(player => {
        playerList.append(getPlayerHtml(player))
    })

    updatePlayerCount(lobbyDetails.players.length, lobbyDetails.lobby.capacity)
}

function updatePlayerCount(playerCount: any, playerLimit: any) {
    playerLimit = playerLimit ? playerLimit.toString() : "∞"
    playerCount = playerCount.toString()
    let indicator = $("#TxtWaitingIndicator");
    if (playerCount == playerLimit) {
        indicator.text("Lobby full")
    } else {
        indicator.text(`Waiting for other players (${playerCount}/${playerLimit})`)
    }
}

function getPlayerHtml(player: Player) {
    return `
<li>
    <i class="material-icons">${player.isParticipating ? "person" : "visibility"}</i>
    <p>${player.name}</p>
</li>
`;
}