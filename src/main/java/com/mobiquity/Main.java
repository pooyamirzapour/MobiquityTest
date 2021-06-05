package com.mobiquity;


import com.mobiquity.exception.APIException;
import com.mobiquity.model.Line;
import com.mobiquity.packer.Packer;
import com.mobiquity.service.read.ReadServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * This class is the start point of the application, and will process the passed parameters.

 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        try {
            Stream<Line> read = ReadServiceImpl.INSTANCE.read("src/main/test/resources/example_input");

            if (args.length == 0) {
                throw new APIException("The file path should be sent.");
            }
            final String pack = Packer.pack(args[0]);
            log.info(String.format("Output:\r\n%s", pack));
        } catch (Exception e) {
            log.error(Optional.of(e.getMessage()).orElse("Error occurred in the packing."));
        }
    }

}
