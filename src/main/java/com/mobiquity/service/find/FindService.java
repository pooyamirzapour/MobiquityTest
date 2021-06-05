package com.mobiquity.service.find;

import com.mobiquity.model.Item;
import com.mobiquity.model.Line;

import java.util.List;

public interface FindService {

    List<Item> find(Line line);
}
