package sia.tacocloud;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import sia.tacocloud.testsupport.TestDataConfig;
import sia.tacocloud.testsupport.TestDataSeeder;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
@Import(TestDataConfig.class)
public abstract class BaseTest {

    @Autowired
    private TestDataSeeder testDataSeeder;

    @BeforeEach
    void resetDb() {
        testDataSeeder.resetAndSeed();
    }

}
