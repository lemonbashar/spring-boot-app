package com.lemon.spring.interfaces;

import com.lemon.spring.web.page.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WebController<K,ID> {
    String PUBLIC_REST="/api/public";
    String PRIVATE_REST ="/api";

    ResponseEntity<Map<String,Object>> save(K entity);

    ResponseEntity<Map<String,Object>> update(K entity);

    ResponseEntity<K> findOne(ID id);

    ResponseEntity<List<K>> findAll(Pageable pageable);

    ResponseEntity<Map<String,Object>> delete(ID id);

    default ResponseEntity<List<K>> pageOf(Page<K> page,String baseUrl) {
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(page, baseUrl);
        return Optional.ofNullable(page).map(val->new ResponseEntity<>(page.getContent(), httpHeaders, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
