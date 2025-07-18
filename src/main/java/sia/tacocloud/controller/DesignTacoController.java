package sia.tacocloud.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import sia.tacocloud.data.model.Ingredient;
import sia.tacocloud.data.model.IngredientRef;
import sia.tacocloud.data.model.Taco;
import sia.tacocloud.data.model.TacoOrder;
import sia.tacocloud.data.repository.IngredientRepository;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

  private final IngredientRepository ingredientRepository;

  @ModelAttribute
  public void addIngredientsToModel(Model model) {
    //        var ingredients = Arrays.asList(
    //                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
    //                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
    //                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
    //                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
    //                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
    //                new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
    //                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
    //                new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
    //                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
    //                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
    //        );
    var ingredients = ingredientRepository.findAll();
    var types = Ingredient.Type.values();
    for (var type : types) {
      model.addAttribute(type.toString().toLowerCase(), filterByTypes(ingredients, type));
    }
  }

  @ModelAttribute(name = "tacoOrder")
  public TacoOrder order() {
    return new TacoOrder();
  }

  @ModelAttribute(name = "taco")
  public Taco taco() {
    return new Taco();
  }

  @GetMapping
  public String showDesignedForm() {
    return "design";
  }

  @PostMapping
  public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
    if (errors.hasErrors()) {
      return "design";
    }
    tacoOrder.addTaco(taco);
    log.info("Processing Taco: {}", taco);
    return "redirect:/orders/current";
  }

  private Iterable<Ingredient> filterByTypes(List<Ingredient> ingredients, Ingredient.Type type) {
    return ingredients.stream().filter(t -> t.getType().equals(type)).toList();
  }
}
