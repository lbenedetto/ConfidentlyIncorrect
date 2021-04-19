import {post} from "./Api";
import {SubmitEstimateResponse} from "./Model";

function submitEstimate() {
    let lobbyId = sessionStorage.getItem("lobbyId")
    let accessToken = sessionStorage.getItem("accessToken")
    let lowerBound = "asdf" // TODO: Get this from the UI
    let upperBound = "asdf" // TODO: Get this from the UI
    post("/api/lobby/v1/" + lobbyId + "/submitEstimate",
        {
            "accessToken": accessToken,
            "lowerBound": lowerBound,
            "upperBound": upperBound
        },
        result => {
            let response = new SubmitEstimateResponse(result.data.score)
            let score = response.score
            // TODO: Display the score in the UI
            // Update the answer indicator
            // Disable editing of bounds
        }
    )
}