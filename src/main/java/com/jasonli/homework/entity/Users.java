package com.jasonli.homework.entity;

import java.io.Serializable;
import java.util.ArrayList;
public class Users implements Serializable {
    private static final long serialVersionUID = 4268287310462519889L;
    private String userName;

    private String userPassword;

    private ArrayList<Roles> userRoles;

    public Users(String userName, String userPassword){
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public Users(String userName, ArrayList<Roles> userRoles){
        this.userName = userName;
        this.userRoles = userRoles;
    }

    public Users(){
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public ArrayList<Roles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(ArrayList<Roles> userRoles) {
        this.userRoles = userRoles;
    }
}
