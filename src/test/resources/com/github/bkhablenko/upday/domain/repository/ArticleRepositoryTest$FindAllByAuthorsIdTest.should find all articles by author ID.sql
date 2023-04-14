INSERT INTO "author" ("id", "full_name")
VALUES
    ('3e6806e9-8605-4438-b843-b929379c48ff', 'Author A'),
    ('bedec281-801e-4225-8f08-ce30e198f9f8', 'Author B');

INSERT INTO "article" ("id", "title", "body")
VALUES
    ('ee8db45b-c395-481d-809b-9af50d959908', 'Article X', ''),
    ('b32d4855-4df7-4bc2-9a91-4fce14828b1f', 'Article Y', ''),
    ('c352907f-1429-47df-b67d-8f99ca050674', 'Article Z', '');

INSERT INTO "author_article" ("author_id", "article_id")
VALUES
    ('3e6806e9-8605-4438-b843-b929379c48ff', 'ee8db45b-c395-481d-809b-9af50d959908'), -- Author A -> Article X
    ('3e6806e9-8605-4438-b843-b929379c48ff', 'b32d4855-4df7-4bc2-9a91-4fce14828b1f'), -- Author A -> Article Y
    ('bedec281-801e-4225-8f08-ce30e198f9f8', 'c352907f-1429-47df-b67d-8f99ca050674'); -- Author B -> Article Z
