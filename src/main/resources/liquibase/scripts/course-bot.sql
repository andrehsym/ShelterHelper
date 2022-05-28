-- liquibase formatted sql

-- changeSet rickln: v1.12
CREATE TABLE public."shelterClient"
(
    "idUser"         serial       NOT NULL PRIMARY KEY,
    "idChat"         bigint       NOT NULL,
    "nameUserInChat" varchar(255) NOT NULL,
    stamp            timestamp    NOT NULL,
    "emailUser"      varchar(255),
    "phoneUser"      varchar(255),
    "idQuestion"     bigint,
    "isAdopt"        boolean
);
-- changeSet rickln: v1.2
CREATE INDEX shelterClient_idChat_idx ON shelterClien (idChat);

-- changeSet rickln: v1.15
CREATE TABLE shelter_dog
(
    id_dog   SERIAL       NOT NULL PRIMARY KEY,
    dog_name varchar(255),
    dog_text varchar(255),
    is_used  BOOLEAN
);
-- При создании  автоматически is_used  BOOLEAN DEFAULT FALSE
ALTER TABLE shelter_dog ALTER is_used SET DEFAULT FALSE;

-- changeSet rickln: v1.3
CREATE TABLE adopter_dog
(
    id_user                  bigint NOT NULL PRIMARY KEY,
    id_dog                   bigint NOT NULL PRIMARY KEY,
    id_checked               BOOLEAN,
    is_problem               BOOLEAN,
    amount_of_probation_days INTEGER,
    amount_of_extra_days     INTEGER

);

-- changeSet rickln: v1.11
CREATE TABLE question
(
    id_question    bigint NOT NULL PRIMARY KEY,
    id_parent      bigint,
    is_list        BOOLEAN,
    is_need_answer BOOLEAN,
    text_question  varchar(255)
);

-- changeSet rickln: v1.13
CREATE TABLE answer
(
    id          bigint NOT NULL PRIMARY KEY,
    id_question bigint NOT NULL,
    text_answer varchar(255)
);
