package com.mobiquity.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Data
@Builder
public class Line {
    private BigDecimal capacity;
    private List<Item> items;

    private static final BigDecimal MAX_CAPACITY = BigDecimal.valueOf(100);

    public Line() {
    }



    @Builder
    public Line(BigDecimal capacity, List<Item> items) {
        this.items = items;
        this.capacity = Optional.of(capacity)
                .filter(w -> MAX_CAPACITY.compareTo(w) >= 0)
                .orElseThrow(() -> new IllegalArgumentException("Max capacity package is 100."));
    }
}
