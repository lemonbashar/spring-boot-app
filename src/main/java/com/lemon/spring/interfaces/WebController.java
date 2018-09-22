package com.lemon.spring.interfaces;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface WebController<K> {

    ResponseEntity<Map<String,Object>> save(K entity);

    ResponseEntity<Map<String,Object>> update(K entity);

    ResponseEntity<K> findOne(Long id);

    ResponseEntity<List<K>> findAll();

    ResponseEntity<Map<String,Object>> delete(Long id);
}
