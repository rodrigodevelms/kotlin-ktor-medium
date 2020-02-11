CREATE TABLE IF NOT EXISTS users.client
(
    id          uuid         not null,
    internal_Id bigserial    not null,
    active      boolean      not null,
    name        varchar(120) not null,
    email       varchar(120) not null,
    document    varchar(14)  not null,
    address     uuid         not null
);

CREATE UNIQUE INDEX client_id_uindex ON users.client (id);
CREATE UNIQUE INDEX client_internalId_uindex ON users.client (internal_Id);
CREATE UNIQUE INDEX client_email_index ON users.client (email);
CREATE UNIQUE INDEX client_document_index ON users.client (document);

ALTER TABLE users.client
    ADD CONSTRAINT client_pk PRIMARY KEY (id);