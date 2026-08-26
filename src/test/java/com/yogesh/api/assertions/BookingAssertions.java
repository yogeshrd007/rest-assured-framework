package com.yogesh.api.assertions;

import com.yogesh.api.models.BookingRequest;
import com.yogesh.api.models.BookingResponse;
import io.qameta.allure.Step;
import org.testng.Assert;

public final class BookingAssertions {

    private BookingAssertions() {

    }

    @Step("verify booking details")
    public static void verifyBookingDetails(
            BookingRequest expected, BookingResponse actual){

        Assert.assertTrue(actual.getBookingid() > 0,
                "Booking id should be generated");

        Assert.assertEquals(actual.getBooking().getFirstname(), expected.getFirstname());

        Assert.assertEquals(actual.getBooking().getLastname(), expected.getLastname());

        Assert.assertEquals(actual.getBooking().getTotalprice(), expected.getTotalprice());

        Assert.assertEquals(actual.getBooking().isDepositpaid(), expected.isDepositpaid());

        Assert.assertEquals(
                actual.getBooking().getBookingdates().getCheckin(),
                expected.getBookingdates().getCheckin()
        );

        Assert.assertEquals(
                actual.getBooking().getBookingdates().getCheckout(),
                expected.getBookingdates().getCheckout()
        );

        Assert.assertEquals(
                actual.getBooking().getAdditionalneeds(),
                expected.getAdditionalneeds()
        );

    }

}
