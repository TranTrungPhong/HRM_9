package com.framgia.humanresourcemanagement.util;


import java.io.Serializable;

/**
 * Created by PhongTran on 3/20/2016.
 */
public class Department implements Serializable {
    private int mId;
    private String mName;
    private String mDescription;

    public Department(int id, String name, String description) {
        this.mId = id;
        this.mName = name;
        this.mDescription = description;
    }

    public int getmID() {
        return mId;
    }

    public void setmID(int mID) {
        this.mId = mID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }
}
