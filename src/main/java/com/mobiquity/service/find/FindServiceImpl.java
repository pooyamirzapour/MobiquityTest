package com.mobiquity.service.find;

import com.mobiquity.model.Item;
import com.mobiquity.model.Line;
import com.mobiquity.model.Result;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;

//With the help of enum INSTANCE the service is Singleton
public enum FindServiceImpl implements FindService {
    INSTANCE;

    @Override
    public String find(Line line) {
        Result line1 = process(line);
        return line1.toString();
    }

    Result process(Line line) {
        final BigDecimal capacity = line.getCapacity();
        final List<Item> items = line.getItems();
        final int itemsSize = items.size();
        if ((capacity.compareTo(ZERO) <= 0) || (itemsSize <= 0)) {
            return Result.empty();
        }

        final Item currentItem = items.get(itemsSize - 1);
        final List<Item> previousItems = items.subList(0, itemsSize - 1);

        Line currentLine = Line.builder()
                .capacity(capacity)
                .items(previousItems)
                .build();
        Line remainedLine= Line.builder()
                .capacity(capacity.subtract(currentItem.getWeight()))
                .items(previousItems)
                .build();

        final Result previousResult = process(currentLine);
        final Result currentResult = process(remainedLine).clone().addItem(currentItem);
        return max(previousResult, currentResult, capacity);
    }

    private Result max(Result first, Result second, BigDecimal weightLimit) {
        if (second.weight().compareTo(weightLimit) > 0) {
            return first.weight().compareTo(weightLimit) > 0 ? Result.empty() : first;
        }
        return (first.weight().compareTo(weightLimit) > 0) || (first.compareTo(second) < 0) ? second : first;
    }




}

