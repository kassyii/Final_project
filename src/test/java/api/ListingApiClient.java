package api;

import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ListingApiClient {

    private static final String BASE_URL = "https://qa-desk.education-services.ru";
    private static final String LISTINGS_ENDPOINT = "/api/listings/";
    private static final String DELETE_LISTING_ENDPOINT = "/api/listings/";

    // Находим ID объявления по названию
    public int getListingIdByName(String title) {
        int currentPage = 1;
        int totalPages = 1;
        String targetTitle = title.trim();

        while (currentPage <= totalPages) {
            Response response = given()
                    .baseUri(BASE_URL)
                    .contentType(ContentType.JSON)
                    .when()
                    .get(LISTINGS_ENDPOINT + currentPage);

            response.then().statusCode(200);

            // Обновляем общее количество страниц
            Integer pagesFromApi = response.jsonPath().get("totalPages");
            if (pagesFromApi != null) {
                totalPages = pagesFromApi;
            }

            List<Map<String, Object>> offers = response.jsonPath().getList("offers");

            if (offers != null) {
                for (Map<String, Object> offer : offers) {
                    String offerName = (String) offer.get("name");
                    if (offerName != null && targetTitle.equalsIgnoreCase(offerName.trim())) {
                        return ((Number) offer.get("id")).intValue();
                    }
                }
            }
            currentPage++;
        }

        throw new RuntimeException("Объявление с названием '" + title + "' не найдено ни на одной из " + totalPages + " страниц API.");
    }

    // Удаляем объявление по ID с токеном Authorization: Bearer <token>
    public Response deleteListingById(int listingId, String token) {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .delete(LISTINGS_ENDPOINT + listingId);
    }

    public Response createListingViaApi(String name, int price, String condition, String token) {
//        return given()
//                .baseUri(BASE_URL)
//                .header("Authorization", "Bearer " + token)
//                .contentType("multipart/form-data")
//                .multiPart("name", name)
//                .multiPart("category", "Авто")
//                .multiPart("condition", condition)
//                .multiPart("city", "Москва")
//                .multiPart("description", "Тестовое описание")
//                .multiPart("price", String.valueOf(price))
//                .when()
//                .post("/api/create-listing/")
//                .then()
//                .log().all() // <--- Выведет полные данные созданного объекта из базы
//                .extract().response();

        return given()
                .config(RestAssuredConfig.config().encoderConfig(
                        EncoderConfig.encoderConfig().defaultContentCharset("UTF-8")
                ))
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer " + token)
                .contentType("multipart/form-data; charset=UTF-8")
                .multiPart("name", name)
                .multiPart("category", "Авто")
                .multiPart("condition", condition)
                .multiPart("city", "Москва")
                .multiPart("description", "Тестовое описание")
                .multiPart("price", String.valueOf(price))
                .when()
                .post("/api/create-listing/");
    }
}