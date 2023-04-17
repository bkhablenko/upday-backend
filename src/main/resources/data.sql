-- ID: espFx31ogpynhXHNJ2TW72
INSERT INTO "author" ("id", "full_name")
VALUES
    ('6cf6bc24-482c-42ac-9c10-7bb8442e3f3d', 'Hunter S. Thompson')

ON CONFLICT DO NOTHING;

-- ID: 5hAthjCg7vobqEgG6WDruY
INSERT INTO "article" ("id", "title", "description", "body", "tags")
VALUES
    ('22b5b2b5-582f-4ea8-a871-b683e06179dc', 'Song of the Sausage Creature', 'A wild ride on a Ducati, celebrating the thrill and culture of motorcycling.', '', '{motorcycling}')

ON CONFLICT DO NOTHING;

INSERT INTO "author_article" ("author_id", "article_id")
VALUES
    ('6cf6bc24-482c-42ac-9c10-7bb8442e3f3d', '22b5b2b5-582f-4ea8-a871-b683e06179dc')

ON CONFLICT DO NOTHING;
