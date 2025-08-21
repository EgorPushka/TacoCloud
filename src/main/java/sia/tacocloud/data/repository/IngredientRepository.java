package sia.tacocloud.data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import sia.tacocloud.data.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, String> {

//  @Override
//  List<Ingredient> findAll();
}
