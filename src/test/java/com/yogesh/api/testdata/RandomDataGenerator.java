package com.yogesh.api.testdata;

import com.yogesh.api.models.BookingDateRange;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomDataGenerator {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ISO_LOCAL_DATE;

    private static final String ALPHABET =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static final int MIN_PRICE = 1;
    private static final int MAX_PRICE = 5000;

    private static final List<String> ADDITIONAL_NEEDS =
            List.of(
                    "Breakfast",
                    "Lunch",
                    "Dinner",
                    "Late checkout",
                    "Airport pickup",
                    "Extra Bed",
                    "None"
            );

    private RandomDataGenerator() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    private static String randomAlphabetic(int length) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {

            int index = ThreadLocalRandom.current()
                    .nextInt(ALPHABET.length());

            sb.append(ALPHABET.charAt(index));

        }
        return sb.toString();
    }

    public static String randomFirstName() {
        return randomAlphabetic(8);
    }

    public static String randomLastName() {
        return randomAlphabetic(10);
    }

    public static String randomAdditionalNeeds() {
        return ADDITIONAL_NEEDS.get(
                ThreadLocalRandom.current()
                        .nextInt(ADDITIONAL_NEEDS.size())
        );
    }

    public static BookingDateRange randomBookingDates() {

        BookingDateRange bookingDateRange = new BookingDateRange();

        LocalDate checkin = LocalDate.now()
                .plusDays(
                        ThreadLocalRandom.current()
                                .nextInt(1, 31)
                );

        LocalDate checkout = checkin
                .plusDays(
                        ThreadLocalRandom.current()
                                .nextInt(1,11)
                );

        bookingDateRange.setCheckin(checkin.format(DATE_FORMATTER));
        bookingDateRange.setCheckout(checkout.format(DATE_FORMATTER));

        return bookingDateRange;
    }

    public static int randomPrice() {
        return ThreadLocalRandom.current()
                .nextInt(MIN_PRICE,MAX_PRICE+1);
    }




}
