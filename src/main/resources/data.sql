-- Очистка таблиц
DELETE FROM Ingredient_Ref;
DELETE FROM Taco;
DELETE FROM Taco_Order;

-- Очистка и наполнение таблицы Ingredient
DELETE FROM Ingredient;

DELETE FROM users;

INSERT INTO Ingredient (id, name, type) VALUES ('FLTO', 'Flour Tortilla', 'WRAP');
INSERT INTO Ingredient (id, name, type) VALUES ('COTO', 'Corn Tortilla', 'WRAP');
INSERT INTO Ingredient (id, name, type) VALUES ('GRBF', 'Ground Beef', 'PROTEIN');
INSERT INTO Ingredient (id, name, type) VALUES ('CARN', 'Carnitas', 'PROTEIN');
INSERT INTO Ingredient (id, name, type) VALUES ('TWTO', 'Diced Tomatoes', 'VEGGIES');
INSERT INTO Ingredient (id, name, type) VALUES ('LETC', 'Lettuce', 'VEGGIES');
INSERT INTO Ingredient (id, name, type) VALUES ('CHED', 'Cheddar', 'CHEESE');
INSERT INTO Ingredient (id, name, type) VALUES ('JACK', 'Monterrey Jack', 'CHEESE');
INSERT INTO Ingredient (id, name, type) VALUES ('SLSA', 'Salsa', 'SAUCE');
INSERT INTO Ingredient (id, name, type) VALUES ('SRCR', 'Sour Cream', 'SAUCE');

INSERT INTO users( username, password, fullname)
VALUES ( 'egor', '$2a$12$.LSwRYPrFkSUBVH4orp6eOfDWb7zT689V87H/sJGvF5rbw4A/GFn.', 'egor.p' );
