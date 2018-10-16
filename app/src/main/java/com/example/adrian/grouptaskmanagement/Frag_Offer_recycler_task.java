package com.example.adrian.grouptaskmanagement;

/**
 * Created by Adrian on 10/4/2018.
 */

public class Frag_Offer_recycler_task {

    private String title;
    private String desc;
    private String diff;
    private String type;
    private String time;
    private String time2;
    private String worker;

    public Frag_Offer_recycler_task() {
    }


    public Frag_Offer_recycler_task(String title, String desc, String diff, String type, String time, String time2, String worker) {
        this.title = title;
        this.desc = desc;
        this.diff = diff;
        this.type = type;
        this.time = time;
        this.time2 = time2;
        this.worker = worker;
    }

    public String getDiff() {
        return diff;
    }

    public String getWorker() {
        return worker;
    }

    public String getType() {
        return type;
    }

    public String getTime2() {
        return time2;
    }

    public String getTime() {
        return time;
    }


    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}
