package Ada.parser;

import Ada.AdaException;
import Ada.command.Command;
import Ada.command.CommandType;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assumptions;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    @Test
    void parse_event_invalid_format() {
        try {
            Parser.parse("event meeting /from 09:00 to 10:00");

        } catch (AdaException e) {
            //expected exception due to invalid date format
        }
    }

    @Test
    void parse_unknown_command() {
        try {
            Parser.parse("test 123");

        } catch (AdaException e) {
            //expected exception due to unknown command
        }
    }

    @Test
    void parseDateTime_supported_formats() {
        LocalDateTime dt1 = Parser.parseDateTime("2025-01-31 14:30");
        assertEquals(LocalDateTime.of(2025, 1, 31, 14, 30), dt1);

        LocalDateTime dt2 = Parser.parseDateTime("31/01/2025 14:30");
        assertEquals(LocalDateTime.of(2025, 1, 31, 14, 30), dt2);

        LocalDateTime dt3 = Parser.parseDateTime("31-01-2025 14:30");
        assertEquals(LocalDateTime.of(2025, 1, 31, 14, 30), dt3);

        // Date-only should be start of day
        LocalDateTime dt4 = Parser.parseDateTime("2025-01-31");
        assertEquals(LocalDateTime.of(2025, 1, 31, 0, 0), dt4);
    }

}
