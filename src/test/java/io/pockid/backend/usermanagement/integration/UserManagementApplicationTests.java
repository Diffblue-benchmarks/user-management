package io.pockid.backend.usermanagement.integration;

import com.jayway.restassured.RestAssured;
import io.pockid.backend.usermanagement.persistence.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.gcp.pubsub.PubSubAdmin;

import static io.pockid.backend.usermanagement.api.ApiConstants.API_V_1_USERS;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class UserManagementApplicationTests {

    static {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Autowired
    UserRepository userRepository;
    @LocalServerPort
    private int serverPort;
    @Autowired
    private PubSubAdmin pubSubAdmin;

    @BeforeAll
    void init() {
        userRepository.deleteAll();
        pubSubAdmin.createTopic("user");
        RestAssured.baseURI = "http://localhost:" + serverPort + API_V_1_USERS;
    }

    @AfterAll
    void destroy() {
        pubSubAdmin.deleteTopic("user");
    }
}
