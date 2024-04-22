CREATE TABLE cats_friends (
                              id UUID NOT NULL PRIMARY KEY,
                              cat_id UUID NOT NULL REFERENCES cats(id)
);