CREATE TABLE CATEGORY (
  ID INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  NAME VARCHAR(30) UNIQUE,
  ADDING_TIME DATETIME NOT NULL
);