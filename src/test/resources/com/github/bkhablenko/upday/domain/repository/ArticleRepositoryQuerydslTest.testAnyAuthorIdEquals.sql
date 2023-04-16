INSERT INTO "author" ("id", "full_name")
VALUES
    ('3e6806e9-8605-4438-b843-b929379c48ff', 'Author 1'),
    ('bedec281-801e-4225-8f08-ce30e198f9f8', 'Author 2');

INSERT INTO "article" ("id", "title", "body")
VALUES
    ('ee8db45b-c395-481d-809b-9af50d959908', 'Article 1', ''),
    ('b32d4855-4df7-4bc2-9a91-4fce14828b1f', 'Article 2', ''),
    ('c352907f-1429-47df-b67d-8f99ca050674', 'Article 3', '');

INSERT INTO "author_article" ("author_id", "article_id")
VALUES
    ('3e6806e9-8605-4438-b843-b929379c48ff', 'ee8db45b-c395-481d-809b-9af50d959908'), -- Author 1 -> Article 1
    ('bedec281-801e-4225-8f08-ce30e198f9f8', 'b32d4855-4df7-4bc2-9a91-4fce14828b1f'), -- Author 2 -> Article 2

    ('3e6806e9-8605-4438-b843-b929379c48ff', 'c352907f-1429-47df-b67d-8f99ca050674'), -- Author 1 -> Article 3
    ('bedec281-801e-4225-8f08-ce30e198f9f8', 'c352907f-1429-47df-b67d-8f99ca050674'); -- Author 2 -> Article 3
