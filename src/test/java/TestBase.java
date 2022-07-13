import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestBase {
    @Test
    @BeforeAll
    static void beforeAllTests() {
        RestAssured.baseURI = "https://reqres.in/api";
    }
}
