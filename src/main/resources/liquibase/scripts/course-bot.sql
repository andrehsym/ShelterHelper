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