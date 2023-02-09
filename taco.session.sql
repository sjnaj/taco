-- CREATE TABLE IF NOT EXISTS Ingredient(
--     id VARCHAR(4) NOT NULL,
--     name VARCHAR(25) NOT NULL,
--     type VARCHAR(10) NOT NULL
-- );
-- CREATE TABLE if NOT EXISTS Taco(
--     id INT PRIMARY KEY AUTO_INCREMENT,
--     name VARCHAR(50) NOT NULL,
--     createAT timestamp NOT NULL
-- );
-- CREATE TABLE Taco_Ingredients(
--     taco BIGINT NOT NULL,
--     ingredient VARCHAR(4) NOT NULL
-- );
-- CREATE TABLE User(
--     taco BIGINT NOT NULL,
--     ingredient VARCHAR(4) NOT NULL
-- );

-- ALTER TABLE taco_ingredients modify taco int
-- ALTER TABLE ingredient add PRIMARY key(id)
-- ALTER TABLE taco_ingredients add  foreign key (ingredient) references ingredient(id);
-- CREATE TABLE taco_order(
--     id int PRIMARY key AUTO_INCREMENT,
--     deliverName VARCHAR(50) NOT NULL,
--     deliverStreet VARCHAR(50) NOT NULL,
--     deliverCity VARCHAR(50) NOT NULL,
--     deliverState VARCHAR(2) NOT NULL,
--     deliverZip VARCHAR(10) NOT NULL,
--     ccNumber VARCHAR(16) NOT NULL,
--     ccExpiration VARCHAR(5) NOT NULL,
--     ccCVV VARCHAR(3) not NULL,
--     placedAt timestamp not NULL
-- )
-- CREATE TABLE Taco_Order_Tacos(
--     tacoOrder int not NULL,
--     taco int not NULL
-- )

-- ALTER TABLE taco_order_tacos add foreign key(tacoOrder) references taco_order(id);
-- ALTER TABLE taco_order_tacos add foreign key(taco) references taco(id);

-- CREATE TABLE user(
--     id bigint PRIMARY KEY AUTO_INCREMENT
-- )
-- DROP TABLE user

ALTER TABLE taco_order rename column user_id to userId