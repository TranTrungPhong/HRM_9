package com.framgia.humanresourcemanagement.util;

import java.io.Serializable;

/**
 * Created by PhongTran on 3/20/2016.
 */
public class Employee implements Serializable {
    private int mId;
    private String mName;
    private String mAddress;
    private String mBirthday;
    private String mPhone;
    private String mStatus;
    private String mPosition;

    public Employee(int mId, String name, String address, String birthday, String phone, String status, String position) {
        this.mId = mId;
        this.mName = name;
        this.mAddress = address;
        this.mBirthday = birthday;
        this.mPhone = phone;
        this.mStatus = status;
        this.mPosition = position;
    }

    public int getmID() {
        return mId;
    }

    public void setmID(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        this.mBirthday = birthday;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        this.mPhone = phone;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        this.mStatus = status;
    }

    public String getPosition() {
        return mPosition;
    }

    public void setPosition(String position) {
        this.mPosition = position;
    }
}
