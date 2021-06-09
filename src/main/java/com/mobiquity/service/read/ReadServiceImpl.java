package com.mobiquity.service.read;

import com.mobiquity.exception.APIException;
import com.mobiquity.exception.LineFormatException;
import com.mobiquity.model.Item;
import com.mobiquity.model.Line;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Stream;

import static com.mobiquity.helper.InputPattern.ITEM_PATTERN;
import static com.mobiquity.helper.InputPattern.LINE_PATTERN;

//With the help of enum INSTANCE the service is Singleton
public enum ReadServiceImpl implements ReadService {

    INSTANCE;

    Logger logger = LoggerFactory.getLogger(ReadServiceImpl.class);

    @Override
    public Stream<Line> read(String path) throws APIException {
        try {
            return Files.lines(Paths.get(path), StandardCharsets.UTF_8)
                    .filter(StringUtils::isNotBlank)
                    .map(this::transformToLine);
        } catch (IOException e) {
            logger.error("Error in reading the file");
            throw new APIException("Error in reading the file", e);
        }
    }

    private Line transformToLine(String line) throws NumberFormatException {
        validateLine(line);
        final String[] lineSections = line.split(":");
        return Line.builder()
                .capacity(new BigDecimal(lineSections[0].trim()))
                .items(makeItems(lineSections[1]))
                .build();
    }

    private void validateLine(String line) throws LineFormatException {
        final Matcher matcher = LINE_PATTERN.matcher(line);
        if (!matcher.find()) {
            String msg = String.format("The line format is incorrect: '%s'", line);
            logger.error(msg);
            throw new LineFormatException(msg);
        }
    }

    private List<Item> makeItems(String itemsSection) {
        if (itemsSection.isEmpty()) {
            String msg = "The input string is empty";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        final List<Item> items = new ArrayList<>();
        final Matcher matcher = ITEM_PATTERN.matcher(itemsSection);
        while (matcher.find()) {
            items.add(makeItem(matcher.group(1)));
        }
        return items;
    }

    private Item makeItem(String itemSection) {
        if (itemSection.isEmpty()) {
            String msg = "The input string is empty";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        final String[] itemParts = itemSection.split(",");
        if (!NumberUtils.isNumber(itemParts[0]) || !NumberUtils.isNumber(itemParts[1])
                || !NumberUtils.isNumber(itemParts[2].substring(1))) {
            String msg="The input string is not number";
            logger.error(msg);
            throw new NumberFormatException(msg);
        }
        return Item.builder()
                .index(Integer.valueOf(itemParts[0]))
                .weight(new BigDecimal(itemParts[1].trim()))
                .cost(Integer.valueOf(itemParts[2].substring(1)))
                .build();
    }
}
