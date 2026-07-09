package com.yogesh.api.tests;

import com.yogesh.api.clients.BookingClient;
import com.yogesh.api.core.ResponseSpecificationFactory;
import com.yogesh.api.models.BookingRequest;
import com.yogesh.api.models.BookingResponse;
import com.yogesh.api.testdata.BookingRequestBuilder;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

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

        Response response = BookingClient.createBooking(bookingRequest);
        response.then().spec(ResponseSpecificationFactory.okResponse());

        BookingResponse bookingResponse = response.as(BookingResponse.class);

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

}
