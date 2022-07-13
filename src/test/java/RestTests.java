import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

public class RestTests {
    String emailAndPass = "{\"email\": \"eve.holt@reqres.in\",\"password\": \"123123\"}";
    String nameAndJob = "{\"name\": \"TestUser\",\"job\": \"Chill\"}";
    String testEmail = "{\"email\": \"sydn123ey@fife\"}";
    String URLApi = "https://reqres.in/api";

    @Test
    @DisplayName("Создание пользователя")
    void createUserTest() {
        given()
                .log().uri()
                .body(nameAndJob)
                .contentType(ContentType.JSON)
                .when()
                .post(URLApi + "/users/2")
                .then()
                .log().body()
                .body("name", is("TestUser"))
                .body("job", is("Chill"))
                .statusCode(201);
    }

    @Test
    @DisplayName("Проверка наличия пользователя")
    void checkSingleUserTest() {
        given()
                .log().uri()
                .when()
                .get(URLApi + "/users/10")
                .then()
                .log().body()
                .body("data.first_name", is("Byron"))
                .body("data.last_name", is("Fields"))
                .statusCode(200);
    }

    @Test
    @DisplayName("Удаление пользователя")
    void deleteUserTest() {
        given()
                .log().uri()
                .when()
                .delete(URLApi + "/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("Успешная авторизация")
    void loginTest() {
        given()
                .log().uri()
                .body(emailAndPass)
                .contentType(ContentType.JSON)
                .when()
                .post(URLApi + "/login")
                .then()
                .log().body()
                .body("token", is("QpwL5tke4Pnpja7X4"))
                .statusCode(200);
    }

    @Test
    @DisplayName("Успешная регистрация")
    void registerUserTest() {
        given()
                .log().uri()
                .body(emailAndPass)
                .contentType(ContentType.JSON)
                .when()
                .post(URLApi + "/register")
                .then()
                .log().body()
                .body("token", is("QpwL5tke4Pnpja7X4"))
                .statusCode(200);
    }

    @Test
    @DisplayName("Неуспешная авторизация")
    void unsuccessfulLoginTest() {
        given()
                .log().uri()
                .body(testEmail)
                .contentType(ContentType.JSON)
                .when()
                .post(URLApi + "/login")
                .then()
                .log().body()
                .body("error", is("Missing password"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Неспешная регистрация")
    void unsuccessfulRegisterUserTest() {
        given()
                .log().uri()
                .body(testEmail)
                .contentType(ContentType.JSON)
                .when()
                .post(URLApi + "/register")
                .then()
                .log().body()
                .body("error", is("Missing password"))
                .statusCode(400);
    }

}
