package com.lemon.spring.interfaces;

import java.math.BigInteger;

public interface ViewController<K> {
    String save(K entity);

    String update(K entity);

    String findOne(BigInteger id);

    String findAll();

    String delete(BigInteger id);
}
