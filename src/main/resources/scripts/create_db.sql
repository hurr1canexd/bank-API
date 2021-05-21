CREATE TABLE Client
(
    id        INT IDENTITY (1,1) PRIMARY KEY,
    firstName VARCHAR(30) NOT NULL,
    lastName  VARCHAR(30) NOT NULL
);


CREATE TABLE Account
(
    id       INT IDENTITY (1,1) PRIMARY KEY,
    number   VARCHAR(20) NOT NULL,
    balance  DECIMAL,
    clientId INT         NOT NULL,
    FOREIGN KEY (clientId) REFERENCES Client (id)
);


CREATE TABLE Card
(
    id        INT IDENTITY (1,1) PRIMARY KEY,
    number    VARCHAR(20) NOT NULL,
    month     VARCHAR(2)  NOT NULL, -- VARCHAR(2)
    year      VARCHAR(4)  NOT NULL, -- VARCHAR(4)
    code      VARCHAR(3),
    balance   DECIMAL,
    status    INT DEFAULT 0,
    accountId INT         NOT NULL,
    FOREIGN KEY (accountId) REFERENCES Account (id)
);
