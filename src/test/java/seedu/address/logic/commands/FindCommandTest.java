package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_CATS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.FindCommand.MESSAGE_NO_MATCH;
import static seedu.address.testutil.TypicalCats.MOCHI;
import static seedu.address.testutil.TypicalCats.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cat.CatContainsKeywordsPredicate;

public class FindCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        // Predicate 1: Searches for name "first"
        CatContainsKeywordsPredicate firstPredicate =
                new CatContainsKeywordsPredicate(Collections.singletonList("first"),
                        Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

        // Predicate 2: Searches for name "second"
        CatContainsKeywordsPredicate secondPredicate =
                new CatContainsKeywordsPredicate(Collections.singletonList("second"),
                        Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicate values -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_noMatchingKeywords() {
        // Expected message for 0 results
        String expectedMessage = String.format(MESSAGE_NO_MATCH, 0);

        // Search for a name that definitely doesn't exist in TypicalCats
        CatContainsKeywordsPredicate predicate = new CatContainsKeywordsPredicate(
                Collections.singletonList("NonExistentCatName"),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredCatList(predicate);

        // assertCommandSuccess verifies the command returns the expected message
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCatList());
    }

    @Test
    public void execute_multipleKeywords_multipleCatsFound() {
        String expectedMessage = String.format(MESSAGE_CATS_LISTED_OVERVIEW, 1);
        // Search for name "Mochi" with traits "White" or "Fluffy"
        CatContainsKeywordsPredicate predicate = new CatContainsKeywordsPredicate(
                Arrays.asList("Mochi"), Collections.emptyList(),
                Arrays.asList("White"), Collections.emptyList());

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredCatList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MOCHI), model.getFilteredCatList());
    }

    /**
     * Parses {@code userInput} into a {@code CatContainsKeywordsPredicate} for name keywords.
     */
    private CatContainsKeywordsPredicate preparePredicate(String userInput) {
        return new CatContainsKeywordsPredicate(
                Arrays.asList(userInput.trim().split("\\s+")),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList()
        );
    }

}
