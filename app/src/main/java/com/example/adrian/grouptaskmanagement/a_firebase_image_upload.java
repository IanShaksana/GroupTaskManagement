package com.example.adrian.grouptaskmanagement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

/**
 * Created by Adrian on 10/19/2018.
 */

public class a_firebase_image_upload {
    private String mName;
    private String mImageUrl;

    public a_firebase_image_upload(){

    }

    public a_firebase_image_upload(String mName, String mImageUrl) {
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
