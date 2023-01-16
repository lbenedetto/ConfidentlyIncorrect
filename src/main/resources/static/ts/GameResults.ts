import * as $ from "jquery";
import {getLobbyDetails} from "./Api";
import {Lobby, LobbyDetails, Player} from "./Model";
import * as ScoreDisplay from "./ScoreDisplay";

$(() => {
    let leaveLobbyButton = $("#BtnLeaveLobby");
    leaveLobbyButton.on("click", onLeaveLobbyClicked);

    getLobbyDetails(showLobbyDetails);
})

function onLeaveLobbyClicked() {
    window.location.href = ""
}

function showLobbyDetails(lobbyDetails: LobbyDetails) {
    let playerList = $("#ScoreList")
    lobbyDetails.players
        .filter(player => player.isParticipating)
        .map(player => getScoreHtml(lobbyDetails.lobby, player))
        .forEach(tableRow => playerList.append(tableRow))
}

function getScoreHtml(lobby: Lobby, player: Player): string {
    let color = ScoreDisplay.getColor(player.score / lobby.questionCount);
    return `
<p><span style="text-align: right; color: ${color}">${player.score.toFixed(2)}</span> <span style="text-align: left">${player.name}</span></p>
`;
}