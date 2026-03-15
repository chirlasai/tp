package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.cat.Cat;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the cats list.
     * This list will not contain any duplicate cats.
     */
    ObservableList<Cat> getCatList();

}
