package com.mobiquity.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Optional;

@Data
@Builder
public class Item {

    private static final BigDecimal MAX_WEIGHT = BigDecimal.valueOf(100);


    private Integer index;
    private BigDecimal weight;
    private Integer cost;

    @Builder
    public Item(Integer index, BigDecimal weight, int cost) {
        this.index = index;
        this.cost = cost;
        this.weight = Optional.of(weight)
                .filter(w -> MAX_WEIGHT.compareTo(w) >= 0)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Index '%d':the max wight is 100", index)));
    }
}
