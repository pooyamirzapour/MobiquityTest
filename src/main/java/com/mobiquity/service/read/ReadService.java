package com.mobiquity.service.read;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Item;
import com.mobiquity.model.Line;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Stream;

public interface ReadService {
    Stream<Line> read(String path) throws APIException;
}
