package com.mobiquity.unit;

import com.mobiquity.model.Item;
import com.mobiquity.model.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResultUnitTest {

    private List<Item> items;
    Item item;

    @BeforeEach
    void init() {
        items = new ArrayList<>();
        item = new Item(1, BigDecimal.TEN, 15);
        items.add(item);
    }

    @Test
    void testBuildResult() {
        Result result = Result.builder().cost(1).weight(new BigDecimal(10))
                .weight(BigDecimal.valueOf(80)).items(items).build();
        assertNotNull(result);
    }

    @Test
    void testCloneResult() {
        Result result = Result.builder().cost(1).weight(new BigDecimal(10))
                .weight(BigDecimal.valueOf(80)).items(items).build();
        Result clone = result.clone();
        assertEquals(result, clone);


    }

    @Test
    void testAddItemResult() {
        Result result = Result.builder().cost(1).weight(new BigDecimal(10))
                .weight(BigDecimal.valueOf(80)).items(items).build();
        result.addItem(item);
        assertEquals(2, result.getItems().size());
    }
}
