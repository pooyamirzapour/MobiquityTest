package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Line;
import com.mobiquity.service.find.FindServiceImpl;
import com.mobiquity.service.read.ReadServiceImpl;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Packer {

    private Packer() {
    }

    public static String pack(String filePath) throws APIException {
      return   ReadServiceImpl.INSTANCE.read(filePath)
               .map(FindServiceImpl.INSTANCE::find)
               .collect(Collectors.joining("\n"));
    }
}
