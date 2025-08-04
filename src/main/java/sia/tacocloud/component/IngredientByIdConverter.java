package sia.tacocloud.component;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import sia.tacocloud.data.model.Ingredient;
import sia.tacocloud.data.repository.IngredientRepository;

@Component
@RequiredArgsConstructor
public class IngredientByIdConverter implements Converter<String, Ingredient> {

  private final IngredientRepository ingredientRepository;

  @Override
  public Ingredient convert(String id) {
    return ingredientRepository.findById(id).orElse(null);
  }
}
