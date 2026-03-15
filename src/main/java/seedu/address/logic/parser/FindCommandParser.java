package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cat.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object.
 */
public class FindCommandParser implements Parser<FindCommand> {

    public static final String MESSAGE_MISSING_NAME =
            "Name is missing for this find command.";
    public static final String MESSAGE_INVALID_SYMBOLS =
            "The name must not contain symbols";

    // letters + spaces only
    private static final String VALID_FIND_INPUT_REGEX = "[A-Za-z ]+";

    @Override
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(MESSAGE_MISSING_NAME);
        }

        if (!trimmedArgs.matches(VALID_FIND_INPUT_REGEX)) {
            throw new ParseException(MESSAGE_INVALID_SYMBOLS);
        }

        List<String> keywords = Arrays.asList(trimmedArgs.split("\\s+"));
        return new FindCommand(new NameContainsKeywordsPredicate(keywords));
    }

}
