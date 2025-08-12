package sia.tacocloud.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import sia.tacocloud.BaseTest;
import sia.tacocloud.data.model.TacoOrder;

class DesignTacoControllerTest extends BaseTest {

  @Autowired private MockMvc mvc;

  @Test
  @DisplayName("GET /design: view 'design' и категории ингредиентов из embedded Mongo")
  void showDesignForm_populatesModel() throws Exception {
    mvc.perform(get("/design"))
        .andExpect(status().isOk())
        .andExpect(view().name("design"))
        .andExpect(model().attributeExists("wrap", "protein", "veggies", "cheese", "sauce"))
        .andExpect(
            model()
                .attribute(
                    "wrap",
                    everyItem(
                        hasProperty("type", is(sia.tacocloud.data.model.Ingredient.Type.WRAP)))));
  }

  @Test
  @DisplayName("POST /design (валидно) -> редирект на /orders/current")
  void processTaco_success() throws Exception {
    mvc.perform(
            post("/design")
                .param("name", "Test Taco")
                .param("ingredients", "FLTO", "GRBF", "CHED")
                .sessionAttr("tacoOrder", new TacoOrder()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/orders/current"));
  }

  @Test
  @DisplayName("POST /design (валидация) -> остаемся на 'design' с ошибками")
  void processTaco_validationError() throws Exception {
    mvc.perform(post("/design").param("name", "Abc").sessionAttr("tacoOrder", new TacoOrder()))
        .andExpect(status().isOk())
        .andExpect(view().name("design"))
        .andExpect(model().attributeHasFieldErrors("taco", "name", "ingredients"));
  }
}
