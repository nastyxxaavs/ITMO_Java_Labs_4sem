CREATE TABLE cats_friends (
                              second_cat_id UUID NOT NULL,
                              cat_id UUID NOT NULL REFERENCES cats(id),
                              PRIMARY KEY (second_cat_id, cat_id)
);