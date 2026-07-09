package com.yogesh.api.clients;

import com.yogesh.api.config.ConfigManager;
import com.yogesh.api.core.RequestSpecificationFactory;
import com.yogesh.api.models.BookingRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public final class BookingClient {
    private BookingClient() {
        throw new AssertionError("Utility class should not be instantiated.");
    }

    public static Response getBookings() {
        RequestSpecification requestSpecification =
                RequestSpecificationFactory.getRequestSpecification();

        return given()
                .spec(requestSpecification)
                .when()
                .get("/booking");
    }

    public static Response getBookings(String firstName){
        RequestSpecification requestSpecification=
                RequestSpecificationFactory.getRequestSpecification();

        return given()
                .spec(requestSpecification)
                .queryParam("firstname",firstName)
                .when()
                .get("/booking");

    }

    public static Response getBooking(int bookingId) {

        return given()
                .baseUri(ConfigManager.getBaseUrl())
                // Restful Booker returns 418 when Accept: application/json is sent.
                .pathParam("id", bookingId)
                .when()
                .get("/booking/{id}");
    }

    public static Response createBooking(BookingRequest bookingRequest){
        RequestSpecification requestSpecification=
                RequestSpecificationFactory.getRequestSpecification();

        return given()
                .spec(requestSpecification)
                .header("Accept", "*/*")
                .body(bookingRequest)
                .when()
                .post("/booking");
    }
}
