-- liquibase formatted sql

-- changeSet rickln: v1.1
CREATE TABLE public."shelterClient"
(
    "idUser" serial NOT NULL PRIMARY KEY,
    "idChat" bigint NOT NULL,
    "nameUserInChat" varchar(255) NOT NULL,
    stamp timestamp NOT NULL,
    "emailUser" varchar(255),
    "phoneUser" varchar(255),
    "idRequest" bigint,
    "isAdopt" boolean
);
-- changeSet rickln: v1.2
Create index shelterClient_idChat_idx on shelterClien (idChat);

-- changeSet rickln: v1.3
CREATE TABLE shelter_dog
(
    idDog     SERIAL NOT NULL PRIMARY KEY,
    dogName   varchar(255) NOT NULL,
    dogText   varchar(255),
    isUsed    BOOLEAN
);
-- changeSet rickln: v1.3
CREATE TABLE adopter_dog
(
    idUser  bigint NOT NULL  PRIMARY KEY,
    idDog   bigint NOT NULL  PRIMARY KEY,
    isChecked BOOLEAN,
    isProblem BOOLEAN,
    amountOfProbationDays INTEGER,
    amountOfExtraDays INTEGER
);

-- changeSet rickln: v1.11
CREATE TABLE question
(
    id_question    bigint NOT NULL  PRIMARY KEY,
    id_parent      bigint,
    is_list        BOOLEAN,
    is_need_answer BOOLEAN,
    text_question varchar(255)
);


