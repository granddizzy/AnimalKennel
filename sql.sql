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
    FOREIGN KEY (id) REFERENCES animals(id)
);

CREATE TABLE pack_animals (
    id INT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES animals(id)
);

CREATE TABLE dogs (
	id INT PRIMARY KEY,
	FOREIGN KEY (id) REFERENCES home_animals(id)
);

CREATE TABLE cats (
	id INT PRIMARY KEY,
	FOREIGN KEY (id) REFERENCES home_animals(id)
);

CREATE TABLE hamsters (
	id INT PRIMARY KEY,
	FOREIGN KEY (id) REFERENCES home_animals(id)
);

CREATE TABLE horses (
	id INT PRIMARY KEY,
	FOREIGN KEY (id) REFERENCES pack_animals(id)
);

CREATE TABLE donkeys (
	id INT PRIMARY KEY,
	FOREIGN KEY (id) REFERENCES pack_animals(id)
);

CREATE TABLE camels (
 	id INT PRIMARY KEY,
	FOREIGN KEY (id) REFERENCES pack_animals(id)
);

# dogs
INSERT INTO animals (name, birthday, skills) VALUES
  ('Бейли','2023-01-01','Сидеть');
INSERT INTO home_animals (SELECT LAST_INSERT_ID());
INSERT INTO dogs (SELECT LAST_INSERT_ID());

INSERT INTO animals (name, birthday, skills) VALUES
  ('Макс','2023-02-01','Лежать');
  INSERT INTO home_animals (SELECT LAST_INSERT_ID());
INSERT INTO dogs (SELECT LAST_INSERT_ID());

INSERT INTO animals (name, birthday, skills) VALUES
  ('Луна','2023-03-01','Фас');
INSERT INTO home_animals (SELECT LAST_INSERT_ID());
INSERT INTO dogs (SELECT LAST_INSERT_ID());