package sia.tacocloud.data.dto;

import org.springframework.security.crypto.password.PasswordEncoder;
import sia.tacocloud.data.model.User;

public record RegistrationForm(String username, String password, String fullname) {

  public User toUser(PasswordEncoder passwordEncoder) {
    return new User(username, passwordEncoder.encode(password), fullname);
  }

}
