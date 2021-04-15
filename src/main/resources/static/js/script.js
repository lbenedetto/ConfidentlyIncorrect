function hostLobby() {
    let playerName = $("#InputUsernameField").text();
    post("/api/lobby/v1/",
        {
            "hostName": playerName,
            "isParticipating": true,
            "capacity": null, // TODO: Set this via UI
            "questionLimit": 20 // TODO: Set this via UI
        },
        result => {
            let createdLobby = new CreateLobbyResponse(result.data)
            let accessToken = createdLobby.accessToken
            let lobbyId = createdLobby.lobbyId
            // TODO:
            //  1. Store accessToken, lobbyId in session
            //  2. Navigate to the pre game lobby page
        }
    )
}

function joinLobby() {
    let playerName = $("#InputUsernameField").text();
    let lobbyId = "asdf" // TODO: Get this from the UI
    post("/api/lobby/v1/" + lobbyId + "/join",
        {"playerName": playerName},
        result => {
            let joinedLobby = new JoinLobbyResponse(result)
            let accessToken = joinedLobby.accessToken
            // TODO:
            //  1. Store accessToken, lobbyId in session
            //  2. Navigate to the pre game lobby page
        }
    )
}

function nextQuestion() {
    let lobbyId = "asdf" // TODO: Get this from the session
    let accessToken = "asdf" // TODO: Get this from the session
    post("/api/lobby/v1/" + lobbyId + "/nextQuestion",
        {"accessToken": accessToken},
        result => {
            // There is no response, instead client will receive a NextQuestionNotification
            // at which point we should navigate
        }
    )
}

function getLobbyDetails() {
    let lobbyId = "asdf" // TODO: Get this from the session
    post("/api/lobby/v1/" + lobbyId + "/",
        {},
        result => {
            let lobbyDetails = new LobbyDetails(result.data)
            // TODO: Navigate the user to the view question page
            // Players who aren't hosting will hopefully receive a stomp notification
        }
    )
}

function getQuestionResults() {
    let lobbyId = "asdf" // TODO: Get this from the session
    let questionId = 1 // TODO: Get this from session
    post("/api/lobby/v1/" + lobbyId + "/results/" + questionId + "/",
        {},
        result => {
            let questionResults = new QuestionResults(result.data)
            // TODO: Display question results
        }
    )
}

function submitEstimate() {
    let lobbyId = "asdf" // TODO: Get this from the session
    let accessToken = "accessToken" // TODO: Get this from the session
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

function connect() {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (messageOutput) {
            showMessageOutput(JSON.parse(messageOutput.body));
        });
    });
}

function leaveLobby() {
    window.location.href = "/";
}

function post(url, data, onSuccess) {
    console.log("POST " + url)
    $.ajax({
        type: "POST",
        url: url,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: onSuccess,
        dataType: "json"
    });
}