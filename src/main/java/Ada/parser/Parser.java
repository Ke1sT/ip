package Ada.parser;

import Ada.command.Command;
import Ada.AdaException;
import Ada.command.CommandType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {

    public static Command parse(String input) throws AdaException {
        try {
            CommandType type = CommandType.valueOf(input.split(" ")[0].toUpperCase());
            String argumentString = input.substring(type.toString().length()).trim();

            switch (type) {
            case BYE:
                return new Command(type, argumentString.split(" "));
            case LIST:
                if (argumentString.isEmpty()) {
                    return new Command(type, new String[]{});
                }
                break;
            case MARK:
                if (argumentString.split(" ").length == 1) {
                    return new Command(type, argumentString.split(" "));
                } else {
                    throw new AdaException("MARK command takes exactly one argument.");
                }
            case UNMARK:
                if (argumentString.split(" ").length == 1) {
                    return new Command(type, argumentString.split(" "));
                } else {
                    throw new AdaException("UNMARK command takes exactly one argument.");
                }
            case DELETE:
                if (argumentString.split(" ").length == 1) {
                    return new Command(type, argumentString.split(" "));
                }
                throw new AdaException("DELETE command takes exactly one argument.");
            case FIND: //allow multiple keywords to be searched
                return new Command(type, argumentString.split(" "));
            case TODO:
                if (argumentString.split(" ").length == 1) {
                    return new Command(type, argumentString.split(" "));
                }
                throw new AdaException("TODO command takes exactly one argument.");
            case DEADLINE: {
                if (argumentString.matches(".* /by .*")) {
                    String[] parts = argumentString.split(" /by ");
                    if (parts.length == 2) {
                        return new Command(type, parts);
                    }
                }
                throw new AdaException("Invalid deadline format. Use: deadline <description> /by <time>");
            }

            case EVENT: {
                if (argumentString.matches(".* /from .* /to .*")) {
                    String[] parts = argumentString.split(" /from | /to ");
                    return new Command(type, parts);
                }
                throw new AdaException("Invalid event format. Use: event <description> /from <start time> /to <end time>");
            }
            default:
                throw new AdaException("Unknown command type.");

            }
        } catch (IllegalArgumentException e) {
            throw new AdaException("Unknown command type");
        } catch (AdaException e) {
            throw e;
        }

        throw new AdaException("Unknown command type.");
    }

    public static LocalDateTime parseDateTime(String dateTimeStr) {
        String[] patterns = {
                "yyyy-MM-dd HH:mm",
                "dd/MM/yyyy HH:mm",
                "dd-MM-yyyy HH:mm",
                "yyyy-MM-dd"
        };
        LocalDateTime dateTime;

        for (String pattern : patterns) {
            DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(pattern);
            try {
                if (pattern.equals("yyyy-MM-dd")) {
                    LocalDate date = LocalDate.parse(dateTimeStr, dateTimeFormat);
                    dateTime = date.atStartOfDay();
                    return dateTime;
                }
                dateTime = LocalDateTime.parse(dateTimeStr, dateTimeFormat);
                return dateTime;
            } catch (DateTimeParseException e) {
                // Try next pattern
            }
        }
        throw new DateTimeParseException("Invalid date/time format: " + dateTimeStr, dateTimeStr, 0);
    }

}