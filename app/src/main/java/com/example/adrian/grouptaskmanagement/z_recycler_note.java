package com.example.adrian.grouptaskmanagement;

/**
 * Created by Adrian on 10/4/2018.
 */

public class z_recycler_note {

    private String title;
    private String desc;
    private String prior;

    public z_recycler_note() {
    }

    public z_recycler_note(String title, String desc, String prior) {
        this.title = title;
        this.desc = desc;
        this.prior = prior;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getPrior() {
        return prior;
    }
}
