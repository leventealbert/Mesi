package com.leventealbert.mesi;

public class Message {

    private String from;
    private String to;
    private String message;
    private Boolean isNew;
    private Long timeStamp;

    public String getFromId() {
        return from;
    }

    public void setFromId(String fromId) {
        from = fromId;
    }

    public String getToId() {
        return to;
    }

    public void setToId(String toId) {
        to = toId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public String getTimeStamp() {
        return new java.util.Date((long)timeStamp*1000).toString();
    }

    public Message(String fromId, String toId, String message, Boolean isNew) {
        this.from = fromId;
        this.to = toId;
        this.message = message;
        this.isNew = isNew;
        this.timeStamp = System.currentTimeMillis() / 1000L;
    }
}
