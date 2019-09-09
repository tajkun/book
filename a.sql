CREATE TABLE application
(
  id     INT(8) AUTO_INCREMENT
    PRIMARY KEY,
  userId CHAR(32)                            NULL,
  date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE article
(
  id           INT(9) AUTO_INCREMENT
    PRIMARY KEY,
  userId       CHAR(32)                                NULL,
  status       TINYINT DEFAULT '0'                     NULL,
  content      TEXT                                    NULL,
  bookId       INT(9)                                  NULL,
  pageCount    INT(9)                                  NULL,
  pageStart    INT(9)                                  NULL,
  name         VARCHAR(20)                             NULL,
  date         TIMESTAMP DEFAULT CURRENT_TIMESTAMP     NOT NULL,
  expertDate   TIMESTAMP DEFAULT '2000-01-01 12:30:00' NOT NULL,
  xml          TEXT                                    NULL,
  annotations  TEXT                                    NULL,
  discontinues VARCHAR(500)                            NULL
);

CREATE TABLE article_author
(
  id        INT AUTO_INCREMENT
    PRIMARY KEY,
  articleId INT         NULL,
  authorId  INT         NULL,
  name      VARCHAR(10) NULL,
  `order`   INT(5)      NULL
);

CREATE TABLE author
(
  id      INT AUTO_INCREMENT
    PRIMARY KEY,
  name    VARCHAR(20)   NULL,
  birth   VARCHAR(10)   NULL,
  death   VARCHAR(10)   NULL,
  dynasty VARCHAR(10)   NULL,
  lift    VARCHAR(1000) NULL
);

CREATE TABLE book
(
  id            INT(9) AUTO_INCREMENT
    PRIMARY KEY,
  userId        CHAR(32)                            NULL,
  articleTotal  INT(9) DEFAULT '0'                  NULL,
  writtenDate   VARCHAR(10)                         NULL,
  name          VARCHAR(20)                         NULL,
  publishedDate CHAR(10)                            NULL,
  date          TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  unexamined    INT(9) DEFAULT '0'                  NULL,
  status        INT(5)                              NULL
);

CREATE TABLE book_author
(
  id       INT AUTO_INCREMENT
    PRIMARY KEY,
  bookId   INT         NULL,
  authorId INT         NULL,
  name     VARCHAR(10) NULL,
  `order`  INT(5)      NULL
);

CREATE TABLE page
(
  id        INT(9) AUTO_INCREMENT
    PRIMARY KEY,
  articleId INT(9) NULL,
  page      INT(9) NULL,
  svg       TEXT   NULL
);

CREATE TABLE users
(
  id       CHAR(32) DEFAULT ''                 NOT NULL
    PRIMARY KEY,
  account  VARCHAR(10)                         NULL,
  nickName VARCHAR(10)                         NULL,
  password CHAR(64)                            NULL,
  date     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  role     TINYINT                             NULL
);

