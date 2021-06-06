package com.mobiquity.unit;

import com.mobiquity.model.Item;
import com.mobiquity.model.Line;
import com.mobiquity.model.Result;
import com.mobiquity.service.find.FindServiceImpl;
import com.mobiquity.service.read.ReadServiceImpl;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindServiceUnitTest {

    @Test
    void testFindServiceValidInput() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Item item1 = Item.builder().index(1).weight(BigDecimal.valueOf(53.38)).cost(45).build();
        Item item2 = Item.builder().index(2).weight(BigDecimal.valueOf(88.62)).cost(98).build();
        Line line = Line.builder().capacity(BigDecimal.valueOf(81)).items(Arrays.asList(item1, item2)).build();

        Result result =(Result) getInvoke(line,"findAnswer");
        List<Item> resultItems = result.items();

        assertEquals(1, resultItems.size());
        assertEquals(item1, resultItems.get(0));
    }

    //For calling private method
    private Object getInvoke(Line input,String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = FindServiceImpl.class.getDeclaredMethod(methodName, Line.class);
        method.setAccessible(true);
        return method.invoke(FindServiceImpl.INSTANCE, input);
    }
}
