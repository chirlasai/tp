package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditCatDescriptor;
import seedu.address.model.cat.Address;
import seedu.address.model.cat.Cat;
import seedu.address.model.cat.Email;
import seedu.address.model.cat.Name;
import seedu.address.model.cat.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditCatDescriptor objects.
 */
public class EditCatDescriptorBuilder {

    private EditCatDescriptor descriptor;

    public EditCatDescriptorBuilder() {
        descriptor = new EditCatDescriptor();
    }

    public EditCatDescriptorBuilder(EditCatDescriptor descriptor) {
        this.descriptor = new EditCatDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCatDescriptor} with fields containing {@code cat}'s details
     */
    public EditCatDescriptorBuilder(Cat cat) {
        descriptor = new EditCatDescriptor();
        descriptor.setName(cat.getName());
        descriptor.setPhone(cat.getPhone());
        descriptor.setEmail(cat.getEmail());
        descriptor.setAddress(cat.getAddress());
        descriptor.setTags(cat.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditCatDescriptor} that we are building.
     */
    public EditCatDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditCatDescriptor} that we are building.
     */
    public EditCatDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditCatDescriptor} that we are building.
     */
    public EditCatDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditCatDescriptor} that we are building.
     */
    public EditCatDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditCatDescriptor}
     * that we are building.
     */
    public EditCatDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCatDescriptor build() {
        return descriptor;
    }
}
