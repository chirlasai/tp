package seedu.address.model.cat;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Cat's name in the cat notebook.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    // Specific error messages for command validation
    public static final String MESSAGE_BLANK = "Name must not be blank!";
    public static final String MESSAGE_TOO_LONG = "Name must be no longer than 30 chars!";
    public static final String MESSAGE_HAS_SYMBOLS = "The name must not contain symbols!";
    public static final String MESSAGE_NUMBERS_ONLY =
            "Name must contain at least one letter and cannot be only numbers!";
    public static final int MAX_LENGTH = 30;

    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     * A valid name contains only alphanumeric characters and spaces,
     * must not be blank, and must not consist of only digits.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX) && !isNumbersOnly(test);
    }

    /**
     * Returns true if the given string consists entirely of digits.
     * Names that are only numbers are rejected because they are ambiguous
     * with index-based commands such as {@code update} and {@code delete}.
     */
    public static boolean isNumbersOnly(String test) {
        return test.trim().matches("\\d+");
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
