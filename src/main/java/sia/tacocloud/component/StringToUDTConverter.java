package sia.tacocloud.component;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import sia.tacocloud.data.model.IngredientUDT;
import sia.tacocloud.data.repository.IngredientRepository;
import sia.tacocloud.util.TacoUDRUtils;

@Component
@RequiredArgsConstructor
public class StringToUDTConverter implements Converter<String, IngredientUDT> {

  private final IngredientRepository ingredientRepository;

  @Override
  public IngredientUDT convert(String id) {
    return ingredientRepository
        .findById(id)
        .map(TacoUDRUtils::toIngredientUDT)
        .orElseThrow(() -> new IllegalArgumentException("Unknown ingredient: " + id));
  }
}
