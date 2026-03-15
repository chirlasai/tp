package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.cat.Cat;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_CAT = "Cats list contains duplicate cat(s).";

    private final List<JsonAdaptedCat> cats = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given cats.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("cats") List<JsonAdaptedCat> cats) {
        this.cats.addAll(cats);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        cats.addAll(source.getCatList().stream().map(JsonAdaptedCat::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedCat jsonAdaptedCat : cats) {
            Cat cat = jsonAdaptedCat.toModelType();
            if (addressBook.hasCat(cat)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CAT);
            }
            addressBook.addCat(cat);
        }
        return addressBook;
    }

}
