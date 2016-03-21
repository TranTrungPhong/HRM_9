package com.framgia.humanresourcemanagement.util;

import java.io.Serializable;

/**
 * Created by PhongTran on 3/20/2016.
 */
public class Employee implements Serializable {
    private String name, address, birthday, phone, status, position;

    public Employee(String name, String address, String birthday, String phone, String status, String position) {
        this.name = name;
        this.address = address;
        this.birthday = birthday;
        this.phone = phone;
        this.status = status;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
