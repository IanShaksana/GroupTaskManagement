package com.example.adrian.grouptaskmanagement;

/**
 * Created by Adrian on 10/4/2018.
 */

public class Frag_Inbox_recycler {

    private String from;
    private String mssg;
    private String conf;
    private String type;
    private String mssgCode;

    public Frag_Inbox_recycler() {
    }

    public Frag_Inbox_recycler(String from, String mssg, String conf, String type, String mssgCode) {
        this.from = from;
        this.mssg = mssg;
        this.conf = conf;
        this.type = type;
        this.mssgCode = mssgCode;
    }

    public String getFrom() {
        return from;
    }

    public String getMssg() {
        return mssg;
    }

    public String getConf() {
        return conf;
    }

    public String getMssgCode() {
        return mssgCode;
    }

    public String getType() {
        return type;
    }
}
