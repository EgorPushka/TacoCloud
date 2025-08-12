package sia.tacocloud.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;
import sia.tacocloud.BaseTest;
import sia.tacocloud.data.model.TacoOrder;

class OrderControllerTest extends BaseTest {

  @Autowired private MockMvc mvc;
  @Autowired private MongoTemplate mongoTemplate;

  @Test
  @DisplayName("Оформление заказа: /design -> /orders")
  void orderForm() throws Exception {
    // сначала добавим тако в заказ через /design
    mvc.perform(
            post("/design")
                .param("name", "taco test 1")
                .param("ingredients", "FLTO", "GRBF", "CHED")
                .sessionAttr("tacoOrder", new TacoOrder()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/orders/current"));

    // затем оформим заказ
    mvc.perform(
            post("/orders")
                .param("deliveryName", "John Doe")
                .param("deliveryStreet", "123 Main St")
                .param("deliveryCity", "Springfield")
                .param("deliveryState", "IL")
                .param("deliveryZip", "12345")
                .param("ccNumber", "4111111111111111")
                .param("ccExpiration", "12/29")
                .param("ccCVV", "123"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/"));

    var orders = mongoTemplate.getCollection("tacoOrder").countDocuments();
    assertThat(orders).isEqualTo(1L);
  }
}
