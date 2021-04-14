function hostGame() {
    let playerName = $("#InputUsernameField").text();
    post("/api/lobby/v1/",
        {
            "hostName": playerName,
            "isParticipating": true
        },
        result => {
            let playerId = result.data.ownerId
            let lobbyCode = result.data.id
            // TODO: store these in the session
        }
    )
}

function joinGame() {
    let playerName = $("#InputUsernameField").text();
    let lobbyId = "asdf" // TODO: Get this from the UI
    post("/api/lobby/v1/" + lobbyId + "/join",
        { "playerName": playerName },
        result => {
            let playerId = result.data.id
            // TODO: store this in the session
        }
    )
}

function startGame() {
    let lobbyId = "asdf" // TODO: Get this from the session
    let playerId = "asdf" // TODO: Get this from the session
    post("/api/lobby/v1/" + lobbyId + "/start",
        { "playerId": playerId },
        result => {
            // TODO: Navigate the user to the view question page
            // Players who aren't hosting will hopefully receive a stomp notification
        }
    )
}

function submitEstimate() {
    let lobbyId = "asdf" // TODO: Get this from the session
    let playerId = "asdf" // TODO: Get this from the session
    let lowerBound = "asdf" // TODO: Get this from the UI
    let upperBound = "asdf" // TODO: Get this from the UI
    post("/api/lobby/v1/" + lobbyId + "/submitEstimate",
        {
            "playerId": playerId,
            "lowerBound": lowerBound,
            "upperBound": upperBound
        },
        result => {
            let score = result.data.score
            // TODO: Display the users score in the UI
            // When other players have answered / times up, move everyone to the results page
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