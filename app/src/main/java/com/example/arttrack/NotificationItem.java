package com.example.arttrack;

/**
 * Data model representing a notification alert in the Client Dashboard.
 * Ready for database integration (Room / Firebase / REST API).
 */
public class NotificationItem {
    private String id;
    private String title;
    private String time;
    private boolean isUnread;

    public NotificationItem(String id, String title, String time, boolean isUnread) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.isUnread = isUnread;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isUnread() {
        return isUnread;
    }

    public void setUnread(boolean unread) {
        isUnread = unread;
    }
}