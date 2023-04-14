CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS "author" (
    "id"        uuid DEFAULT uuid_generate_v4(),
    "full_name" text NOT NULL,

    "created_at"  timestamp without time zone NOT NULL DEFAULT now(),
    "updated_at"  timestamp without time zone NOT NULL DEFAULT now(),

    PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "article" (
    "id"          uuid DEFAULT uuid_generate_v4(),
    "title"       text NOT NULL,
    "description" text NOT NULL DEFAULT '',
    "body"        text NOT NULL,
    "tags"        text[] NOT NULL DEFAULT '{}',

    "created_at"  timestamp without time zone NOT NULL DEFAULT now(),
    "updated_at"  timestamp without time zone NOT NULL DEFAULT now(),

    PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "author_article" (
    "author_id"  uuid REFERENCES "author" ("id"),
    "article_id" uuid REFERENCES "article" ("id"),

    PRIMARY KEY ("author_id", "article_id")
);
