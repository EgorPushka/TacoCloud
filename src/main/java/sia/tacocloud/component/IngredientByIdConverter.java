package sia.tacocloud.component;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import sia.tacocloud.data.model.Ingredient;

import java.util.HashMap;
import java.util.Map;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private static final Map<String, Ingredient> INGREDIENT_MAP = new HashMap<>();

    static {
        INGREDIENT_MAP.put("FLTO", new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
        INGREDIENT_MAP.put("COTO", new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
        INGREDIENT_MAP.put("GRBF", new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
        INGREDIENT_MAP.put("CARN", new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
        INGREDIENT_MAP.put("TMTO", new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
        INGREDIENT_MAP.put("LETC", new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
        INGREDIENT_MAP.put("CHED", new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
        INGREDIENT_MAP.put("JACK", new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
        INGREDIENT_MAP.put("SLSA", new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
        INGREDIENT_MAP.put("SRCR", new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
    }

    @Override
    public Ingredient convert(String id) {
        return INGREDIENT_MAP.get(id);
    }

}
