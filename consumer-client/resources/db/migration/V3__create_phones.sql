CREATE TABLE IF NOT EXISTS phones.phone
(
    id          uuid         not null,
    internal_Id bigserial    not null,
    active      boolean      not null,
    number      varchar(120) not null,
    client_fk   uuid
);

CREATE UNIQUE INDEX phone_id_uindex ON phones.phone (id);
CREATE UNIQUE INDEX phone_internalId_uindex ON phones.phone (internal_Id);
CREATE UNIQUE INDEX phone_number_index ON phones.phone (number);


ALTER TABLE phones.phone
    ADD CONSTRAINT phone_pk PRIMARY KEY (id);

ALTER TABLE phones.phone
    ADD CONSTRAINT phone_client_fk FOREIGN KEY (client_fk) REFERENCES
        users.client (id) ON DELETE CASCADE;
