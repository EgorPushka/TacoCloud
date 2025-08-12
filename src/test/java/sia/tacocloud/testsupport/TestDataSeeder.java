package sia.tacocloud.testsupport;

import java.util.Arrays;
import java.util.List;
import org.springframework.data.mongodb.core.MongoTemplate;
import sia.tacocloud.data.model.Ingredient;

/** Утилита для очистки и засева тестовых данных в embedded Mongo. */
public class TestDataSeeder {

  private final MongoTemplate mongo;

  public TestDataSeeder(MongoTemplate mongo) {
    this.mongo = mongo;
  }

  /** Полностью очищает коллекции и заново засевает ингредиенты. */
  public void resetAndSeed() {
    dropIfExists("ingredients");
    dropIfExists("tacos");
    dropIfExists("orders");

    mongo.insert(seedIngredients(), "ingredients");
  }

  private void dropIfExists(String collection) {
    if (mongo.collectionExists(collection)) {
      mongo.dropCollection(collection);
    }
  }

  private List<Ingredient> seedIngredients() {
    return Arrays.asList(
        new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
        new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
        new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
        new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
        new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
        new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
        new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
        new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
        new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
        new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
  }
}
