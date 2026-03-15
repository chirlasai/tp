package seedu.address.model.cat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCats.ALICE;
import static seedu.address.testutil.TypicalCats.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.cat.exceptions.CatNotFoundException;
import seedu.address.model.cat.exceptions.DuplicateCatException;
import seedu.address.testutil.CatBuilder;

public class UniqueCatListTest {

    private final UniqueCatList uniqueCatList = new UniqueCatList();

    @Test
    public void contains_nullCat_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCatList.contains(null));
    }

    @Test
    public void contains_catNotInList_returnsFalse() {
        assertFalse(uniqueCatList.contains(ALICE));
    }

    @Test
    public void contains_catInList_returnsTrue() {
        uniqueCatList.add(ALICE);
        assertTrue(uniqueCatList.contains(ALICE));
    }

    @Test
    public void contains_catWithSameIdentityFieldsInList_returnsTrue() {
        uniqueCatList.add(ALICE);
        Cat editedAlice = new CatBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueCatList.contains(editedAlice));
    }

    @Test
    public void add_nullCat_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCatList.add(null));
    }

    @Test
    public void add_duplicateCat_throwsDuplicateCatException() {
        uniqueCatList.add(ALICE);
        assertThrows(DuplicateCatException.class, () -> uniqueCatList.add(ALICE));
    }

    @Test
    public void setCat_nullTargetCat_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCatList.setCat(null, ALICE));
    }

    @Test
    public void setCat_nullEditedCat_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCatList.setCat(ALICE, null));
    }

    @Test
    public void setCat_targetCatNotInList_throwsCatNotFoundException() {
        assertThrows(CatNotFoundException.class, () -> uniqueCatList.setCat(ALICE, ALICE));
    }

    @Test
    public void setCat_editedCatIsSameCat_success() {
        uniqueCatList.add(ALICE);
        uniqueCatList.setCat(ALICE, ALICE);
        UniqueCatList expectedUniqueCatList = new UniqueCatList();
        expectedUniqueCatList.add(ALICE);
        assertEquals(expectedUniqueCatList, uniqueCatList);
    }

    @Test
    public void setCat_editedCatHasSameIdentity_success() {
        uniqueCatList.add(ALICE);
        Cat editedAlice = new CatBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueCatList.setCat(ALICE, editedAlice);
        UniqueCatList expectedUniqueCatList = new UniqueCatList();
        expectedUniqueCatList.add(editedAlice);
        assertEquals(expectedUniqueCatList, uniqueCatList);
    }

    @Test
    public void setCat_editedCatHasDifferentIdentity_success() {
        uniqueCatList.add(ALICE);
        uniqueCatList.setCat(ALICE, BOB);
        UniqueCatList expectedUniqueCatList = new UniqueCatList();
        expectedUniqueCatList.add(BOB);
        assertEquals(expectedUniqueCatList, uniqueCatList);
    }

    @Test
    public void setCat_editedCatHasNonUniqueIdentity_throwsDuplicateCatException() {
        uniqueCatList.add(ALICE);
        uniqueCatList.add(BOB);
        assertThrows(DuplicateCatException.class, () -> uniqueCatList.setCat(ALICE, BOB));
    }

    @Test
    public void remove_nullCat_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCatList.remove(null));
    }

    @Test
    public void remove_catDoesNotExist_throwsCatNotFoundException() {
        assertThrows(CatNotFoundException.class, () -> uniqueCatList.remove(ALICE));
    }

    @Test
    public void remove_existingCat_removesCat() {
        uniqueCatList.add(ALICE);
        uniqueCatList.remove(ALICE);
        UniqueCatList expectedUniqueCatList = new UniqueCatList();
        assertEquals(expectedUniqueCatList, uniqueCatList);
    }

    @Test
    public void setCats_nullUniqueCatList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCatList.setCats((UniqueCatList) null));
    }

    @Test
    public void setCats_uniqueCatList_replacesOwnListWithProvidedUniqueCatList() {
        uniqueCatList.add(ALICE);
        UniqueCatList expectedUniqueCatList = new UniqueCatList();
        expectedUniqueCatList.add(BOB);
        uniqueCatList.setCats(expectedUniqueCatList);
        assertEquals(expectedUniqueCatList, uniqueCatList);
    }

    @Test
    public void setCats_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCatList.setCats((List<Cat>) null));
    }

    @Test
    public void setCats_list_replacesOwnListWithProvidedList() {
        uniqueCatList.add(ALICE);
        List<Cat> catList = Collections.singletonList(BOB);
        uniqueCatList.setCats(catList);
        UniqueCatList expectedUniqueCatList = new UniqueCatList();
        expectedUniqueCatList.add(BOB);
        assertEquals(expectedUniqueCatList, uniqueCatList);
    }

    @Test
    public void setCats_listWithDuplicateCats_throwsDuplicateCatException() {
        List<Cat> listWithDuplicateCats = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateCatException.class, () -> uniqueCatList.setCats(listWithDuplicateCats));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueCatList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueCatList.asUnmodifiableObservableList().toString(), uniqueCatList.toString());
    }
}
