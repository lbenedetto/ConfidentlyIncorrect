import {submitEstimate} from "./Api";
import {PlayerAnsweredEvent, SubmitEstimateRequest} from "./Model";
import * as $ from "jquery";
import * as Events from "./Events";
import * as ScoreDisplay from "./ScoreDisplay";

$(() => {
    let btnSubmitEstimate = $("#BtnSubmitEstimate");
    if (sessionStorage.getItem("isParticipating") == "false") {
        btnSubmitEstimate.hide()
        $("#LowerBoundField").hide();
        $("#UpperBoundField").hide();
    } else {
        btnSubmitEstimate.on("click", onSubmitEstimateClicked);
    }
    $('#QuestionField').text(sessionStorage.getItem("questionText"));
    window.setTimeout(goToResults, 60_000);
});

function goToResults() {
    window.location.href = "results"
}

function goToResultsWithDelay() {
    window.setTimeout(() => {
        window.location.href = "results"
    }, 3000);
}

Events.connect(() => {
    console.log("Connection successful, subscribing");
    Events.subscribeToPlayerAnswered(onPlayerAnswered);
})

function onPlayerAnswered(playerAnswered: PlayerAnsweredEvent) {
    if(playerAnswered.playerCount == playerAnswered.answerCount) {
        goToResultsWithDelay();
    }
    //TODO: Maybe update some indicator of how many other players have answered?
}

function onSubmitEstimateClicked() {
    let lobbyId = sessionStorage.getItem("lobbyId")
    let accessToken = sessionStorage.getItem("accessToken")
    let lowerBoundField = $("#LowerBoundField");
    let upperBoundField = $("#UpperBoundField");
    let lowerBound = lowerBoundField.val() as string;
    let upperBound = upperBoundField.val() as string;

    let request = new SubmitEstimateRequest(lobbyId, accessToken, lowerBound, upperBound)
    submitEstimate(request, response => {
        lowerBoundField.hide();
        upperBoundField.hide();
        $("#BtnSubmitEstimate").hide();
        let color = ScoreDisplay.getColor(response.score);

        let scoreField = $("#ScoreField");

        scoreField.text(response.score.toFixed(2).toString())
            .css("color", color);


        $("#TitleText")
            .html(ScoreDisplay.getExplanationText(response.score))
            .css("color", color);

        if(response.otherAnswersCount == response.otherPlayersCount) {
            goToResultsWithDelay();
        }

        //TODO: Maybe update some indicator of how many other players have answered?
    })
}