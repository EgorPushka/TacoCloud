package sia.tacocloud.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sia.tacocloud.data.dto.RegistrationForm;
import sia.tacocloud.data.repository.UserRepository;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @GetMapping
  public String registerForm() {
    return "registration";
  }

  @PostMapping
  public String processRegisterForm(RegistrationForm registrationForm) {
    userRepository.save(registrationForm.toUser(passwordEncoder));
    return "redirect:/design";
  }
}
