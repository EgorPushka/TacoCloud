package sia.tacocloud.data.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.data.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

  @Override
  List<Ingredient> findAll();
}
