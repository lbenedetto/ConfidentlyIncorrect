import * as $ from "jquery";
import {getQuestionResults} from "./Api";
import * as Notifications from "./Notifications";
import {NextQuestionNotification, QuestionResults, Score} from "./Model";
import * as ScoreDisplay from "./ScoreDisplay";

$(() => {
    let startGameButton = $("#BtnNextQuestion");
    if (sessionStorage.getItem("isHosting") === "true") {
        startGameButton.on("click", onNextQuestionClicked)
        startGameButton.show();
    } else {
        startGameButton.hide()
    }
    getQuestionResults(showQuestionResults)

    Notifications.connect(() => {
        console.log("Connection successful, subscribing");
        Notifications.subscribeToNextQuestion(onNextQuestion);
    })
})

function onNextQuestion(nextQuestion: NextQuestionNotification) {
    sessionStorage.setItem("questionText", nextQuestion.nextQuestionText)
    sessionStorage.setItem("questionId", nextQuestion.nextQuestionId.toString())
    window.location.href = "/question"
}

function onNextQuestionClicked() {

}

function showQuestionResults(questionResults: QuestionResults) {
    $("#AnswerField").text(questionResults.question.explanation)

    let playerList = $("#ScoreList")
    questionResults.scores.forEach(score => {
        playerList.append(getScoreHtml(score))
    })
}

function getScoreHtml(score: Score) {
    let icon = ScoreDisplay.getIcon(score.value);
    let color = ScoreDisplay.getColor(score.value);
    return `
<tr>
    <td><i class="material-icons" style="color:${color}">${icon}</i></td>
    <td><p>${score.value.toFixed(2)}</p></td>
    <td><p>${score.cumulativeScore.toFixed(2)}</p></td>
    <td><p>${score.playerName}</p></td>
    <td><p>${score.lowerBound}</p></td>
    <td><p>${score.upperBound}</p></td>
</tr>
`;
}

