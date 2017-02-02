package com.sindhu.loginproject;

/**
 * Created by Sindhu on February 01 2017.
 */

public class UserInfo {


    int id;
    String name,mailId,password,phone;

    public UserInfo(String name, String mailId, String password, String phone) {
        this.name = name;
        this.mailId = mailId;
        this.password = password;
        this.phone = phone;
    }

    public UserInfo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
