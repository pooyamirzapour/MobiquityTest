package com.mobiquity.unit;

import com.mobiquity.model.Item;
import com.mobiquity.model.Line;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LineUnitTest {

    private List<Item> items;

    @BeforeEach
    void init() {
        items = new ArrayList<>();
        Item item = new Item(1, BigDecimal.TEN, 15);
        items.add(item);
    }

    @Test
    void testBuildLine() {
        Line.LineBuilder builder = Line.builder().capacity(BigDecimal.TEN).items(items);
        Line result = builder.build();
        assertNotNull(result);
    }

    @Test()
    public void testMaxLineWeight() {
        Line.LineBuilder builder = Line.builder().capacity(new BigDecimal(1000)).items(items);
        assertThrows(IllegalArgumentException.class, () -> builder.build());

    }
}
