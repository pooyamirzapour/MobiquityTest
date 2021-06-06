package com.mobiquity.unit;

import com.mobiquity.model.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ItemUnitTest {

    @Test
    void testBuildItem() {
        Item.ItemBuilder builder = Item.builder().index(1).cost(20).weight(BigDecimal.valueOf(80));
        Item result = builder.build();
        assertNotNull(result);
    }

    @Test()
    public void testMaxItemWeight() {
            Item.ItemBuilder builder = Item.builder().index(1).cost(30).weight(BigDecimal.valueOf(101));
        assertThrows(IllegalArgumentException.class, () -> builder.build());

    }
}
