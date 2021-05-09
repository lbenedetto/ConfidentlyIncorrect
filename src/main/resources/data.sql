# data.sql is executed during startup
INSERT IGNORE INTO TblQuestion (id, answer, category, scoringType, text, explanation)
VALUES (1, 21105172, 'TECH', 1,
        'How many questions are there on Stack Overflow',
        'There are approximately 21,105,172 questions on Stack Overflow');

INSERT IGNORE INTO TblQuestion (id, answer, category, scoringType, text, explanation)
VALUES (2, 700, 'JOKE', 1,
        'How much wood could a wood chuck chuck if a wood chuck could chuck wood',
        'Researchers at Cornell determined that a woodchuck could chuck about 700 pounds');