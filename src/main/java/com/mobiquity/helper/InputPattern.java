package com.mobiquity.helper;

import java.util.regex.Pattern;
/**
 * This class provides some regex patterns for evaluating input.
 */
public class InputPattern {

    public static final String INTEGER_REGEX = "\\d+?";
    public static final String DECIMAL_REGEX = String.format("%s(\\.%s)?", INTEGER_REGEX, INTEGER_REGEX);
    public static final String OPTIONAL_SPACE_REGEX = "\\s*?";

    public static final String ITEM_INDEX_REGEX = String.format("%s%s%s", OPTIONAL_SPACE_REGEX, INTEGER_REGEX, OPTIONAL_SPACE_REGEX);
    public static final String ITEM_WEIGHT_REGEX = String.format("%s%s%s", OPTIONAL_SPACE_REGEX, DECIMAL_REGEX, OPTIONAL_SPACE_REGEX);
    public static final String ITEM_COST_REGEX = String.format("%s\\D%s%s", OPTIONAL_SPACE_REGEX, INTEGER_REGEX, OPTIONAL_SPACE_REGEX);

    public static final String ITEM_REGEX = String.format("\\((%s,%s,%s)\\)", ITEM_INDEX_REGEX, ITEM_WEIGHT_REGEX, ITEM_COST_REGEX);
    public static final String WEIGHT_LIMIT_REGEX = String.format("^%s%s%s:", OPTIONAL_SPACE_REGEX, DECIMAL_REGEX, OPTIONAL_SPACE_REGEX);
    public static final String LINE_REGEX = String.format("%s(%s%s)+?", WEIGHT_LIMIT_REGEX, OPTIONAL_SPACE_REGEX, ITEM_REGEX);

    public static final Pattern LINE_PATTERN = Pattern.compile(LINE_REGEX);
    public static final Pattern ITEM_PATTERN = Pattern.compile(ITEM_REGEX);
}
