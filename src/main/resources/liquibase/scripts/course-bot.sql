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
CREATE INDEX shelterClient_idChat_idx ON shelterClient (idChat);

-- changeSet rickln: v2.5
CREATE TABLE shelter_pets
(
    id_pet      SERIAL NOT NULL  PRIMARY KEY,
    id_entity   INT  NOT NULL,
    pet_name    varchar(255),
    pet_text    varchar(255),
    is_used     BOOLEAN
);
-- При создании  автоматически is_used  BOOLEAN DEFAULT FALSE
ALTER TABLE shelter_pets ALTER is_used   SET DEFAULT FALSE;

-- changeSet rickln: v1.31
CREATE TABLE adopter_dogs
(
    id_user                  bigint NOT NULL PRIMARY KEY,
    id_pet                   bigint NOT NULL PRIMARY KEY,
    id_checked               BOOLEAN,
    is_problem               BOOLEAN,
    amount_of_probation_days INTEGER,
    amount_of_extra_days     INTEGER

);
-- changeSet rickln: v1.31
CREATE TABLE adopter_cats
(
    id_user                  bigint NOT NULL PRIMARY KEY,
    id_pet                   bigint NOT NULL PRIMARY KEY,
    id_checked               BOOLEAN,
    is_problem               BOOLEAN,
    amount_of_probation_days INTEGER,
    amount_of_extra_days     INTEGER

);
-- changeSet rickln: v2.11
CREATE TABLE question
(
    id_question    bigint NOT NULL PRIMARY KEY,
    id_parent      bigint,
    id_entity      INT  NOT NULL,
    is_list        BOOLEAN,
    is_need_answer BOOLEAN,
    text_question  varchar(255)
);
ALTER TABLE question ALTER id_entity SET DEFAULT '1';
-- changeSet rickln: v1.13
CREATE TABLE answer
(
    id          bigint NOT NULL PRIMARY KEY,
    id_question bigint NOT NULL,
    text_answer varchar(255)
);

-- changeSet rickln: v1.16
CREATE TABLE shelter_entity
(
    id_entity   INT NOT NULL PRIMARY KEY,
    text_entity varchar(50)
);

-- changeSet igor: 1
CREATE TABLE answer_image
(
    id   bigint NOT NULL PRIMARY KEY,
    data oid,
    file_size bigint,
    media_type varchar(255),
    answer_id bigint
);