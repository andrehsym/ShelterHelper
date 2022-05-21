-- liquibase formatted sql

-- changeSet rickln: v1.0
CREATE TABLE user
(
    idChat         BIGINT       NOT NULL PRIMARY KEY,
    idUser         SERIAL       NOT NULL,
    nameUserInChat VARCHAR(100) NOT NULL,
    stamp          timestamp    NOT NULL,
    emailUser      varchar(100),
    phoneUser      varchar(20),
    idRequest      bigint,
    isAdopt        BOOLEAN
);
