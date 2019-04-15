package com.lemon.spring.interfaces.impl;

import com.lemon.spring.interfaces.WebController;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public abstract class AbstractRestController<K, ID> implements WebController<K, ID> {

    @Override
    public ResponseEntity<Map<String, Object>> save(K entity) {
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> update(K entity) {
        return null;
    }

    @Override
    public ResponseEntity<K> findOne(ID id) {
        return null;
    }

    @Override
    public ResponseEntity<List<K>> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> delete(ID id) {
        return null;
    }
}
