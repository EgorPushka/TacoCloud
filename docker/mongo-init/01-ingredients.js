/* Инициализация ингредиентов для TacoCloud */
db = db.getSiblingDB('tacocloud');

db.ingredients.drop();

db.ingredients.insertMany([
    { _id: "FLTO", name: "Flour Tortilla", type: "WRAP" },
    { _id: "COTO", name: "Corn Tortilla",  type: "WRAP" },
    { _id: "GRBF", name: "Ground Beef",     type: "PROTEIN" },
    { _id: "CARN", name: "Carnitas",        type: "PROTEIN" },
    { _id: "TMTO", name: "Diced Tomatoes",  type: "VEGGIES" },
    { _id: "LETC", name: "Lettuce",         type: "VEGGIES" },
    { _id: "CHED", name: "Cheddar",         type: "CHEESE" },
    { _id: "JACK", name: "Monterrey Jack",  type: "CHEESE" },
    { _id: "SLSA", name: "Salsa",           type: "SAUCE" },
    { _id: "SRCR", name: "Sour Cream",      type: "SAUCE" }
]);

db.tacos.createIndex({ createdAt: -1 });
db.orders.createIndex({ placedAt: -1 });
