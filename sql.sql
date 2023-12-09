DROP DATABASE IF EXISTS friends_of_man;
CREATE DATABASE friends_of_man;
USE friends_of_man;

CREATE TABLE animals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    birthday DATE,
    skills TEXT
);

CREATE TABLE home_animals (
    id INT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES animals(id) ON DELETE CASCADE
);

CREATE TABLE pack_animals (
    id INT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES animals(id) ON DELETE CASCADE
);

CREATE TABLE dogs (
	id INT PRIMARY KEY,
	FOREIGN KEY (id) REFERENCES home_animals(id) ON DELETE CASCADE
);

CREATE TABLE cats (
	id INT PRIMARY KEY,
	FOREIGN KEY (id) REFERENCES home_animals(id) ON DELETE CASCADE
);

CREATE TABLE hamsters (
	id INT PRIMARY KEY,
	FOREIGN KEY (id) REFERENCES home_animals(id) ON DELETE CASCADE
);

CREATE TABLE horses (
	id INT PRIMARY KEY,
	FOREIGN KEY (id) REFERENCES pack_animals(id) ON DELETE CASCADE
);

CREATE TABLE donkeys (
	id INT PRIMARY KEY,
	FOREIGN KEY (id) REFERENCES pack_animals(id) ON DELETE CASCADE
);

CREATE TABLE camels (
 	id INT PRIMARY KEY,
	FOREIGN KEY (id) REFERENCES pack_animals(id) ON DELETE CASCADE
);

# dogs
INSERT INTO animals (name, birthday, skills) VALUES ('Бейли','2023-01-01','Сидеть');
INSERT INTO home_animals (SELECT LAST_INSERT_ID());
INSERT INTO dogs (SELECT LAST_INSERT_ID());

INSERT INTO animals (name, birthday, skills) VALUES ('Макс','2023-02-01','Лежать');
INSERT INTO home_animals (SELECT LAST_INSERT_ID());
INSERT INTO dogs (SELECT LAST_INSERT_ID());

INSERT INTO animals (name, birthday, skills) VALUES ('Луна','2023-03-01','Фас');
INSERT INTO home_animals (SELECT LAST_INSERT_ID());
INSERT INTO dogs (SELECT LAST_INSERT_ID());

# cats
INSERT INTO animals (name, birthday, skills) VALUES ('Вася','2023-01-01','Мурлыкать');
INSERT INTO home_animals (SELECT LAST_INSERT_ID());
INSERT INTO cats (SELECT LAST_INSERT_ID());

INSERT INTO animals (name, birthday, skills) VALUES ('Мурка','2023-01-01','Ловить мышей');
INSERT INTO home_animals (SELECT LAST_INSERT_ID());
INSERT INTO cats (SELECT LAST_INSERT_ID());

INSERT INTO animals (name, birthday, skills) VALUES ('Симба','2023-01-01','Играть');
INSERT INTO home_animals (SELECT LAST_INSERT_ID());
INSERT INTO cats (SELECT LAST_INSERT_ID());

# hamsters
INSERT INTO animals (name, birthday, skills) VALUES ('Карамелька','2022-02-01','Подъем на задние лапы');
INSERT INTO home_animals (SELECT LAST_INSERT_ID());
INSERT INTO hamsters (SELECT LAST_INSERT_ID());

INSERT INTO animals (name, birthday, skills) VALUES ('Шнюфель','2023-03-01','Бег в колесе');
INSERT INTO home_animals (SELECT LAST_INSERT_ID());
INSERT INTO hamsters (SELECT LAST_INSERT_ID());

INSERT INTO animals (name, birthday, skills) VALUES ('Пушистик','2023-04-01','');
INSERT INTO home_animals (SELECT LAST_INSERT_ID());
INSERT INTO hamsters (SELECT LAST_INSERT_ID());

# horses
INSERT INTO animals (name, birthday, skills) VALUES ('Торнадо','2020-01-01','Шаг');
INSERT INTO pack_animals (SELECT LAST_INSERT_ID());
INSERT INTO horses (SELECT LAST_INSERT_ID());

