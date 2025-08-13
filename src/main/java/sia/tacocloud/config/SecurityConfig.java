package sia.tacocloud.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    List<UserDetails> users = new ArrayList<>();

    users.add(
        new User(
            "user",
            passwordEncoder.encode("user"),
            List.of(new SimpleGrantedAuthority("ROLE_USER"))));

    users.add(
        new User(
            "admin",
            passwordEncoder.encode("admin"),
            List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));

    return new InMemoryUserDetailsManager(users);
  }


}
