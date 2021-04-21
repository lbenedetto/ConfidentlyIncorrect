import {getLobbyDetails, nextQuestion} from "./Api"
import * as $ from "jquery";
import {LobbyDetails, NextQuestionNotification, Player, PlayerJoinedNotification} from "./Model";
import * as Notifications from "./Notifications";

$(() => {
    let startGameButton = $("#BtnStartGame");
    if (sessionStorage.getItem("isHosting") === "true") {
        startGameButton.on("click", onStartGameClicked)
        startGameButton.show();
    } else {
        startGameButton.hide()
    }
    getLobbyDetails(showLobbyDetails)

    Notifications.connect(() => {
        Notifications.subscribeToPlayerJoined(onPlayerJoined);
        Notifications.subscribeToNextQuestion(onNextQuestion);
    })
})

function onPlayerJoined(playerJoined: PlayerJoinedNotification) {
    $("#PlayerList").append(getPlayerHtml(playerJoined.player));
    updatePlayerCount(playerJoined.playerCount, playerJoined.playerLimit)
}

function onNextQuestion(nextQuestion: NextQuestionNotification) {
    sessionStorage.putItem("questionText", nextQuestion.nextQuestionText)
    sessionStorage.putItem("questionId", nextQuestion.nextQuestionId)
    window.location.href = "/question"
}

function onStartGameClicked() {
    let lobbyId = sessionStorage.getItem("lobbyId")
    let accessToken = sessionStorage.getItem("accessToken")
    nextQuestion(lobbyId, accessToken, () => {
    })
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