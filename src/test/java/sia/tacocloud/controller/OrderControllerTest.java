package sia.tacocloud.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.jdbc.Sql;
import sia.tacocloud.BaseTest;
import sia.tacocloud.data.model.Ingredient;
import sia.tacocloud.data.model.Taco;
import sia.tacocloud.data.model.TacoOrder;
import sia.tacocloud.data.repository.IngredientRepository;

class OrderControllerTest extends BaseTest {

  @Autowired
  private IngredientRepository ingredientRepository;

  @Test
  @DisplayName("GET /orders/current -> view 'orderForm'")
  void test_getOrderForm_Ok() throws Exception {
    var taco = Instancio.create(Taco.class);
    var tacoOrder = Instancio.create(TacoOrder.class);
    tacoOrder.setTacos(List.of(taco));

    var session = new MockHttpSession();
    session.setAttribute("tacoOrder", tacoOrder);

    mockMvc
        .perform(get("/orders/current").session(session))
        .andExpect(status().isOk())
        .andExpect(view().name("orderForm"));
  }

  @Test
  @DisplayName("POST /orders -> invalid -> stay on 'orderForm'")
  void test_processTaco_invalid_stayOnForm() throws Exception {
    var taco = Instancio.create(Taco.class);
    var invalidOrder = Instancio.create(TacoOrder.class);
    invalidOrder.setTacos(List.of(taco));
    invalidOrder.setCcCVV(null);

    var session = new MockHttpSession();
    session.setAttribute("tacoOrder", invalidOrder);

    mockMvc
        .perform(
            post("/orders").session(session).contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk())
        .andExpect(view().name("orderForm"));
  }

  @Test
  @Sql(scripts = {"/clearDB.sql", "/addIngredient.sql"})
  @DisplayName("POST /orders -> valid -> save() and redirect '/'")
  void test_processTaco_valid_redirectAndSave() throws Exception {
    var allIngredients = ingredientRepository.findAll();
    var taco = Instancio.create(Taco.class);
    taco.setId(null);
    taco.setIngredients(allIngredients);

    var tacoOrder = Instancio.create(TacoOrder.class);
    tacoOrder.setId(null);
    tacoOrder.setTacos(List.of(taco));
    tacoOrder.setCcCVV("123");
    tacoOrder.setCcExpiration("12/12");

    taco.setOrder(tacoOrder);

    var session = new MockHttpSession();
    session.setAttribute("tacoOrder", tacoOrder);

    mockMvc
        .perform(
            post("/orders").session(session).contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/"));
  }
}
