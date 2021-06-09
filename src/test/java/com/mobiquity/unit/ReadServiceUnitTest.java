package com.mobiquity.unit;

import com.mobiquity.exception.APIException;
import com.mobiquity.exception.LineFormatException;
import com.mobiquity.model.Item;
import com.mobiquity.model.Line;
import com.mobiquity.service.read.ReadServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


// With the help of reflection private methods should be tested
public class ReadServiceUnitTest {




    //For calling private method
    private Object getInvoke(String input,String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = ReadServiceImpl.class.getDeclaredMethod(methodName, String.class);
        method.setAccessible(true);
        return method.invoke(ReadServiceImpl.INSTANCE, input);
    }
    private static MockedStatic<Files> mockedSettings;


    @BeforeEach
    public void before() {
        mockedSettings= Mockito.mockStatic(Files.class);
    }

    @AfterEach
    public void after() {
        mockedSettings.close();
    }

    @Test
    void testMakeItemValidPattern() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String input = "1,53.38,€45";
        Object invoke = getInvoke(input,"makeItem");
        Item build = Item.builder().index(1).cost(45).weight(BigDecimal.valueOf(53.38)).build();
        assertEquals(build, invoke);
    }



    @Test
    void testMakeItemEmpty() throws NoSuchMethodException {
        String input = "";
        try {
            getInvoke(input, "makeItem");
        }
        //unwrap the InvocationTargetException
        catch (InvocationTargetException | IllegalAccessException e) {
            Throwable throwable = e.getCause();
            if (throwable instanceof IllegalArgumentException)
                assertThrows(IllegalArgumentException.class, () -> {
                    throw throwable;
                });
        }
    }

    @Test
    void testMakeItemInvalidFormat() throws NoSuchMethodException {
        String input = "1,53.38,A";
        try {
            getInvoke(input, "makeItem");
        }
        //unwrap the InvocationTargetException
        catch (InvocationTargetException | IllegalAccessException e) {
            Throwable throwable = e.getCause();
            if (throwable instanceof NumberFormatException)
                assertThrows(IllegalArgumentException.class, () -> {
                    throw throwable;
                });
        }


    }

    @Test
    void testMakeItemsValidFormat() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String input = " (1,53.38,€45) (2,88.62,€98)";
        Object invoke = getInvoke(input, "makeItems");
        Item item1 = Item.builder().index(1).cost(45).weight(BigDecimal.valueOf(53.38)).build();
        Item item2 = Item.builder().index(2).cost(98).weight(BigDecimal.valueOf(88.62)).build();
        assertEquals(Arrays.asList(item1,item2), invoke);
    }

    @Test
    void testMakeItemsEmpty() throws NoSuchMethodException {
        try {
             getInvoke("", "makeItems");
        }
        //unwrap the InvocationTargetException
        catch (InvocationTargetException | IllegalAccessException e) {
            Throwable throwable = e.getCause();
            if (throwable instanceof IllegalArgumentException)
                assertThrows(IllegalArgumentException.class, () -> {
                    throw throwable;
                });
        }
    }


    @Test
    void testValidateLineValidFormat() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String input = "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
        Object invoke = getInvoke(input ,"validateLine");
        assertNull(invoke);
    }

    @Test
    void testValidateLineInvalidFormat() throws NoSuchMethodException {
        String input = "(1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
        try {
            getInvoke(input ,"validateLine");
        }
        //unwrap the InvocationTargetException
        catch (InvocationTargetException | IllegalAccessException e) {
            Throwable throwable = e.getCause();
            if (throwable instanceof LineFormatException)
                assertThrows(LineFormatException.class, () -> {
                    throw throwable;
                });
        }
    }

    @Test
    void testTransformToLineValidFormat() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String input = "81 : (1,53.38,€45) (2,88.62,€98)";
        Object invoke = getInvoke(input, "transformToLine");
        List<Item> items = new ArrayList<>();
        Item item1 = Item.builder().index(1).cost(45).weight(BigDecimal.valueOf(53.38)).build();
        Item item2 = Item.builder().index(2).cost(98).weight(BigDecimal.valueOf(88.62)).build();
        items.add(item1);
        items.add(item2);
        Line line = Line.builder().capacity(new BigDecimal(81)).items(items).build();
        assertEquals(invoke, line);
    }

    @Test
    void testTransformToLineInvalidFormat() throws NoSuchMethodException {
        String input = " : (1,53.38,€45) (2,88.62,€98)";
        try {
            getInvoke(input, "transformToLine");
        } catch (InvocationTargetException | IllegalAccessException e) {
            Throwable throwable = e.getCause();
            if (throwable instanceof LineFormatException)
                assertThrows(LineFormatException.class, () -> {
                    throw throwable;
                });
        }
    }


    @Test
    void testReadValidFormat() throws APIException, IOException {
        String line = "58 : (1,53.38,€45) (2,88.62,€98)";
        Mockito.when(Files.lines(any(), any())).thenReturn(Stream.of(line));
        Stream<Line> lineStream = ReadServiceImpl.INSTANCE.read("path");
        List<Line> collect = lineStream.collect(Collectors.toList());
        Line line1 = collect.get(0);
        assertEquals(1, collect.size());
        assertEquals(new BigDecimal(58), line1.getCapacity());
        assertEquals(2, line1.getItems().size());


    }

    @Test
    void testReadInvalidFormat() throws IOException {
        String line = "fddfhdfh";
        Mockito.when(Files.lines(any(),any())).thenReturn(Stream.of(line));
        assertThrows(LineFormatException.class, () -> ReadServiceImpl.INSTANCE.read("path").collect(Collectors.toList()));
    }



}
