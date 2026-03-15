package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.cat.Cat;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withCat("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Cat} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withCat(Cat cat) {
        addressBook.addCat(cat);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
