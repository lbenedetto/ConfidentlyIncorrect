# data.sql is executed during startup
INSERT IGNORE INTO TblQuestion (id, answer, category, scoringType, text)
VALUES (1, 21105172, 0, 1, 'How many questions are there on Stack Overflow');

INSERT IGNORE INTO TblQuestion (id, answer, category, scoringType, text)
VALUES (2, 700, 2, 1, 'How much wood could a wood chuck chuck if a wood chuck could chuck wood');