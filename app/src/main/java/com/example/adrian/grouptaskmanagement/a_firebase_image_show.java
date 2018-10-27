package com.example.adrian.grouptaskmanagement;

/**
 * Created by Adrian on 10/19/2018.
 */

public class a_firebase_image_show {
    private String mName;
    private String mImageUrl;

    public a_firebase_image_show(){

    }

    public a_firebase_image_show(String mName, String mImageUrl) {
        if (mName.trim().equals("")){
            mName = "no name";
        }
        this.mName = mName;
        this.mImageUrl = mImageUrl;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }


}
