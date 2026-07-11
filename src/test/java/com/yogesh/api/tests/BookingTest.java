package com.yogesh.api.tests;

import com.yogesh.api.clients.BookingClient;
import com.yogesh.api.core.ResponseSpecificationFactory;
import com.yogesh.api.models.BookingRequest;
import com.yogesh.api.models.BookingResponse;
import com.yogesh.api.testdata.BookingRequestBuilder;
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

    @Test
    public void shouldCreateBooking() {

        BookingRequest bookingRequest =
                BookingRequestBuilder.validBooking().
                        build();

        BookingResponse bookingResponse = createBookingSuccessfully(bookingRequest);

        Assert.assertTrue(bookingResponse.getBookingid() > 0,
                "booking id should be generated");

        Assert.assertEquals(bookingResponse.getBooking().getFirstname(), bookingRequest.getFirstname());
        Assert.assertEquals(bookingResponse.getBooking().getLastname(), bookingRequest.getLastname());
        Assert.assertEquals(bookingResponse.getBooking().getTotalprice(), bookingRequest.getTotalprice());
        Assert.assertEquals(bookingResponse.getBooking().isDepositpaid(), bookingRequest.isDepositpaid());
        Assert.assertEquals(bookingResponse.getBooking().getBookingdates().getCheckin(),
                bookingRequest.getBookingdates().getCheckin());
        Assert.assertEquals(bookingResponse.getBooking().getBookingdates().getCheckout(),
                bookingRequest.getBookingdates().getCheckout());
        Assert.assertEquals(bookingResponse.getBooking().getAdditionalneeds(), bookingRequest.getAdditionalneeds());


    }

    private BookingResponse createBookingSuccessfully(BookingRequest bookingRequest) {


        Response response = BookingClient.createBooking(bookingRequest);
        response.then().spec(ResponseSpecificationFactory.okResponse());

        return response.as(BookingResponse.class);

    }


    @Test
    public void shouldUpdateBooking() {
        BookingRequest createRequest =
                BookingRequestBuilder.validBooking().
                        build();

        BookingResponse createdBooking =
                createBookingSuccessfully(createRequest);

        int bookingId = createdBooking.getBookingid();

        BookingRequest updateRequest = BookingRequestBuilder.validBooking()
                .withFirstname("John")
                .withLastname("Smith")
                .withTotalPrice(999)
                .withDepositPaid(false)
                .withAdditionalNeeds("Lunch")
                .withCheckin("2025-08-01")
                .withCheckout("2025-08-14")
                .build();

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

        BookingRequest createRequest = BookingRequestBuilder.validBooking().build();

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
                BookingRequestBuilder.validBooking().build();

       BookingResponse createdBooking =createBookingSuccessfully(createRequest);
       int bookingId = createdBooking.getBookingid();

        Response deleteResponse = BookingClient.deleteBooking(bookingId);

        deleteResponse.then()
                .spec(ResponseSpecificationFactory.createdResponse());

        Response getResponse = BookingClient.getBooking(bookingId);

        getResponse.then()
                .spec(ResponseSpecificationFactory.notFoundResponse());


    }

}
