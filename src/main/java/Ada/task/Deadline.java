package Ada.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDateTime by;
    DateTimeFormatter DATETIMEFORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toDataString() {
        return "D | " + super.toDataString() + " | " + by.format(DATETIMEFORMAT);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}
