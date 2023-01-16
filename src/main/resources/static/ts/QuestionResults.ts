import * as $ from "jquery";
import {getQuestionResults, nextQuestion} from "./Api";
import * as Events from "./Events";
import {GameOverEvent, NextQuestionEvent, QuestionResults, Score} from "./Model";
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

    Events.connect(() => {
        console.log("Connection successful, subscribing");
        Events.subscribeToNextQuestion(onNextQuestion);
        Events.subscribeToGameOver(onGameOver);
    })
})

function onNextQuestion(nextQuestion: NextQuestionEvent) {
    sessionStorage.setItem("questionText", nextQuestion.nextQuestionText)
    sessionStorage.setItem("questionId", nextQuestion.nextQuestionId.toString())
    window.location.href = "question"
}

function onGameOver(gameOver: GameOverEvent) {
    window.location.href = "gameOver"
}

function onNextQuestionClicked() {
    nextQuestion();
}

function showQuestionResults(questionResults: QuestionResults) {
    $("#AnswerField").text(questionResults.question.explanation)
    if (questionResults.gameOver) {
        $("#BtnNextQuestion").text("Show Final Results")
    }

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
    <td><p>${score.playerName}</p></td>
    <td><p>${score.value.toFixed(2)}</p></td>
    <td><p>${score.cumulativeScore.toFixed(2)}</p></td>
    <td><p>${score.lowerBound}</p></td>
    <td><p>${score.upperBound}</p></td>
</tr>
`;
}

