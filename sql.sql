DROP DATABASE IF EXISTS homework;
CREATE DATABASE homework;
USE homework;

CREATE TABLE dogs (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100),
  birthday DATE,
  skills TEXT
);

CREATE TABLE cats (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100),
  birthday DATE,
  skills TEXT
);

CREATE TABLE hamsters (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100),
  birthday DATE,
  skills TEXT
);

CREATE TABLE horses (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100),
  birthday DATE,
  skills TEXT
);

CREATE TABLE donkeys (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100),
  birthday DATE,
  skills TEXT
);

CREATE TABLE camels (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100),
  birthday DATE,
  skills TEXT
);

INSERT INTO dogs (name, birthday, skills) VALUES
  ('Бейли','2023-01-01','Сидеть'),
  ('Макс','2023-02-01','Лежать'),
  ('Луна','2023-03-01','Фас');
  
INSERT INTO cats (name, birthday, skills) VALUES
  ('Вася','2023-01-01','Мурлыкать'),
  ('Мурка','2023-01-01','Ловить мышей'),
  ('Симба','2023-01-01','Играть');
  
INSERT INTO hamsters (name, birthday, skills) VALUES
  ('Карамелька','2022-02-01','Подъем на задние лапы'),
  ('Шнюфель','2023-03-01','Бег в колесе'),
  ('Пушистик','2023-04-01','');
  
INSERT INTO horses (name, birthday, skills) VALUES
  ('Торнадо','2020-01-01','Шаг'),
  ('Рыжик','2020-01-01','Галоп'),
  ('Пегас','2020-01-01','');
  
INSERT INTO donkeys (name, birthday, skills) VALUES
  ('Булат','2020-05-15',''),
  ('Потап','2020-06-16',''),
  ('Граф','2020-06-17','');
  
INSERT INTO camels (name, birthday, skills) VALUES
  ('Хаджар','2019-07-20','Кусается'),
  ('Касим','2019-08-21','Плюется'),
  ('Зара','2019-09-22','');
  
DELETE FROM camels;
  
SELECT name, birthday, skills
FROM horses
UNION ALL 
SELECT name, birthday, skills
FROM donkeys;

CREATE TABLE young_animals AS
SELECT 
  name, 
  birthday, 
  skills, 
  TIMESTAMPDIFF(MONTH, birthday, CURDATE()) AS age_month
FROM (SELECT name, birthday, skills
	FROM dogs
	UNION ALL
	SELECT name, birthday, skills
	FROM cats
  UNION ALL
  SELECT name, birthday, skills
	FROM hamsters
   UNION ALL
	SELECT name, birthday, skills
	FROM horses
	UNION ALL 
	SELECT name, birthday, skills
	FROM donkeys
  UNION ALL
  SELECT name, birthday, skills
	FROM camels) AS all_animals
WHERE birthday BETWEEN DATE_SUB(CURDATE(), INTERVAL 3 YEAR) AND DATE_SUB(CURDATE(), INTERVAL 1 YEAR);
  
SELECT * FROM young_animals;
  
CREATE TABLE all_animals AS
	SELECT *, 'dogs' AS source FROM dogs
	UNION ALL
	SELECT *, 'cats' AS source FROM cats
	UNION ALL
	SELECT *, 'hamsters' AS source FROM hamsters
	UNION ALL
	SELECT *, 'donkeys' AS source FROM donkeys
	UNION ALL
	SELECT *, 'horses' AS source FROM horses
	UNION ALL
	SELECT *, 'camels' AS source FROM camels;

SELECT * FROM all_animals;