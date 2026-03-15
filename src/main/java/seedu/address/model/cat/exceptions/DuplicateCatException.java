package seedu.address.model.cat.exceptions;

/**
 * Signals that the operation will result in duplicate Cats (Cats are considered duplicates if they have the same
 * identity).
 */
public class DuplicateCatException extends RuntimeException {
    public DuplicateCatException() {
        super("Operation would result in duplicate cats");
    }
}
