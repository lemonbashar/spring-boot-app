package com.lemon.spring.interfaces.impl;

import com.lemon.spring.interfaces.ViewController;
import org.springframework.ui.Model;

public abstract class AbstractViewController<K,ID> implements ViewController<K,ID> {
    protected final String HOME_PAGE="index";
    protected final String CREATE_PAGE="create";
    protected final String ALL_PAGE="all";
    @Override
    public String home() {
        return view(HOME_PAGE);
    }

    @Override
    public String saveEntry(Model model) {
        return view(CREATE_PAGE);
    }

    @Override
    public String save(K entity) {
        return home();
    }

    @Override
    public String updateEntry(Model model, ID id) {
        return view(CREATE_PAGE);
    }

    @Override
    public String update(K entity) {
        return home();
    }

    @Override
    public String findOne(ID id, Model model) {
        return view(ALL_PAGE);
    }

    @Override
    public String findAll(Model model) {
        return view(ALL_PAGE);
    }

    @Override
    public String delete(ID id) {
        return home();
    }
}
