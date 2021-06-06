package com.mobiquity.unit;

import com.mobiquity.model.Item;
import com.mobiquity.model.Line;
import com.mobiquity.model.Result;
import com.mobiquity.service.find.FindServiceImpl;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindServiceUnitTest {

    //For calling private method
    private Object getInvoke(Line input, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = FindServiceImpl.class.getDeclaredMethod(methodName, Line.class);
        method.setAccessible(true);
        return method.invoke(FindServiceImpl.INSTANCE, input);
    }

    @Test
    void testFindServiceValidInput() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Item item1 = Item.builder().index(1).weight(BigDecimal.valueOf(21)).cost(34).build();
        Item item2 = Item.builder().index(2).weight(BigDecimal.valueOf(82)).cost(82).build();
        Line line = Line.builder().capacity(BigDecimal.valueOf(81)).items(Arrays.asList(item1, item2)).build();

        Result result = (Result) getInvoke(line, "process");
        List<Item> resultItems = result.getItems();

        assertEquals(1, resultItems.size());
        assertEquals(item1, resultItems.get(0));
    }

    @Test
    void testFindServiceEqualItemsCost() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Item item1 = Item.builder().index(1).weight(BigDecimal.valueOf(55.55)).cost(40).build();
        Item item2 = Item.builder().index(2).weight(BigDecimal.valueOf(66.66)).cost(40).build();
        Line line = Line.builder().capacity(BigDecimal.valueOf(81)).items(Arrays.asList(item1, item2)).build();

        Result result = (Result) getInvoke(line, "process");
        List<Item> resultItems = result.getItems();

        assertEquals(1, resultItems.size());
        assertEquals(item1, resultItems.get(0));
    }

    @Test
    void testFindServiceEqualItemsWeight() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Item item1 = Item.builder().index(1).weight(BigDecimal.valueOf(20.12)).cost(41).build();
        Item item2 = Item.builder().index(2).weight(BigDecimal.valueOf(20.12)).cost(40).build();
        Line line = Line.builder().capacity(BigDecimal.valueOf(81)).items(Arrays.asList(item1, item2)).build();

        Result result = (Result) getInvoke(line, "process");
        List<Item> resultItems = result.getItems();

        assertEquals(2, resultItems.size());
        assertEquals(item1, resultItems.get(0));
        assertEquals(item2, resultItems.get(1));
    }

    @Test
    void testFindServiceNotFound() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Item item1 = Item.builder().index(1).weight(BigDecimal.valueOf(81.12)).cost(20).build();
        Item item2 = Item.builder().index(2).weight(BigDecimal.valueOf(82.12)).cost(30).build();
        Line line = Line.builder().capacity(BigDecimal.valueOf(81)).items(Arrays.asList(item1, item2)).build();
        Result result = (Result) getInvoke(line, "process");
        List<Item> resultItems = result.getItems();

        assertEquals(0, resultItems.size());
    }


}
