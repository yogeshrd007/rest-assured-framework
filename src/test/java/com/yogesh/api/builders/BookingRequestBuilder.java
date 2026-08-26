package com.yogesh.api.builders;

import com.yogesh.api.models.BookingDates;
import com.yogesh.api.models.BookingRequest;
import io.qameta.allure.Step;

public final class BookingRequestBuilder {

    private final BookingRequest bookingRequest;

    private BookingRequestBuilder(){
        bookingRequest = new BookingRequest();

        BookingDates bookingDates = new BookingDates();
        bookingRequest.setBookingdates(bookingDates);
    }


    public static BookingRequestBuilder builder(){
        return new BookingRequestBuilder();
    }

    public BookingRequestBuilder withFirstname(String firstname){
        bookingRequest.setFirstname(firstname);
        return this;
    }


    public BookingRequestBuilder withLastname(String lastname){
        bookingRequest.setLastname(lastname);
        return this;
    }

    public BookingRequestBuilder withTotalPrice (int totalPrice){
        bookingRequest.setTotalprice(totalPrice);
        return this;
    }

    public BookingRequestBuilder withDepositPaid(boolean depositPaid){
        bookingRequest.setDepositpaid(depositPaid);
        return this;
    }

    public BookingRequestBuilder withCheckin(String checkin){
        bookingRequest.getBookingdates().setCheckin(checkin);
        return this;
    }

    public BookingRequestBuilder withCheckout(String checkout){
        bookingRequest.getBookingdates().setCheckout(checkout);
        return this;
    }

    public BookingRequestBuilder withAdditionalNeeds(String additionalNeeds) {
        bookingRequest.setAdditionalneeds(additionalNeeds);
        return this;
    }

    @Step("Build a booking request")
    public BookingRequest build(){
        return bookingRequest;
    }
}
