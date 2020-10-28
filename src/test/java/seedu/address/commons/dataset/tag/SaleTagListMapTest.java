package seedu.address.commons.dataset.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSaleTags.IMPORTANT;
import static seedu.address.testutil.meeting.TypicalMeetings.LUNCH_DANIEL;
import static seedu.address.testutil.meeting.TypicalMeetings.LUNCH_ELLE;
import static seedu.address.testutil.meeting.TypicalMeetings.LUNCH_FIONA;
import static seedu.address.testutil.meeting.TypicalMeetings.MEET_ALICE;
import static seedu.address.testutil.sale.TypicalSales.APPLE;
import static seedu.address.testutil.sale.TypicalSales.BALL;
import static seedu.address.testutil.sale.TypicalSales.CAMERA;
import static seedu.address.testutil.sale.TypicalSales.DRUMS;
import static seedu.address.testutil.sale.TypicalSales.GUITAR;

import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.dataset.DataSet;
import seedu.address.commons.dataset.date.MonthAndYear;
import seedu.address.commons.dataset.date.MonthlyCountData;
import seedu.address.commons.dataset.date.MonthlyListMap;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.sale.Sale;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.sale.SaleBuilder;

public class SaleTagListMapTest {

    private SaleTagListMap saleTagListMap;
    private final Sale sale1 = new SaleBuilder(APPLE).withTags("fruit").build();
    private final Sale sale2 = new SaleBuilder(BALL).withTags("fruit").build();

    @BeforeEach
    public void init() {
        this.saleTagListMap = new SaleTagListMap();
        this.saleTagListMap.addSale(sale1);
        this.saleTagListMap.addSale(sale2);
    }

    @Test
    public void addSale_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saleTagListMap.addSale(null));
    }

    @Test
    public void addSale_validInputs_success() {
        Sale sale = new SaleBuilder(CAMERA).withTags("fruit").build();
        this.saleTagListMap.addSale(sale);
        assertEquals(3, this.saleTagListMap.getSaleCount(new Tag("fruit")));
    }

    @Test
    public void removeSale_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saleTagListMap.removeSale(null));
    }

    @Test
    public void removeSale_validInputs_success() {
        this.saleTagListMap.removeSale(sale1);
        assertEquals(1, this.saleTagListMap.getSaleCount(new Tag("fruit")));
        this.saleTagListMap.removeSale(sale2);
        assertEquals(0, this.saleTagListMap.getSaleCount(new Tag("fruit")));
    }

    @Test
    public void removeTag_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saleTagListMap.removeTag(null));
    }

    @Test
    public void removeTag_validInputs_success() {
        Tag tag = new Tag("fruit");
        this.saleTagListMap.removeTag(tag);
        assertEquals(0, this.saleTagListMap.getSaleCount(tag));
    }

    @Test
    public void editTag_validInputs_success() {
        Tag tag = new Tag("fruit");
        Tag newTag = new Tag("stationery");
        this.saleTagListMap.editTag(tag, newTag);
        assertEquals(0, this.saleTagListMap.getSaleCount(tag));
        assertEquals(2, this.saleTagListMap.getSaleCount(newTag));
    }

    @Test
    public void editTag_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saleTagListMap.editTag(IMPORTANT, null));
        assertThrows(NullPointerException.class, () -> saleTagListMap.editTag(null, IMPORTANT));
        assertThrows(NullPointerException.class, () -> saleTagListMap.editTag(null, null));
    }

    @Test
    public void clear_noInput_success() {
        this.saleTagListMap.clear();
        assertEquals(0, this.saleTagListMap.getSaleCount(new Tag("fruit")));
    }

    @Test
    public void getSaleTagCount_valid_success() {
        Sale sale1 = new SaleBuilder(APPLE).withTags("stationery").build();
        Sale sale2 = new SaleBuilder(BALL).withTags("stationery").build();
        Sale sale3 = new SaleBuilder(CAMERA).withTags("stationery").build();
        Sale sale4 = new SaleBuilder(APPLE).withTags("music").build();

        this.saleTagListMap.addSale(sale1);
        this.saleTagListMap.addSale(sale2);
        this.saleTagListMap.addSale(sale3);
        this.saleTagListMap.addSale(sale4);

        DataSet<SaleTagCountData> actual = this.saleTagListMap.getSaleTagCount();
        DataSet<SaleTagCountData> expected = new DataSet<>(Arrays.asList(
                new SaleTagCountData(new TagKey(new Tag("stationery")), 3),
                new SaleTagCountData(new TagKey(new Tag("fruit")), 2),
                new SaleTagCountData(new TagKey(new Tag("music")), 1)));
        assertEquals(expected, actual);
    }
}
