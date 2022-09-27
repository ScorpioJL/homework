package com.jasonli.homework.entity;

import java.io.Serializable;

public class Roles implements Serializable{
    private static final long serialVersionUID = 4268287310462519889L;
    private String roleName;

    public Roles(String roleName){
        this.roleName = roleName;
    }

    public Roles(){
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
