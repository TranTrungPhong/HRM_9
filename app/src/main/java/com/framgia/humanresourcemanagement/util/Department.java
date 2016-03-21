package com.framgia.humanresourcemanagement.util;


import java.io.Serializable;

/**
 * Created by PhongTran on 3/20/2016.
 */
public class Department implements Serializable{

    private String name, description;

    public Department(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
