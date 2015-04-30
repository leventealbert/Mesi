package com.leventealbert.mesi;

/**
 * Data class for the message type
 *
 * @author Levente Albert
 */
public class Message {

    private String from;
    private String to;
    private String message;
    private Boolean isNew;
    private Long timeStamp;

    /**
     * get from id
     *
     * @return String
     */
    public String getFromId() {
        return from;
    }

    /**
     * set from id
     *
     * @param fromId String
     */
    public void setFromId(String fromId) {
        from = fromId;
    }

    /**
     * get to id
     *
     * @return String
     */
    public String getToId() {
        return to;
    }

    /**
     * set to id
     *
     * @param toId String
     */
    public void setToId(String toId) {
        to = toId;
    }

    /**
     * get message
     *
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * set message
     *
     * @param message String
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * get if message is new
     *
     * @return Boolean
     */
    public Boolean getIsNew() {
        return isNew;
    }

    /**
     * set isNew
     *
     * @param isNew Boolean
     */
    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    /**
     * get messaage timestamp
     *
     * @return String
     */
    public String getTimeStamp() {
        return new java.util.Date(timeStamp*1000).toString();
    }

    /**
     * constructor
     *
     * @param fromId String
     * @param toId String
     * @param message String
     * @param isNew Boolean
     */
    public Message(String fromId, String toId, String message, Boolean isNew) {
        this.from = fromId;
        this.to = toId;
        this.message = message;
        this.isNew = isNew;
        this.timeStamp = System.currentTimeMillis() / 1000L;
    }
}
