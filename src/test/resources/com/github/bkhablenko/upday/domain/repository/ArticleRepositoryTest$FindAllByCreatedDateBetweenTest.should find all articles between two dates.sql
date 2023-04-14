INSERT INTO "author" ("id", "full_name")
VALUES
    ('ff114f3c-f23e-481f-8e8e-db3525a8d01b', 'Hunter S. Thompson');

INSERT INTO "article" ("id", "title", "body", "created_at")
VALUES
    ('e9231b30-63cb-4c82-8b74-9511a32cc21d', 'Article X', '', '2023-04-05 00:00:00'),
    ('dfdb9f4a-4a62-47a2-807b-198725225d47', 'Article Y', '', '2023-04-15 00:00:00'),
    ('a97c2004-da20-4d6a-a0a5-379123733f49', 'Article Z', '', '2023-04-25 00:00:00');

INSERT INTO "author_article" ("author_id", "article_id")
VALUES
    ('ff114f3c-f23e-481f-8e8e-db3525a8d01b', 'e9231b30-63cb-4c82-8b74-9511a32cc21d'), -- Hunter S. Thompson -> Article X
    ('ff114f3c-f23e-481f-8e8e-db3525a8d01b', 'dfdb9f4a-4a62-47a2-807b-198725225d47'), -- Hunter S. Thompson -> Article Y
    ('ff114f3c-f23e-481f-8e8e-db3525a8d01b', 'a97c2004-da20-4d6a-a0a5-379123733f49'); -- Hunter S. Thompson -> Article Z
