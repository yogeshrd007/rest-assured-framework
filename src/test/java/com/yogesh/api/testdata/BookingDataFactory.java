package com.yogesh.api.testdata;

import com.yogesh.api.builders.BookingRequestBuilder;
import com.yogesh.api.models.BookingDateRange;
import com.yogesh.api.models.BookingRequest;
import io.qameta.allure.Step;

public final  class BookingDataFactory {

    private BookingDataFactory() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    @Step("Create a valid booking test data")
    public static BookingRequest validBooking() {
        return BookingRequestBuilder.builder()
                 .withFirstname("Jim")
                .withLastname("Brown")
                .withTotalPrice(111)
                .withDepositPaid(true)
                .withCheckin("2025-07-01")
                .withCheckout("2025-07-10")
                .withAdditionalNeeds("Breakfast")
                .build();
    }

    public static BookingRequest randomBooking() {

        BookingDateRange bookingDateRange =
                RandomDataGenerator.randomBookingDates();

        return BookingRequestBuilder.builder()
                .withFirstname(RandomDataGenerator.randomFirstName())
                .withLastname(RandomDataGenerator.randomLastName())
                .withTotalPrice(RandomDataGenerator.randomPrice())
                .withDepositPaid(true)
                .withCheckin(bookingDateRange.getCheckin())
                .withCheckout(bookingDateRange.getCheckout())
                .withAdditionalNeeds(RandomDataGenerator.randomAdditionalNeeds())
                .build();

    }

    public static BookingRequest bookingWithNullFirstname() {

         BookingRequest request = validBooking();

         request.setFirstname(null);

         return request;
    }

    public static BookingRequest bookingWithNullLastname() {

        BookingRequest request = validBooking();

        request.setLastname(null);

        return request;
    }

    public static BookingRequest bookingWithNegativePrice() {

        BookingRequest request = validBooking();

        request.setTotalprice(-1);

        return request;
    }

    public static BookingRequest bookingWithZeroPrice() {

        BookingRequest request = validBooking();

        request.setTotalprice(0);

        return request;
    }

    public static BookingRequest bookingWithLargePrice() {

        BookingRequest request = validBooking();

        request.setTotalprice(Integer.MAX_VALUE);

        return request;
    }

}
