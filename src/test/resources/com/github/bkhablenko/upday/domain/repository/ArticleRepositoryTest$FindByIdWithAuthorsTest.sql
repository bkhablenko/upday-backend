INSERT INTO "author" ("id", "full_name")
VALUES
    ('3e6806e9-8605-4438-b843-b929379c48ff', 'Author 1');

INSERT INTO "article" ("id", "title", "body")
VALUES
    ('ee8db45b-c395-481d-809b-9af50d959908', 'Article 1', '');

INSERT INTO "author_article" ("author_id", "article_id")
VALUES
    ('3e6806e9-8605-4438-b843-b929379c48ff', 'ee8db45b-c395-481d-809b-9af50d959908'); -- Author 1 -> Article 1
