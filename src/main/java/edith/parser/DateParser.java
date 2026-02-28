package edith.parser;

import edith.exception.EdithException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

    public static String formatDate(LocalDateTime date) {
        return date.format(OUTPUT_FORMAT);
    }
}
