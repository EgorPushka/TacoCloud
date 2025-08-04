-- 1. Keyspace
CREATE KEYSPACE IF NOT EXISTS tacocloud
    WITH replication = {'class':'SimpleStrategy','replication_factor':1}
     AND durable_writes = true;

USE tacocloud;

-- 2. UDT для IngredientUDT
CREATE TYPE IF NOT EXISTS ingredient (
                                         name text,
                                         type text
                                     );

-- 3. Таблица ingredients для сущности Ingredient
CREATE TABLE IF NOT EXISTS ingredients (
                                           id text PRIMARY KEY,
                                           name text,
                                           type text
);

-- 4. Наполнение таблицы ingredients
INSERT INTO ingredients (id, name, type) VALUES ('FLTO', 'Flour Tortilla', 'WRAP');
INSERT INTO ingredients (id, name, type) VALUES ('COTO', 'Corn Tortilla', 'WRAP');
INSERT INTO ingredients (id, name, type) VALUES ('GRBF', 'Ground Beef', 'PROTEIN');
INSERT INTO ingredients (id, name, type) VALUES ('CARN', 'Carnitas', 'PROTEIN');
INSERT INTO ingredients (id, name, type) VALUES ('TMTO', 'Diced Tomatoes', 'VEGGIES');
INSERT INTO ingredients (id, name, type) VALUES ('LETC', 'Lettuce', 'VEGGIES');
INSERT INTO ingredients (id, name, type) VALUES ('CHED', 'Cheddar', 'CHEESE');
INSERT INTO ingredients (id, name, type) VALUES ('JACK', 'Monterrey Jack', 'CHEESE');
INSERT INTO ingredients (id, name, type) VALUES ('SLSA', 'Salsa', 'SAUCE');
INSERT INTO ingredients (id, name, type) VALUES ('SRCR', 'Sour Cream', 'SAUCE');
