INSERT INTO "author" ("id", "full_name")
VALUES
    ('3e6806e9-8605-4438-b843-b929379c48ff', 'Author 1');

INSERT INTO "article" ("id", "title", "body", "tags")
VALUES
    ('ee8db45b-c395-481d-809b-9af50d959908', 'Article 1', '', '{health}'),
    ('b32d4855-4df7-4bc2-9a91-4fce14828b1f', 'Article 2', '', '{politics}'),
    ('c352907f-1429-47df-b67d-8f99ca050674', 'Article 3', '', '{health,politics}'),
    ('73d17d9e-16b2-4159-8c28-ba98e23e246c', 'Article 4', '', '{science}');

INSERT INTO "author_article" ("author_id", "article_id")
VALUES
    ('3e6806e9-8605-4438-b843-b929379c48ff', 'ee8db45b-c395-481d-809b-9af50d959908'), -- Author 1 -> Article 1
    ('3e6806e9-8605-4438-b843-b929379c48ff', 'b32d4855-4df7-4bc2-9a91-4fce14828b1f'), -- Author 1 -> Article 2
    ('3e6806e9-8605-4438-b843-b929379c48ff', 'c352907f-1429-47df-b67d-8f99ca050674'), -- Author 1 -> Article 3
    ('3e6806e9-8605-4438-b843-b929379c48ff', '73d17d9e-16b2-4159-8c28-ba98e23e246c'); -- Author 1 -> Article 4
