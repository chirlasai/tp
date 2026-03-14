package seedu.address.model.cat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCats.ALICE;
import static seedu.address.testutil.TypicalCats.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CatBuilder;

public class CatTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Cat cat = new CatBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> cat.getTags().remove(0));
    }

    @Test
    public void isSameCat() {
        // same object -> returns true
        assertTrue(ALICE.isSameCat(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameCat(null));

        // same name, all other attributes different -> returns true
        Cat editedAlice = new CatBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCat(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new CatBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameCat(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Cat editedBob = new CatBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameCat(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new CatBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameCat(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Cat aliceCopy = new CatBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different cat -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Cat editedAlice = new CatBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new CatBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new CatBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new CatBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new CatBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Cat.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
