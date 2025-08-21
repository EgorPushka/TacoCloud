package sia.tacocloud.service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import sia.tacocloud.data.model.User;
import sia.tacocloud.data.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class OAuth2LoginUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    var delegate = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    String registrationId =
        userRequest.getClientRegistration().getRegistrationId(); // "github" / "google"
    Map<String, Object> attrs = oAuth2User.getAttributes();

    String localUsername;
    String displayName;
    String nameAttributeKey;

    switch (registrationId) {
      case "github" -> {
        String ghLogin = String.valueOf(attrs.get("login"));
        displayName = String.valueOf(attrs.getOrDefault("name", ghLogin));
        localUsername = "gh:" + ghLogin; // локальный username
        nameAttributeKey = "login"; // что вернуть как OAuth2User.getName()
      }
      case "google" -> {
        String email = String.valueOf(attrs.get("email"));
        displayName = String.valueOf(attrs.getOrDefault("name", email));
        localUsername = "gg:" + email; // локальный username
        nameAttributeKey = "email";
      }
      default -> throw new IllegalArgumentException("Unsupported provider: " + registrationId);
    }

    userRepository
        .findByUsername(localUsername)
        .orElseGet(
            () -> {
              // создаём локального пользователя с ролью USER
              var randomPassword = UUID.randomUUID().toString();
              var user =
                  new User(localUsername, passwordEncoder.encode(randomPassword), displayName);
              return userRepository.save(user);
            });

    // Возвращаем аутентифицированного OAuth2User с ROLE_USER
    Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("ROLE_USER"));
    return new DefaultOAuth2User(authorities, attrs, nameAttributeKey);
  }
}
