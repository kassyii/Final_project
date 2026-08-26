package api;

import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ListingApiClient {

    private static final String BASE_URL = "https://qa-desk.education-services.ru";
    private static final String LISTINGS_ENDPOINT = "/api/listings/";

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

    public Response deleteListingById(int listingId, String token) {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .delete(LISTINGS_ENDPOINT + listingId);
    }
}