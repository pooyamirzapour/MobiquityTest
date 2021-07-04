package com.mobiquity.mypack;

public interface ProductService {

    void add(Product product);

    Product findByName(String name);
}
