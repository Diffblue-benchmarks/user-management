create TABLE "auth_user" (
    "id" serial NOT NULL,
    "created_at" timestamp NOT NULL,
    "modified_at" timestamp,
    "date_of_birth" date NOT NULL,
    "first_name" varchar(255) NOT NULL,
    "last_name" varchar(255) NOT NULL,
    "nick_name" varchar(255),
    "uid" varchar(255) UNIQUE NOT NULL,
    "gender" varchar(255) NOT NULL,
    "active" boolean NOT NULL DEFAULT true,
    "is_parent" boolean NOT NULL DEFAULT false,
    PRIMARY KEY ("id")
);