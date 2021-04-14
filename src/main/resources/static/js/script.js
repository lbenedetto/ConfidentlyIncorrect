function hostGame() {
    post("/api/lobby/v1/",
        {
            "hostName": $("#InputUsernameField").text(),
            "isParticipating": true
        },
        result => {
            console.log(result)
        }
    )
}

function joinGame() {
    console.log("join game clicked")
}

function startGame() {

}

function submitEstimate() {

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
    $.ajax({
        type: "POST",
        url: url,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: onSuccess,
        dataType: "json"
    });
}