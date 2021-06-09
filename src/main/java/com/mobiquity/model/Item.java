package com.mobiquity.model;

import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Optional;

@Data
@Builder
public class Item {
  private static   Logger logger = LoggerFactory.getLogger(Item.class);

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
                .orElseThrow(() ->
                {
                    String msg = String.format("Index '%d':the max wight is 100", index);
                    logger.error(msg);
                    return new IllegalArgumentException(msg);
                });
    }
}
