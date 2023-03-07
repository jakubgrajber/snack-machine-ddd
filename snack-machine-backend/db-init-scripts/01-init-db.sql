DROP TABLE IF EXISTS snack_machine;
CREATE TABLE snack_machine
(
    id                  SERIAL PRIMARY KEY,
    one_cent_count      INTEGER NOT NULL DEFAULT 0,
    ten_cent_count      INTEGER NOT NULL DEFAULT 0,
    quarter_count       INTEGER NOT NULL DEFAULT 0,
    one_dollar_count    INTEGER NOT NULL DEFAULT 0,
    five_dollar_count   INTEGER NOT NULL DEFAULT 0,
    twenty_dollar_count INTEGER NOT NULL DEFAULT 0
);

DROP TABLE IF EXISTS snack;
CREATE TABLE snack
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

DROP TABLE IF EXISTS slot;
CREATE TABLE slot
(
    id               SERIAL PRIMARY KEY,
    quantity         INTEGER       NOT NULL DEFAULT 0,
    price            DECIMAL(7, 2) NOT NULL DEFAULT 0.00,
    snack_machine_id INTEGER       NOT NULL,
    snack_id         INTEGER       NOT NULL,
    position         INTEGER       NOT NULL,
    FOREIGN KEY (snack_machine_id) REFERENCES snack_machine (id),
    FOREIGN KEY (snack_id) REFERENCES snack (id)
);

-- SAMPLE DATA

-- SNACK MACHINE
INSERT INTO snack_machine (one_cent_count, ten_cent_count, quarter_count,
                           one_dollar_count, five_dollar_count,twenty_dollar_count)
VALUES (100, 100, 100, 100, 100, 100);

INSERT INTO snack_machine (one_cent_count, ten_cent_count, quarter_count,
                           one_dollar_count, five_dollar_count, twenty_dollar_count)
VALUES (0, 0, 0, 0, 0, 0);


-- SNACKS
INSERT INTO snack (name) VALUES ('Chocolate');
INSERT INTO snack (name) VALUES ('Soda');
INSERT INTO snack (name) VALUES ('Gum');

