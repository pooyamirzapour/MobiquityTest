package com.mobiquity.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class Line {
    private BigDecimal maxWeight;
    private List<Item> items;
}
