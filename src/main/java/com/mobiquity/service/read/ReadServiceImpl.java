package com.mobiquity.service.read;

import com.mobiquity.exception.APIException;
import com.mobiquity.exception.LineFormatException;
import com.mobiquity.model.Item;
import com.mobiquity.model.Line;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.math.BigDecimal;
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

    @Override
    public  Stream<Line> read(String path) throws APIException {
        try {
            return Files.lines(Paths.get(path))
                    .filter(StringUtils::isNotBlank)
                    .map(this::transformToLine);
        } catch (IOException e) {
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
            throw new LineFormatException(String.format("The line format is incorrect: '%s'", line));
        }
    }

    private List<Item> makeItems(String itemsSection) {
        if (itemsSection.isEmpty())
            throw new IllegalArgumentException("The input string is empty");
        final List<Item> items = new ArrayList<>();
        final Matcher matcher = ITEM_PATTERN.matcher(itemsSection);
        while (matcher.find()) {
            items.add(makeItem(matcher.group(1)));
        }
        return items;
    }

    private Item makeItem(String itemSection) {
        if (itemSection.isEmpty())
            throw new IllegalArgumentException("The input string is empty");
        final String[] itemParts = itemSection.split(",");
        if (!NumberUtils.isNumber( itemParts[0] ) || ! NumberUtils.isNumber( itemParts[1] ) || !NumberUtils.isNumber( itemParts[2].substring(1) ))
            throw new NumberFormatException("The input string is not number");
        return Item.builder()
                .index(Integer.valueOf(itemParts[0]))
                .weight(new BigDecimal(itemParts[1].trim()))
                .cost(Integer.valueOf(itemParts[2].substring(1)))
                .build();
    }
}
