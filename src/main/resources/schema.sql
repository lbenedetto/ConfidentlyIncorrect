CREATE TABLE IF NOT EXISTS TblLobby
(
    id                VARCHAR(6) NOT NULL,
    capacity          INT(11)    NULL DEFAULT NULL,
    ownerId           BIGINT(20) NOT NULL,
    questionId        BIGINT(20) NULL DEFAULT NULL,
    questionCount     INT(11)    NOT NULL,
    questionLimit     INT(11)    NOT NULL,
    questionExpiresAt DATETIME   NULL DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS TblPlayer
(
    id              BIGINT(20)   NOT NULL AUTO_INCREMENT,
    name            VARCHAR(100) NOT NULL,
    score           INT(11)      NOT NULL,
    isParticipating TINYINT(1)   NOT NULL,
    lobbyId         VARCHAR(6)   NULL DEFAULT NULL,
    PRIMARY KEY (id),
    INDEX idx_lobbyId (lobbyId),
    INDEX idx_isParticipating (isParticipating)
);

CREATE TABLE IF NOT EXISTS TblQuestion
(
    id          BIGINT(20) NOT NULL AUTO_INCREMENT,
    scoringType INT(11)    NOT NULL,
    text        TEXT       NOT NULL,
    answer      DOUBLE     NOT NULL,
    category    INT(11)    NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_category (category)
);

CREATE TABLE IF NOT EXISTS TblEstimate
(
    id         BIGINT(20) NOT NULL AUTO_INCREMENT,
    lobbyId    VARCHAR(6) NOT NULL,
    playerId   BIGINT(20) NOT NULL,
    questionId BIGINT(20) NOT NULL,
    lowerBound DOUBLE     NOT NULL,
    upperBound DOUBLE     NOT NULL,
    score      DOUBLE     NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_lobbyId (lobbyId),
    INDEX idx_playerId (playerId),
    INDEX idx_questionId (questionId)
);

CREATE TABLE IF NOT EXISTS TblAccessToken
(
    id       BIGINT(20)  NOT NULL AUTO_INCREMENT,
    playerId BIGINT(20)  NOT NULL,
    token    VARCHAR(36) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_playerId (playerId),
    UNIQUE INDEX idx_token (token)
);

ALTER TABLE TblLobby
    ADD FOREIGN KEY (ownerId) REFERENCES TblPlayer (id),
    ADD FOREIGN KEY (questionId) REFERENCES TblQuestion (id);

ALTER TABLE TblPlayer
    ADD FOREIGN KEY (lobbyId) REFERENCES TblLobby (id);

ALTER TABLE TblEstimate
    ADD FOREIGN KEY (lobbyId) REFERENCES TblLobby (id),
    ADD FOREIGN KEY (playerId) REFERENCES TblPlayer (id),
    ADD FOREIGN KEY (questionId) REFERENCES TblQuestion (id);