package com.mobiquity.mypack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void add_Test()
    {
        assertEquals(10,Calculator.add(5,5));
    }

}