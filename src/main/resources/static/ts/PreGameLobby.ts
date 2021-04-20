import {getLobbyDetails} from "./Api"
import * as $ from "jquery";
import {LobbyDetails} from "./Model";

$(() => {
    if (sessionStorage.getItem("isHosting") === "true") {
        $("#BtnStartGame").show()
    } else {
        $("#BtnStartGame").hide()
    }
    getLobbyDetails(showLobbyDetails)
})

function showLobbyDetails(lobbyDetails: LobbyDetails) {
    console.log(lobbyDetails)
    let playerList = $("#PlayerList")
    $("#DisplayLobbyCode").text(lobbyDetails.lobby.id)
    lobbyDetails.players.forEach(player => {
        //TODO: differentiate between participating and spectating
        playerList.append("<li>" + player.name + "</li>")
    })

    let capacity = lobbyDetails.lobby.capacity;
    if (capacity != null) {

    }
}