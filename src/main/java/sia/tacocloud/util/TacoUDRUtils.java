package sia.tacocloud.util;

import lombok.experimental.UtilityClass;
import sia.tacocloud.data.model.Ingredient;
import sia.tacocloud.data.model.IngredientUDT;
import sia.tacocloud.data.model.Taco;
import sia.tacocloud.data.model.TacoUDT;

@UtilityClass
public class TacoUDRUtils {

    public static IngredientUDT toIngredientUDT(Ingredient ingredient) {
        return new IngredientUDT(ingredient.getName(), ingredient.getType());
    }

    public static Ingredient toIngredient(IngredientUDT udt) {
        var id = udt.getName().toLowerCase().replaceAll("\\s+", "");
        return new Ingredient(id, udt.getName(), udt.getType());
    }

    public static TacoUDT toTacoUDT(Taco taco) {
        return new TacoUDT(taco.getName(), taco.getIngredients());
    }
}
