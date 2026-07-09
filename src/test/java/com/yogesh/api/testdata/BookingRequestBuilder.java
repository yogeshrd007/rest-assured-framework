package com.yogesh.api.testdata;

import com.yogesh.api.models.BookingDates;
import com.yogesh.api.models.BookingRequest;

public class BookingRequestBuilder {

    private final BookingRequest bookingRequest;

    private BookingRequestBuilder(){
        bookingRequest = new BookingRequest();

        BookingDates bookingDates = new BookingDates();
        bookingRequest.setBookingdates(bookingDates);
    }

    public static BookingRequestBuilder validBooking(){
        return new BookingRequestBuilder()
                .withFirstname("Jim")
                .withLastname("Brown")
                .withTotalPrice(111)
                .withDepositPaid(true)
                .withCheckin("2025-07-01")
                .withCheckout("2025-07-10")
                .withAdditionalNeeds("Breakfast");
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

    public BookingRequest build(){
        return bookingRequest;
    }
}
