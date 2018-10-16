package com.example.adrian.grouptaskmanagement;

/**
 * Created by Adrian on 10/4/2018.
 */

public class Frag_Offer_recycler_job {

    private String title;
    private String desc;
    private String time;
    private String slotmax;
    private String slotnow;

    public Frag_Offer_recycler_job() {
    }


    public Frag_Offer_recycler_job(String title, String desc, String time, String slotmax, String slotnow) {
        this.title = title;
        this.desc = desc;
        this.time = time;
        this.slotmax = slotmax;
        this.slotnow = slotnow;
    }

    public String getTime() {
        return time;
    }

    public String getSlotmax() {
        return slotmax;
    }

    public String getSlotnow() {
        return slotnow;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}
