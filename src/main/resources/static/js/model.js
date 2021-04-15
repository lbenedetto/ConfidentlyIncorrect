class CreateLobbyResponse {
    constructor(data) {
        this.lobbyId = data.lobbyId
        this.accessToken = data.accessToken
    }
}

class JoinLobbyResponse {
    constructor(data) {
        this.accessToken = data.accessToken
    }
}

class LobbyDetails {
    constructor(data) {
        this.lobby = new Lobby(data.lobby)
        this.players = data.players.map(playerData => new Player(playerData))
    }
}

class QuestionResults {
    constructor(data) {
        this.question = new Question(data.question)
        this.scores = data.scores.map(scoreData => new Score(scoreData))
    }
}

class SubmitEstimateResponse {
    constructor(data) {
        this.score = data.score
        this.otherAnswersCount = data.otherAnswersCount
        this.otherPlayersCount = data.otherPlayersCount
    }
}

class Lobby {
    constructor(data) {
        this.id = data.id
        this.ownerId = data.ownerId
        this.questionId = data.questionId
        this.capacity = data.capacity
        this.questionCount = data.questionCount
        this.questionLimit = data.questionLimit
        this.questionExpiresAt = data.questionExpiresAt
    }
}

class Player {
    constructor(data) {
        this.id = data.id
        this.name = data.name
        this.score = data.score
        this.isParticipating = data.isParticipating
        this.lobbyId = data.lobbyId
    }
}

class Question {
    constructor(data) {
        this.id = data.id
        this.scoringType = data.scoringType
        this.text = data.text
        this.answer = data.answer
        this.category = data.category
    }
}

class Score {
    constructor(data) {
        this.score = data.score
        this.playerName = data.playerName
        this.lowerBound = data.lowerBound
        this.upperBound = data.upperBound
    }
}
