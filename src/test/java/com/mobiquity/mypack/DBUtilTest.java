package com.mobiquity.mypack;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DBUtilTest {

    DBUtil dbUtil = new DBUtil();

    @Test
    void insert_happy_Test() {
        assertTrue(dbUtil.insert(new Client("Pooya")));
    }


}