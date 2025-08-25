package sia.tacocloud.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import sia.tacocloud.BaseTest;
import sia.tacocloud.data.repository.UserRepository;

class RegistrationControllerTestIT extends BaseTest {

  @Autowired private UserRepository userRepository;

  @Test
  @DisplayName("GET /registration -> Ok, view -> registration")
  void test_getRegistration_Ok() throws Exception {
    mockMvc
        .perform(get("/registration"))
        .andExpect(status().isOk())
        .andExpect(view().name("registration"));
  }

  @Test
  @DisplayName("POST /registration -> redirect:/design and save user")
  void test_postRegistration_redirect_andSave() throws Exception {
    var username = "user";
    mockMvc
        .perform(
            post("/registration")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", username)
                .param("password", "password")
                .param("fullname", "fullname"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/design"));

    var savedUserOptional = userRepository.findByUsername(username);
    assertThat(savedUserOptional).isPresent();
  }
}
