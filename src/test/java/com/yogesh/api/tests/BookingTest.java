package com.yogesh.api.tests;

import com.yogesh.api.assertions.BookingAssertions;
import com.yogesh.api.assertions.ErrorAssertions;
import com.yogesh.api.clients.BookingClient;
import com.yogesh.api.core.ResponseSpecificationFactory;
import com.yogesh.api.dataproviders.BookingDataProvider;
import com.yogesh.api.models.BookingRequest;
import com.yogesh.api.models.BookingResponse;
import com.yogesh.api.builders.BookingRequestBuilder;
import com.yogesh.api.testdata.BookingDataFactory;
import com.yogesh.api.validation.ResponseHeaderValidator;
import com.yogesh.api.validation.ResponseSchemaValidator;
import com.yogesh.api.validation.ResponseTimeValidator;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BookingTest {

    @Test
    public void shouldReturnAllBookings() {
        Response response = BookingClient.getBookings();
        response.then().spec(ResponseSpecificationFactory.okResponse());
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "Booking List should not be empty");
    }

    @Test
    public void shouldReturnBookingByName() {
        Response response = BookingClient.getBookings("sally");
        response.then().spec(ResponseSpecificationFactory.okResponse());
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "Booking list should not be empty");

    }

    @Test
    public void shouldReturnBookingById() {

        Response bookingsResponse = BookingClient.getBookings();
        int bookingId = bookingsResponse.jsonPath().getInt("[0].bookingid");

        Response bookingResponse = BookingClient.getBooking(bookingId);
        bookingResponse.then().spec(ResponseSpecificationFactory.okResponse());

        String firstName = bookingResponse.jsonPath().getString("firstname");
        Assert.assertNotNull(firstName, "First name should not be null");
        Assert.assertFalse(firstName.isBlank(), "First name should not be blank");
    }

    @Epic("Booking API")
    @Feature("Booking Management")
    @Story("Create Booking")
    @Owner("Yogesh")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify a new booking can be created successfully")
    @Test (
            dataProvider = "validBookings",
            dataProviderClass = BookingDataProvider.class
    )
    public void shouldCreateBooking(BookingRequest bookingRequest) {


        BookingResponse bookingResponse = createBookingSuccessfully(bookingRequest);

        BookingAssertions.verifyBookingDetails(bookingRequest,bookingResponse);

    }

    @Step("Create a new booking successfully")
    private BookingResponse createBookingSuccessfully(BookingRequest bookingRequest) {


        Response response = BookingClient.createBooking(bookingRequest);

        response.then().spec(ResponseSpecificationFactory.okResponse());
        ResponseSchemaValidator.validate(
                response,
                "schemas/booking-response-schema.json");

        ResponseTimeValidator.verifyResponseTimeLessThan(response);

        ResponseHeaderValidator.verifyHeaderContains(
                response,
                "Content-Type",
                "application/json"
        );


        return response.as(BookingResponse.class);

    }


    @Test
    public void shouldUpdateBooking() {
        BookingRequest createRequest =
                BookingDataFactory.validBooking();

        BookingResponse createdBooking =
                createBookingSuccessfully(createRequest);

        int bookingId = createdBooking.getBookingid();

        BookingRequest updateRequest = BookingDataFactory.validBooking();

        Response response = BookingClient.updateBooking(bookingId, updateRequest);


        response.then().spec(ResponseSpecificationFactory.okResponse());

        BookingRequest updatedBooking =
                response.as(BookingRequest.class);

        Assert.assertEquals(updatedBooking.getFirstname(),
                updateRequest.getFirstname());

        Assert.assertEquals(updatedBooking.getLastname(),
                updateRequest.getLastname());

        Assert.assertEquals(updatedBooking.getTotalprice(),
                updateRequest.getTotalprice());

        Assert.assertEquals(updatedBooking.isDepositpaid(),
                updateRequest.isDepositpaid());

        Assert.assertEquals(updatedBooking.getBookingdates().getCheckin(),
                updateRequest.getBookingdates().getCheckin());

        Assert.assertEquals(updatedBooking.getBookingdates().getCheckout(),
                updateRequest.getBookingdates().getCheckout());

        Assert.assertEquals(updatedBooking.getAdditionalneeds(),
                updateRequest.getAdditionalneeds());

    }

    @Test
    public void shouldPartialUpdateBooking() {

        BookingRequest createRequest = BookingDataFactory.validBooking();

        BookingResponse createdBooking = createBookingSuccessfully(createRequest);
        int bookingId = createdBooking.getBookingid();

        Map<String, Object> updates = new HashMap<>();

        updates.put("firstname", "John");
        updates.put("totalprice", 999);

        Response response = BookingClient.partialUpdateBooking(bookingId, updates);
        response.then().spec(ResponseSpecificationFactory.okResponse());

        BookingRequest updatedBooking =
                response.as(BookingRequest.class);

        Assert.assertEquals(updatedBooking.getFirstname(), (String) updates.get("firstname"));
        Assert.assertEquals(updatedBooking.getTotalprice(), (Integer) updates.get("totalprice"));

        Assert.assertEquals(updatedBooking.getLastname(),
                createRequest.getLastname());

        Assert.assertEquals(updatedBooking.isDepositpaid(),
                createRequest.isDepositpaid());

        Assert.assertEquals(updatedBooking.getBookingdates().getCheckin(),
                createRequest.getBookingdates().getCheckin());

        Assert.assertEquals(updatedBooking.getBookingdates().getCheckout(),
                createRequest.getBookingdates().getCheckout());

        Assert.assertEquals(updatedBooking.getAdditionalneeds(),
                createRequest.getAdditionalneeds());


    }

    @Test
    public void shouldDeleteBooking(){
        BookingRequest createRequest =
                BookingDataFactory.validBooking();

       BookingResponse createdBooking =createBookingSuccessfully(createRequest);
       int bookingId = createdBooking.getBookingid();

        Response deleteResponse = BookingClient.deleteBooking(bookingId);

        deleteResponse.then()
                .spec(ResponseSpecificationFactory.createdResponse());

        Response getResponse = BookingClient.getBooking(bookingId);

        getResponse.then()
                .spec(ResponseSpecificationFactory.notFoundResponse());


    }

    @Test
    public void shouldRetryDemo() {
        System.out.println("Executing shouldRetryDemo");
        Assert.fail("Intentional Failure");
    }


    @Test(
     dataProvider = "negativeBookings",
     dataProviderClass = BookingDataProvider.class
    )
    public void exploreNegativeBookings(
            BookingRequest bookingRequest,
            String scenario,
            int expectedStatusCode) {


        Response response = BookingClient.createBooking(bookingRequest);

        ErrorAssertions.verifyErrorResponse(
                response,
                500,
                "text/plain"
        );

    }




}