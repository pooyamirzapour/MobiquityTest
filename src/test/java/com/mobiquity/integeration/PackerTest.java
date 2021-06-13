package com.mobiquity.integeration;

import com.mobiquity.exception.APIException;
import com.mobiquity.exception.LineFormatException;
import com.mobiquity.packer.Packer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PackerTest {

    @Test
    public void testPackerValidPath() throws APIException {
        String actual = Packer.pack("src/test/resources/example_input");
        assertEquals("4\n-\n2, 7\n8, 9", actual);
    }

    @Test
    public void testPackerEmptyLines() throws APIException {
        String pack = Packer.pack("src/test/resources/empty-lines.txt");
        assertEquals("4\n-\n2, 7\n8, 9", pack);
    }

    @Test
    public void testPackerInvalidFileFormat() {
       assertThrows(APIException.class,()->Packer.pack("src/test/resources/bad-format.txt")) ;
    }

    @Test
    public void testPackerInvalidPath() {
        assertThrows(APIException.class,()-> Packer.pack("invalid")) ;
    }

    @Test
    public void testPackerEmptyFile() throws APIException {
        assertEquals("", Packer.pack("src/test/resources/empty-file.txt"));
    }

    @Test
    public void testPackerBadFormat()  {
        assertThrows( APIException.class,()-> Packer.pack("src/test/resources/bad-format.txt"));
    }

    @Test
    public void testPackerBadLineWeight()  {
        assertThrows( APIException.class,()-> Packer.pack("src/test/resources/bad-line-weight.txt"));
    }

    @Test
    public void testBadItemWight()  {
        assertThrows(APIException.class,()-> Packer.pack("src/test/resources/sample-files/bad-item-wight.txt"));
    }
}
