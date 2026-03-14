package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CATS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cat.Address;
import seedu.address.model.cat.Cat;
import seedu.address.model.cat.Email;
import seedu.address.model.cat.Name;
import seedu.address.model.cat.Phone;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing cat in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the cat identified "
            + "by the index number used in the displayed cat list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_CAT_SUCCESS = "Edited Cat: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CAT = "This cat already exists in the address book.";

    private final Index index;
    private final EditCatDescriptor editCatDescriptor;

    /**
     * @param index of the cat in the filtered cat list to edit
     * @param editCatDescriptor details to edit the cat with
     */
    public EditCommand(Index index, EditCatDescriptor editCatDescriptor) {
        requireNonNull(index);
        requireNonNull(editCatDescriptor);

        this.index = index;
        this.editCatDescriptor = new EditCatDescriptor(editCatDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Cat> lastShownList = model.getFilteredCatList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CAT_DISPLAYED_INDEX);
        }

        Cat catToEdit = lastShownList.get(index.getZeroBased());
        Cat editedCat = createEditedCat(catToEdit, editCatDescriptor);

        if (!catToEdit.isSameCat(editedCat) && model.hasCat(editedCat)) {
            throw new CommandException(MESSAGE_DUPLICATE_CAT);
        }

        model.setCat(catToEdit, editedCat);
        model.updateFilteredCatList(PREDICATE_SHOW_ALL_CATS);
        return new CommandResult(String.format(MESSAGE_EDIT_CAT_SUCCESS, Messages.format(editedCat)));
    }

    /**
     * Creates and returns a {@code Cat} with the details of {@code catToEdit}
     * edited with {@code editCatDescriptor}.
     */
    private static Cat createEditedCat(Cat catToEdit, EditCatDescriptor editCatDescriptor) {
        assert catToEdit != null;

        Name updatedName = editCatDescriptor.getName().orElse(catToEdit.getName());
        Phone updatedPhone = editCatDescriptor.getPhone().orElse(catToEdit.getPhone());
        Email updatedEmail = editCatDescriptor.getEmail().orElse(catToEdit.getEmail());
        Address updatedAddress = editCatDescriptor.getAddress().orElse(catToEdit.getAddress());
        Set<Tag> updatedTags = editCatDescriptor.getTags().orElse(catToEdit.getTags());

        return new Cat(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editCatDescriptor.equals(otherEditCommand.editCatDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editCatDescriptor", editCatDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the cat with. Each non-empty field value will replace the
     * corresponding field value of the cat.
     */
    public static class EditCatDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditCatDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCatDescriptor(EditCatDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCatDescriptor)) {
                return false;
            }

            EditCatDescriptor otherEditCatDescriptor = (EditCatDescriptor) other;
            return Objects.equals(name, otherEditCatDescriptor.name)
                    && Objects.equals(phone, otherEditCatDescriptor.phone)
                    && Objects.equals(email, otherEditCatDescriptor.email)
                    && Objects.equals(address, otherEditCatDescriptor.address)
                    && Objects.equals(tags, otherEditCatDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .toString();
        }
    }
}
