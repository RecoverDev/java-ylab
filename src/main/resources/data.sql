CREATE SCHEMA IF NOT EXISTS fitness
AUTHORIZATION CURRENT_USER; 
GRANT ALL ON SCHEMA fitness TO CURRENT_USER;


CREATE TABLE fitness.users (
	id BIGINT NOT NULL PRIMARY KEY,
	name VARCHAR(255),
	login VARCHAR(255),
	password VARCHAR(255),
	role BIGINT);
	
INSERT INTO fitness.users (id, name, login, password, role) VALUES 
(1,'Test User','user1','111',0),
(2,'Test User2','user2','222',0),
(3,'Test User3','admin','1234',1);
