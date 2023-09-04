DROP DATABASE LIBRARY;
CREATE DATABASE IF NOT EXISTS LIBRARY;
USE LIBRARY;

CREATE TABLE BOOK
(
    ISBN         BIGINT ,
    TITRE        VARCHAR(50),
    AUTHOR       VARCHAR(50),
    QUANTITE    INT,
    PRIMARY KEY (ISBN)
);

CREATE TABLE BOOK_EXAMPLE(
    ID INT NOT NULL AUTO_INCREMENT,
    AVAILABILITY ENUM('AVAILABLE', 'NOT_AVAILABLE', 'LOST'),
    ISBN BIGINT,
    PRIMARY KEY (ID),
    FOREIGN KEY (ISBN) REFERENCES BOOK(ISBN)
);

CREATE TABLE MEMBER
(
    NUMERO_MEMBRE INT NOT NULL AUTO_INCREMENT,
    NOM VARCHAR(50) NOT NULL ,
    PRENOM VARCHAR(50) NOT NULL ,
    PRIMARY KEY (NUMERO_MEMBRE)
);

CREATE TABLE EMPRUNT(
    NUMERO_MEMBRE INT NOT NULL ,
    BOOK_EXAMPLE INT NOT NULL ,
    DATE_EMPRUNT DATE,
    DATE_ROUTOUR DATE,
    PRIMARY KEY (NUMERO_MEMBRE,BOOK_EXAMPLE),
    FOREIGN KEY (NUMERO_MEMBRE) REFERENCES MEMBER(NUMERO_MEMBRE),
    FOREIGN KEY (BOOK_EXAMPLE) REFERENCES BOOK_EXAMPLE(ID)
);



-- Insert data into the BOOK table
INSERT INTO BOOK (ISBN, TITRE, AUTHOR, QUANTITE)
VALUES
    (9780451524935, 'To Kill a Mockingbird', 'Harper Lee', 5),
    (9781984819194, '1984', 'George Orwell', 3),
    (9780061120084, 'Fahrenheit 451', 'Ray Bradbury', 4);

-- Insert data into the BOOK_EXAMPLE table
INSERT INTO BOOK_EXAMPLE (AVAILABILITY, ISBN)
VALUES
    ('AVAILABLE', 9780451524935),
    ('AVAILABLE', 9780451524935),
    ('NOT_AVAILABLE', 9781984819194),
    ('AVAILABLE', 9780061120084);

-- Insert data into the MEMBER table
INSERT INTO MEMBER (NOM, PRENOM)
VALUES
    ('Smith', 'John'),
    ('Johnson', 'Alice'),
    ('Brown', 'Michael');

-- Insert data into the EMPRUNT table
INSERT INTO EMPRUNT (NUMERO_MEMBRE, BOOK_EXAMPLE, DATE_EMPRUNT, DATE_ROUTOUR)
VALUES
    (1, 1, '2023-08-01', '2023-08-15'),
    (2, 2, '2023-08-05', '2023-08-20'),
    (1, 3, '2023-08-10', '2023-08-25'),
    (3, 4, '2023-08-15', '2023-08-30');


SELECT * FROM BOOK;
SELECT * FROM BOOK_EXAMPLE;
SELECT * FROM MEMBER;
SELECT * FROM EMPRUNT;


