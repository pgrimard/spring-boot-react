package com.patrickgrimard.examples;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created on 2016-11-25
 *
 * @author Patrick
 */
@Entity
public class Item {

    @Id
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public Item() {

    }

    public String getName() {
        return name;
    }
}
