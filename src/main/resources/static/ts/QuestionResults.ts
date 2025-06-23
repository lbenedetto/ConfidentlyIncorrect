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

    const lobbyScore = questionResults.lobbyScore;
    $("#LobbyScoreDisplay").html(`<p>As a group you scored <strong>${lobbyScore.value.toFixed(2)}</strong>, accumulating <strong>${lobbyScore.cumulativeScore.toFixed(2)}</strong> points so far</p>`);

    // Render the range visualization
    renderRangeVisualization(questionResults);

    let playerList = $("#ScoreList")
    questionResults.scores.forEach(score => {
        playerList.append(getScoreHtml(score))
    })
}

function renderRangeVisualization(questionResults: QuestionResults) {
    const correctAnswer = parseFloat(questionResults.question.answer);
    const scores = questionResults.scores;

    // Find min and max values for the number line
    let minValue = correctAnswer;
    let maxValue = correctAnswer;

    scores.forEach(score => {
        minValue = Math.min(minValue, score.lowerBound);
        maxValue = Math.max(maxValue, score.upperBound);
    });

    // Add some padding to the min and max values
    const padding = (maxValue - minValue) * 0.1;
    minValue = Math.floor(minValue - padding);
    maxValue = Math.ceil(maxValue + padding);

    // Create the number line
    const rangeChart = $("#RangeChart");
    rangeChart.empty();

    // Create a container for the visualization
    const visualizationContainer = $("<div class='visualization-container'></div>");
    rangeChart.append(visualizationContainer);

    // Create the number line
    const numberLine = $("<div class='number-line'></div>");
    visualizationContainer.append(numberLine);

    // Add min, max, and middle labels to the number line
    const minLabel = $(`<div class='number-line-label' style='left: 0%'>${minValue}</div>`);
    const maxLabel = $(`<div class='number-line-label' style='left: 100%'>${maxValue}</div>`);
    const midLabel = $(`<div class='number-line-label' style='left: 50%'>${Math.round((minValue + maxValue) / 2)}</div>`);

    numberLine.append(minLabel);
    numberLine.append(maxLabel);
    numberLine.append(midLabel);

    // Create a container for player ranges
    const playerRangesContainer = $("<div class='player-ranges-container'></div>");
    visualizationContainer.append(playerRangesContainer);

    // Add player ranges
    scores.forEach((score, index) => {
        const lowerPercent = ((score.lowerBound - minValue) / (maxValue - minValue)) * 100;
        const upperPercent = ((score.upperBound - minValue) / (maxValue - minValue)) * 100;
        const width = upperPercent - lowerPercent;
        const color = ScoreDisplay.getColor(score.value);

        // Calculate vertical position (stagger the ranges)
        const verticalOffset = 30 + (index * 25);

        const rangeElement = $(`
            <div class='player-range'
                 style='left: ${lowerPercent}%;
                        width: ${width}%;
                        background-color: ${color};
                        top: ${verticalOffset}px'>
            </div>
            <div class='player-range-label'
                 style='left: ${lowerPercent + (width / 2)}%;
                        top: ${verticalOffset}px'>
                ${score.scorerName}
            </div>
        `);

        playerRangesContainer.append(rangeElement);
    });

    // Calculate the maximum vertical offset for the vertical line
    const maxVerticalOffset = 30 + ((scores.length - 1) * 25) + 20; // Add 20px for the height of the range

    // Add the correct answer marker and vertical line
    const correctAnswerPercent = ((correctAnswer - minValue) / (maxValue - minValue)) * 100;

    // Create a container for the correct answer marker and line
    const correctAnswerContainer = $("<div class='correct-answer-container'></div>");
    visualizationContainer.append(correctAnswerContainer);

    // Add the correct answer dot
    const correctAnswerDot = $(`
        <div class='correct-answer' style='left: ${correctAnswerPercent}%; top: 50%'></div>
    `);
    correctAnswerContainer.append(correctAnswerDot);

    // Add the vertical line
    const verticalLine = $(`
        <div class='correct-answer-line' style='left: ${correctAnswerPercent}%; top: 50%; height: ${maxVerticalOffset}px'></div>
    `);
    correctAnswerContainer.append(verticalLine);

    // Format the correct answer for better readability
    const formattedAnswer = formatNumber(correctAnswer);

    // Add the correct answer label
    const correctAnswerLabel = $(`
        <div class='correct-answer-label' style='left: ${correctAnswerPercent}%'>${formattedAnswer}</div>
    `);
    correctAnswerContainer.append(correctAnswerLabel);
}

/**
 * Formats a number to be more human-readable
 * - Adds thousands separators
 * - Intelligently handles decimal places
 * - Returns a formatted string
 */
function formatNumber(value: number): string {
    // For integers or numbers with very small decimal parts, show as integers
    if (Number.isInteger(value) || Math.abs(value - Math.round(value)) < 0.0001) {
        return value.toLocaleString();
    }

    // For numbers with decimal parts
    // Determine how many decimal places to show (max 4, min 1)
    const decimalPlaces = Math.min(4, Math.max(1,
        // If decimal part starts with zeros, include them plus one significant digit
        value.toString().split('.')[1]?.match(/^0*/)?.length + 1 || 2));

    return value.toLocaleString(undefined, {
        minimumFractionDigits: decimalPlaces,
        maximumFractionDigits: decimalPlaces
    });
}

function getScoreHtml(score: Score) {
    let icon = ScoreDisplay.getIcon(score.value);
    let color = ScoreDisplay.getColor(score.value);
    return `
<tr>
    <td><i class="material-icons" style="color:${color}">${icon}</i></td>
    <td><p>${score.scorerName}</p></td>
    <td><p>${score.value.toFixed(2)}</p></td>
    <td><p>${score.cumulativeScore.toFixed(2)}</p></td>
    <td><p>${formatNumber(score.lowerBound)}</p></td>
    <td><p>${formatNumber(score.upperBound)}</p></td>
</tr>
`;
}
