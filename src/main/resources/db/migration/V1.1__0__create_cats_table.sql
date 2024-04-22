CREATE TYPE catsColor AS ENUM ('black', 'grey', 'ginger', 'white', 'colorful');

CREATE TABLE cats (
                      id UUID NOT NULL PRIMARY KEY,
                      name varchar(250),
                      date_of_birth timestamp,
                      species varchar(250),
                      color catsColor,
                      owner_id UUID NOT NULL REFERENCES owners(id)
);