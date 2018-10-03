package com.example.adrian.grouptaskmanagement;

import com.google.firebase.firestore.Exclude;

/**
 * Created by Adrian on 10/3/2018.
 */

public class z_firebase1 {

    private String ID;
    String desc;
    String name;
    String numb;

    public z_firebase1(){

    }

    public z_firebase1(String desc, String name){
        this.name = name;
        this.desc = desc;
    }

    public z_firebase1(String desc, String name, String numb){
        this.name = name;
        this.desc = desc;
        this.numb = numb;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    @Exclude
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNumb() {
        return numb;
    }
}
