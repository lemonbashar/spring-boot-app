package com.lemon.spring.interfaces;

import org.springframework.ui.Model;

public interface ViewController<K,ID> {

    /**
     * Redirect The Home Page
     * @return The Home Page View Identity of The Entity
     */
    String home();


    /**
     * Prepare The Save Page to Create Entity
     * @param model Spring UI Model
     * @return Create Page Location With Empty Entity
     */
    String saveEntry(Model model);

    /**
     * Save The Entity and Redirect Home Page
     * @param k The Entity
     * @return Home Page Location
     */
    String save(K k);


    /**
     * Prepare The Save Page to Update Entity
     * @param model The Spring UI Model
     * @param id id of the Entity
     * @return Create Page Location With Object Entity From Database
     */
    String updateEntry(Model model,ID id);


    /**
     * Update The Entity and Redirect Home Page
     * @param k The Populated Entity
     * @return Home Page Location
     */
    String update(K k);

    /**
     * Find A Entity by it's ID and view it on The All Pge
     * @param id Id
     * @param model Spring UI Model
     * @return All Page Directory
     */
    String findOne(ID id,Model model);

    /**
     * Find All Entity From It's Table and View them on All Page
     * @param model Ui Model
     * @return All Page Location
     */
    String findAll(Model model);

    /**
     * Delete an Entity and Return to home page
     * @param id Id To Delete
     * @return Home Page Location
     */
    String delete(ID id);

    default String view(String view) {
        return baseView()+view;
    }

    /**
     * Base Directory Location of the Entity
     * @return Base Directory
     */
    String baseView();
}
