package pl.ioad.skyflow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.ioad.skyflow.database.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SkyFlowApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void databaseInitialized() {
        assertThat(userRepository.findAll()).hasSize(1);
    }

}
