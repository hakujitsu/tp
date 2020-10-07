package seedu.address.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class SimilarItemsTest {

    @Test
    public void isInSimilarityMapper_validInput_correctResults() {
        SimilarContacts similarItems = new SimilarContacts(ALICE.getName().fullName);
        similarItems.getSimilarityMapper().put(ALICE, 0.3);

        assertFalse(similarItems.isInSimilarityMapper(null));
        assertFalse(similarItems.isInSimilarityMapper(IDA));
        assertTrue(similarItems.isInSimilarityMapper(ALICE));
    }

    @Test
    public void getFromSimilarityMatrix_validInput_correctResults() {
        SimilarContacts similarItems = new SimilarContacts(ALICE.getName().fullName);
        similarItems.getSimilarityMapper().put(ALICE, 0.3);

        assertEquals(-1, similarItems.getFromSimilarityMatrix(null));
        assertEquals(-1, similarItems.getFromSimilarityMatrix(IDA));
        assertEquals(0.3, similarItems.getFromSimilarityMatrix(ALICE));
    }

    @Test
    public void fillSimilarityMapper_validInput_oneResultFound() {
        SimilarContacts similarItems = new SimilarContacts(ALICE.getName().fullName);

        similarItems.fillSimilarityMapper(getTypicalPersons());
        assertEquals(1, similarItems.getSimilarityMapper().size());
        assertTrue(similarItems.getSimilarityMapper().containsKey(ALICE));
    }

    @Test
    public void fillSimilarityMapper_emptyList_noResultFound() {
        SimilarContacts similarItems = new SimilarContacts(ALICE.getName().fullName);

        similarItems.fillSimilarityMapper(Collections.emptyList());
        assertTrue(similarItems.getSimilarityMapper().isEmpty());
    }

    @Test
    public void fillSimilarityMapper_noMatchingList_noResultFound() {
        SimilarContacts similarItems = new SimilarContacts(ALICE.getName().fullName);
        similarItems.fillSimilarityMapper(Collections.singletonList(BENSON));

        assertTrue(similarItems.getSimilarityMapper().isEmpty());
    }

    @Test
    public void fillSimilarityMapper_validInput_multipleResultsFound() {
        SimilarContacts similarItems = new SimilarContacts(HOON.getName().fullName);
        similarItems.fillSimilarityMapper(getTypicalPersons());

        assertEquals(3, similarItems.getSimilarityMapper().size());
        assertEquals(Set.of(BENSON, DANIEL, ELLE), similarItems.getSimilarityMapper().keySet());
    }
}
