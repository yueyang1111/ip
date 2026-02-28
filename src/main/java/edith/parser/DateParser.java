package edith.parser;

import edith.exception.EdithException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represent a class for parsing and formatting date and time value.
 * <p>
 * Supports multiple input formats for date and date time strings.
 * Converts valid inpout into a standard format.
 */
public class DateParser {
    private static final String[] DATETIME_PATTERN = {
            "d/M/yyyy HHmm", "d-M-yyyy HHmm", "yyyy-M-d HHmm"
    };
    private static final String[] DATE_PATTERN = {
            "d/M/yyyy", "d-M-yyyy", "yyyy-M-d"
    };
    private static final String TIME_PATTERN = "HHmm";

    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");

    /**
     * Parse the given input string into a standard format.
     *
     * @param input The date/time string provide by the user.
     * @return The formatted date/time.
     * @throws EdithException If the input does not match the supported format.
     */
    public static LocalDateTime parseDate(String input) throws EdithException {
        if (input == null) {
            throw new EdithException("OOPS! Date cannot be empty");
        }

        String date = input.trim();
        if (date.isEmpty()) {
            throw new EdithException("OOPS! Date cannot be empty");
        }

        for (String dateTimePattern : DATETIME_PATTERN) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
                return LocalDateTime.parse(date, formatter);
            } catch (DateTimeParseException e) {
                // ignored, try date format next
            }
        }

        for (String datePattern : DATE_PATTERN) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
                return LocalDate.parse(date, formatter).atStartOfDay();
            } catch (DateTimeParseException e) {
                // ignored, try time format next
            }
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
            LocalTime time = LocalTime.parse(date, formatter);
            return LocalDate.now().atTime(time);
        } catch (DateTimeParseException e) {
            throw new EdithException("OOPS! Invalid date format!");
        }
    }

    /**
     * Formats the date/time into a user-friendly string.
     *
     * @param date The date/time to be formatted.
     * @return Formatted date string.
     */
    public static String formatDate(LocalDateTime date) {
        return date.format(OUTPUT_FORMAT);
    }
}
