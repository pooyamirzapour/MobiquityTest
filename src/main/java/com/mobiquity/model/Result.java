package com.mobiquity.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
public class Result implements Cloneable, Comparable<Result> {

    private static final Result EMPTY = new Result();

    private BigDecimal weight = BigDecimal.ZERO;
    private Integer cost = 0;
    private List<Item> items = new ArrayList<>();

    private Result() {
    }

    public static Result empty() {
        return EMPTY;
    }

    public Result addItem(Item item) {
        this.weight = this.weight.add(item.getWeight());
        this.cost += item.getCost();
        this.items.add(item);
        return this;
    }

    @Override
    public Result clone() {
        Result clone = new Result();
        clone.weight = this.weight;
        clone.cost = this.cost;
        clone.items = new ArrayList<>(this.items);
        return clone;
    }

    @Override
    public int compareTo(Result other) {
        if (other == null) {
            return 1;
        }
        if (this.cost.compareTo(other.cost) == 0) {
            return other.weight.compareTo(this.weight);
        }
        return this.cost.compareTo(other.cost);
    }

    @Override
    public String toString() {
        return items.stream()
                .map(Item::getIndex)
                .map(Object::toString)
                .reduce((first, second) -> String.format("%s, %s", first, second))
                .orElse("-");
    }

}
