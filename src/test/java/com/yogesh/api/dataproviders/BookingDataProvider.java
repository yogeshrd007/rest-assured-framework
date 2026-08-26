package com.yogesh.api.dataproviders;

import com.yogesh.api.testdata.BookingDataFactory;
import org.testng.annotations.DataProvider;

public final class BookingDataProvider {

    private BookingDataProvider() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    @DataProvider(name = "validBookings")
    public static Object[][] validBookings() {

        return new Object[][]{
                {BookingDataFactory.validBooking()},
                {BookingDataFactory.randomBooking()}
        };
    }

    @DataProvider(name = "negativeBookings")
    public static Object[][] negativeBookings() {

        return new Object[][]{
                {
                        BookingDataFactory.bookingWithNullFirstname(),
                        "Null Firstname",
                        500
                },
                {
                    BookingDataFactory.bookingWithNullLastname(),
                        "Null Lastname",
                        500
                },
               /* {
                  BookingDataFactory.bookingWithNegativePrice(),
                  "Negative price",
                        200
                },
                {
                    BookingDataFactory.bookingWithZeroPrice(),
                        "Zero price",
                        200
                },
                {
                    BookingDataFactory.bookingWithLargePrice(),
                        "Large price",
                        200
                }*/

        };
    }
}