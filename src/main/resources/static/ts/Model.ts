export class CreateLobbyRequest {
    playerName: string;
    isParticipating: boolean;
    capacity: string;
    questionLimit: string;

    constructor(playerName: string, isParticipating: boolean, capacity: string, questionLimit: string) {
        this.playerName = playerName;
        this.isParticipating = isParticipating;
        this.capacity = capacity != "" ? capacity : "10";
        this.questionLimit = questionLimit != "" ? questionLimit : "20";
    }
}

export class CreateLobbyResponse {
    lobbyId: string;
    accessToken: string;

    constructor(data: any) {
        this.lobbyId = data.lobbyId
        this.accessToken = data.accessToken
    }
}

export class JoinLobbyResponse {
    accessToken: string;

    constructor(data: any) {
        this.accessToken = data.accessToken
    }
}

export class LobbyDetails {
    lobby: Lobby;
    players: Array<Player>;
    constructor(data: any) {
        this.lobby = new Lobby(data.lobby)
        this.players = data.players.map((playerData: any) => new Player(playerData))
    }
}

export class QuestionResults {
    question: Question;
    scores: Array<Score>;

    constructor(data: any) {
        this.question = new Question(data.question)
        this.scores = data.scores.map((scoreData: any) => new Score(scoreData))
    }
}

export class SubmitEstimateResponse {
    score: Score;
    otherAnswersCount: number;
    otherPlayersCount: number;

    constructor(data: any) {
        this.score = data.score
        this.otherAnswersCount = data.otherAnswersCount
        this.otherPlayersCount = data.otherPlayersCount
    }
}

export class Lobby {
    id: string;
    ownerId: number;
    questionId: number;
    capacity: number;
    questionCount: number;
    questionLimit: number;
    questionExpiresAt: string
    constructor(data: any) {
        this.id = data.id
        this.ownerId = data.ownerId
        this.questionId = data.questionId
        this.capacity = data.capacity
        this.questionCount = data.questionCount
        this.questionLimit = data.questionLimit
        this.questionExpiresAt = data.questionExpiresAt
    }
}

export class Player {
    id: number;
    name: string;
    score: number;
    isParticipating: boolean;
    lobbyId: string;

    constructor(data: any) {
        this.id = data.id
        this.name = data.name
        this.score = data.score
        this.isParticipating = data.isParticipating
        this.lobbyId = data.lobbyId
    }
}

export class Question {
    id: string;
    scoringType: string;
    text: string;
    answer: string;
    category: string;

    constructor(data: any) {
        this.id = data.id
        this.scoringType = data.scoringType
        this.text = data.text
        this.answer = data.answer
        this.category = data.category
    }
}

export class Score {
    score: number;
    playerName: string;
    lowerBound: number;
    upperBound: number;
    constructor(data: any) {
        this.score = data.score
        this.playerName = data.playerName
        this.lowerBound = data.lowerBound
        this.upperBound = data.upperBound
    }
}
