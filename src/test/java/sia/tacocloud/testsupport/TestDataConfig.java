package sia.tacocloud.testsupport;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

@TestConfiguration
@Profile({"test"})
public class TestDataConfig {

  @Bean
  public TestDataSeeder testDataSeeder(MongoTemplate mongoTemplate) {
    return new TestDataSeeder(mongoTemplate);
  }

}
