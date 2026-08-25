package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserApiClient {

    private static final String BASE_URL = "https://qa-desk.education-services.ru";
    private static final String SIGNUP_ENDPOINT = "/api/signup";
    private static final String LOGIN_ENDPOINT = "/api/signin";

    public Response registerUser(String email, String password) {
        SignupRequest requestBody = new SignupRequest(email, password, password);

        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(SIGNUP_ENDPOINT);
    }

    public Response loginUser(String email, String password) {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(Map.of("email", email, "password", password))
                .when()
                .post(LOGIN_ENDPOINT);
    }
}