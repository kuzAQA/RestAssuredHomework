import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;


public class RestTests extends TestBase {
    String emailAndPass = "{\"email\": \"eve.holt@reqres.in\",\"password\": \"123123\"}";
    String testEmail = "{\"email\": \"sydn123ey@fife\"}";

    @Test
    @DisplayName("Создание пользователя")
    void createUserTest() {
        String nameAndJob = "{\"name\": \"TestUser\",\"job\": \"Chill\"}";
        given()
                .log().uri()
                .body(nameAndJob)
                .contentType(ContentType.JSON)
                .when()
                .post("/users/2")
                .then()
                .log().body()
                .statusCode(201)
                .body("name", is("TestUser"))
                .body("job", is("Chill"));
    }

    @Test
    @DisplayName("Проверка наличия пользователя")
    void checkSingleUserTest() {
        given()
                .log().uri()
                .when()
                .get("/users/10")
                .then()
                .log().body()
                .statusCode(200)
                .body("data.first_name", is("Byron"))
                .body("data.last_name", is("Fields"));
    }

    @Test
    @DisplayName("Удаление пользователя")
    void deleteUserTest() {
        given()
                .log().uri()
                .when()
                .delete("/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("Успешная авторизация")
    void loginTest() {
        String token = "";
        given()
                .log().uri()
                .body(emailAndPass)
                .contentType(ContentType.JSON)
                .when()
                .post("/login")
                .then()
                .log().body()
                .statusCode(200)
                .body("token", is(notNullValue()));
    }

    @Test
    @DisplayName("Успешная регистрация")
    void registerUserTest() {
        given()
                .log().uri()
                .body(emailAndPass)
                .contentType(ContentType.JSON)
                .when()
                .post("/register")
                .then()
                .statusCode(200)
                .log().body()
                .body("token", is(notNullValue()));
    }

    @Test
    @DisplayName("Неуспешная авторизация")
    void unsuccessfulLoginTest() {
        given()
                .log().uri()
                .body(testEmail)
                .contentType(ContentType.JSON)
                .when()
                .post("/login")
                .then()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    @DisplayName("Неуспешная регистрация")
    void unsuccessfulRegisterUserTest() {
        given()
                .log().uri()
                .body(testEmail)
                .contentType(ContentType.JSON)
                .when()
                .post("/register")
                .then()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

}