INSERT INTO animals (name, birthday, skills) VALUES ('Рыжик','2020-01-01','Галоп');
INSERT INTO pack_animals (SELECT LAST_INSERT_ID());
INSERT INTO horses (SELECT LAST_INSERT_ID());

INSERT INTO animals (name, birthday, skills) VALUES ('Пегас','2020-01-01','');
INSERT INTO pack_animals (SELECT LAST_INSERT_ID());
INSERT INTO horses (SELECT LAST_INSERT_ID());

# donkeys
INSERT INTO animals (name, birthday, skills) VALUES ('Булат','2020-05-15','');
INSERT INTO pack_animals (SELECT LAST_INSERT_ID());
INSERT INTO donkeys (SELECT LAST_INSERT_ID());

INSERT INTO animals (name, birthday, skills) VALUES ('Потап','2020-06-16','');
INSERT INTO pack_animals (SELECT LAST_INSERT_ID());
INSERT INTO donkeys (SELECT LAST_INSERT_ID());

INSERT INTO animals (name, birthday, skills) VALUES ('Граф','2020-06-17','');
INSERT INTO pack_animals (SELECT LAST_INSERT_ID());
INSERT INTO donkeys (SELECT LAST_INSERT_ID());

# camels
INSERT INTO animals (name, birthday, skills) VALUES ('Хаджар','2019-07-20','Кусается');
INSERT INTO pack_animals (SELECT LAST_INSERT_ID());
INSERT INTO camels (SELECT LAST_INSERT_ID());

INSERT INTO animals (name, birthday, skills) VALUES ('Касим','2019-08-21','Плюется');
INSERT INTO pack_animals (SELECT LAST_INSERT_ID());
INSERT INTO camels (SELECT LAST_INSERT_ID());

INSERT INTO animals (name, birthday, skills) VALUES ('Зара','2019-09-22','У верблюда два горба, потому что жизнь - борьба.');
INSERT INTO pack_animals (SELECT LAST_INSERT_ID());
INSERT INTO camels (SELECT LAST_INSERT_ID());

# delete camels
DELETE FROM animals WHERE id IN (SELECT id FROM camels);

SELECT * FROM animals;
SELECT * FROM home_animals;
SELECT * FROM pack_animals;
SELECT * FROM dogs;
SELECT * FROM cats;
SELECT * FROM hamsters;
SELECT * FROM donkeys;
SELECT * FROM horses;
SELECT * FROM camels;

# young_animals
CREATE TABLE young_animals AS
SELECT
  id,
  name,
  birthday,
  skills,
  TIMESTAMPDIFF(MONTH, birthday, CURDATE()) AS age_month
FROM animals AS all_animals
WHERE birthday BETWEEN DATE_SUB(CURDATE(), INTERVAL 3 YEAR) AND DATE_SUB(CURDATE(), INTERVAL 1 YEAR);

SELECT * FROM young_animals;

# merge table
CREATE TABLE all_animals AS
SELECT *, 'dog' AS 'class', 'home' as 'type'  FROM animals WHERE id IN (SELECT id FROM dogs)
UNION ALL
SELECT *, 'cat' AS 'class', 'home' as 'type' FROM animals WHERE id IN (SELECT id FROM cats)
UNION ALL
SELECT *, 'hamster' AS 'class', 'home' as 'type' FROM animals WHERE id IN (SELECT id FROM hamsters)
UNION ALL
SELECT *, 'donkey' AS 'class', 'pack' as 'type' FROM animals WHERE id IN (SELECT id FROM donkeys)
UNION ALL
SELECT *, 'horse' AS 'class', 'pack' as 'type' FROM animals WHERE id IN (SELECT id FROM horses)
UNION ALL
SELECT *, 'camel' AS 'class', 'pack' as 'type' FROM animals WHERE id IN (SELECT id FROM camels);

SELECT * FROM all_animals;