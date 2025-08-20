package sia.tacocloud.data.repository;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.data.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
  Optional<User> findByUsername(String username);
}
