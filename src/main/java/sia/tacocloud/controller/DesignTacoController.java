package sia.tacocloud.controller;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import sia.tacocloud.data.dto.DesignTacoFrom;
import sia.tacocloud.data.model.Ingredient;
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
    var ingredients = ingredientRepository.findAll();
    var byType = ingredients.stream().collect(Collectors.groupingBy(Ingredient::getType));
    for (var type : Ingredient.Type.values()) {
      model.addAttribute(
          type.toString().toLowerCase(), byType.getOrDefault(type, Collections.emptyList()));
    }
  }

  @ModelAttribute(name = "tacoOrder")
  public TacoOrder order() {
    return new TacoOrder();
  }

  @ModelAttribute(name = "tacoForm")
  public DesignTacoFrom tacoForm() {
    return new DesignTacoFrom();
  }

  @GetMapping
  public String showDesignedForm() {
    return "design";
  }

  @PostMapping
  public String processTaco(
      @Valid @ModelAttribute("tacoForm") DesignTacoFrom form,
      Errors errors,
      @ModelAttribute TacoOrder tacoOrder) {

    if (errors.hasErrors()) {
      return "design";
    }

    var ingredients = ingredientRepository.findAllById(form.getIngredientIds());

    Taco taco = new Taco();
    taco.setName(form.getName());
    taco.setIngredients(ingredients);

    tacoOrder.addTaco(taco);
    log.info("Processing Taco: {}", taco);

    // sessionStatus.setComplete(); if session end now
    return "redirect:/orders/current";
  }
}
