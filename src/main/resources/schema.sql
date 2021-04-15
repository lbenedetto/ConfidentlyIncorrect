CREATE TABLE IF NOT EXISTS tbl_lobby
(
    id                VARCHAR(6) NOT NULL,
    capacity          INT(11)    NULL DEFAULT NULL,
    ownerId           BIGINT(20) NOT NULL,
    questionId        BIGINT(20) NULL DEFAULT NULL,
    question_count    INT(11)    NOT NULL,
    question_limit    INT(11)    NOT NULL,
    questionExpiresAt DATETIME   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tbl_player
(
    id              BIGINT(20)   NOT NULL AUTO_INCREMENT,
    name            VARCHAR(100) NOT NULL,
    score           INT(11)      NOT NULL,
    isParticipating TINYINT(1)   NOT NULL,
    lobbyId         VARCHAR(6)   NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_lobbyId (lobbyId),
    INDEX idx_isParticipating (isParticipating)
);

CREATE TABLE IF NOT EXISTS tbl_question
(
    id          BIGINT(20) NOT NULL AUTO_INCREMENT,
    scoringType INT(11)    NOT NULL,
    text        TEXT       NOT NULL,
    answer      DOUBLE     NOT NULL,
    category    INT(11)    NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_category (category)
);

CREATE TABLE IF NOT EXISTS tbl_estimate
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

CREATE TABLE IF NOT EXISTS tbl_access_token
(
    id       BIGINT(20)  NOT NULL AUTO_INCREMENT,
    playerId BIGINT(20)  NOT NULL,
    token    VARCHAR(32) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_playerId (playerId),
    UNIQUE INDEX idx_token (token)
);

ALTER TABLE tbl_lobby
    ADD FOREIGN KEY (ownerId) REFERENCES tbl_player (id),
    ADD FOREIGN KEY (questionId) REFERENCES tbl_question (id);

ALTER TABLE tbl_player
    ADD FOREIGN KEY (lobbyId) REFERENCES tbl_lobby (id);

ALTER TABLE tbl_estimate
    ADD FOREIGN KEY (lobbyId) REFERENCES tbl_lobby (id),
    ADD FOREIGN KEY (playerId) REFERENCES tbl_player (id),
    ADD FOREIGN KEY (questionId) REFERENCES tbl_question (id);