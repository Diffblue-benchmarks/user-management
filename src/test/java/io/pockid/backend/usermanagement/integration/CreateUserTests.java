package io.pockid.backend.usermanagement.integration;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.nio.file.Files;
import java.nio.file.Paths;

import static com.jayway.restassured.RestAssured.given;
import static io.pockid.backend.usermanagement.api.ApiConstants.USER_ID_REQUEST_HEADER;
import static org.assertj.core.api.Assertions.assertThat;

class CreateUserTests extends UserManagementApplicationTests {

    private JSONObject user;

    @BeforeEach
    void prepareTest() throws Exception {
        var path = Paths.get(new ClassPathResource("src/test/resources/user.json").getPath());
        var userJson = Files.readString(path);
        user = new JSONObject(userJson);
    }

    @Test
    @DisplayName("Should create user with all valid fields")
    void shouldCreateUserWithAllValidFields() throws Exception {
        var uid = "123456";
        var createUserResponse = createUser(uid);
        assertThat(createUserResponse.statusCode()).isEqualTo(200);
        var createdUser = new JSONObject(createUserResponse.getBody().asString());
        assertThat(user.get("firstName")).isEqualTo(createdUser.get("firstName"));
        assertThat(user.get("lastName")).isEqualTo(createdUser.get("lastName"));
        assertThat(user.get("dateOfBirth")).isEqualTo(createdUser.get("dateOfBirth"));
        assertThat(user.get("nickName")).isEqualTo(createdUser.get("nickName"));
        assertThat(user.get("isParent")).isEqualTo(createdUser.get("isParent"));
        assertThat(uid).isEqualTo(createdUser.get("uid"));
    }

    @Test
    @DisplayName("Should NOT create user with missing firstName")
    void shouldNotCreateUserWithMissingFirstName() throws Exception {
        var uid = "123456";
        user.remove("firstName");
        var createUserResponse = createUser(uid);
        assertThat(createUserResponse.statusCode()).isEqualTo(400);
        var savedUser = userRepository.findByUid(uid);
        assertThat(savedUser.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Should NOT create user with missing lastName")
    void shouldNotCreateUserWithMissingLastName() throws Exception {
        var uid = "123456";
        user.remove("lastName");
        var createUserResponse = createUser(uid);
        assertThat(createUserResponse.statusCode()).isEqualTo(400);
        var savedUser = userRepository.findByUid(uid);
        assertThat(savedUser.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Should NOT create user with missing nickName")
    void shouldCreateUserWithMissingNickName() throws Exception {
        var uid = "123456";
        user.remove("nickName");
        var createUserResponse = createUser(uid);
        assertThat(createUserResponse.statusCode()).isEqualTo(200);
        var savedUser = userRepository.findByUid(uid);
        assertThat(savedUser.isPresent()).isTrue();
    }


    private Response createUser(String uid) {
        return given().urlEncodingEnabled(true)
                .body(user.toString())
                .contentType(ContentType.JSON)
                .header(USER_ID_REQUEST_HEADER, uid)
                .post()
                .then()
                .extract()
                .response();
    }
}
