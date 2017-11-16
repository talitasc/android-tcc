package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 15/11/2017.
 */

public class Message {
    String message;
    String tpMsg;
    long createdAt;

    public Message(String message, String tpMsg) {
        this.message = message;
        this.tpMsg = tpMsg;
    }

    public String getTpMsg() {
        return tpMsg;
    }

    public void setTpMsg(String tpMsg) {
        this.tpMsg = tpMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}

