package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.cat.Cat;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_CAT_DISPLAYED_INDEX = "The cat index provided is invalid";
    public static final String MESSAGE_CATS_LISTED_OVERVIEW = "%1$d cats listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code cat} for display to the user.
     */
    public static String format(Cat cat) {
        final StringBuilder builder = new StringBuilder();
        builder.append(cat.getName())
                .append("; Phone: ")
                .append(cat.getPhone())
                .append("; Email: ")
                .append(cat.getEmail())
                .append("; Address: ")
                .append(cat.getAddress())
                .append("; Tags: ");
        cat.getTags().forEach(builder::append);
        return builder.toString();
    }

}
