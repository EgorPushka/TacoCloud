package sia.tacocloud.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.jdbc.Sql;
import sia.tacocloud.BaseTest;
import sia.tacocloud.data.model.Ingredient;
import sia.tacocloud.data.model.TacoOrder;

class DesignTacoControllerTest extends BaseTest {

  @Test
  @Sql(scripts = {"/clearDB.sql", "/addIngredient.sql"})
  @DisplayName("GET /design -> view design")
  void test_getDesign_Ok() throws Exception {
    var expectedIngredientTypes = new String[] {"wrap", "protein", "veggies", "cheese", "sauce"};

    mockMvc
        .perform(get("/design"))
        .andExpect(status().isOk())
        .andExpect(view().name("design"))
        .andExpect(model().attributeExists(expectedIngredientTypes))
        .andExpect(model().attribute("tacoOrder", notNullValue()))
        .andExpect(model().attribute("tacoForm", notNullValue()));
  }

  @Test
  @Sql(scripts = {"/clearDB.sql", "/addIngredient.sql"})
  @DisplayName("POST /design -> validation error -> stay on design")
  void test_postDesign_withValidationError_stayOnDesign() throws Exception {
    var mockHttpSession = new MockHttpSession();
    mockMvc.perform(get("/design").session(mockHttpSession)).andExpect(status().isOk());

    var before = (TacoOrder) mockHttpSession.getAttribute("tacoOrder");
    assertThat(before).isNotNull();
    var sizeBefore = before.getTacos().size();

    mockMvc
        .perform(
            post("/design")
                .session(mockHttpSession)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "testTaco"))
        .andExpect(status().isOk())
        .andExpect(view().name("design"));

    var after = (TacoOrder) mockHttpSession.getAttribute("tacoOrder");
    assertThat(after).isNotNull();
    var sizeAfter = after.getTacos().size();
    assertThat(sizeBefore).isEqualTo(sizeAfter);
  }

  @Test
  @Sql(scripts = {"/clearDB.sql", "/addIngredient.sql"})
  @DisplayName("POST /design valid")
  void test_postDesign_addsTacoAndRedirect() throws Exception {
    var mockHttpSession = new MockHttpSession();
    mockMvc.perform(get("/design").session(mockHttpSession)).andExpect(status().isOk());

    var selectedIds = new String[] {"FLTO", "GRBF", "CHED"};

    mockMvc
        .perform(
            post("/design")
                .session(mockHttpSession)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Test Taco Name")
                .param("ingredientIds", selectedIds))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/orders/current"));

    var tacoOrder = (TacoOrder) mockHttpSession.getAttribute("tacoOrder");

    assertThat(tacoOrder).isNotNull();
    var orderTacos = tacoOrder.getTacos();
    assertThat(orderTacos).hasSize(1);

    assertThat(orderTacos.getFirst().getName()).isEqualTo("Test Taco Name");
    var ingredientsInOrderTaco =
        orderTacos.getFirst().getIngredients().stream()
            .map(Ingredient::getId)
            .toArray(String[]::new);
    assertThat(ingredientsInOrderTaco).contains(selectedIds);
  }
}
