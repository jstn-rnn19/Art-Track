package com.example.arttrack;

/**
 * Data model representing a Commission item in the Client Dashboard.
 * Ready for database integration (Room / Firebase / REST API).
 */
public class CommissionItem {
    private String id;
    private String title;
    private String artistName;
    private String status;
    private String dueDate;
    private int imageResId;
    private double price;
    private int progressStep;

    public CommissionItem(String id, String title, String artistName, String status, String dueDate, int imageResId, double price, int progressStep) {
        this.id = id;
        this.title = title;
        this.artistName = artistName;
        this.status = status;
        this.dueDate = dueDate;
        this.imageResId = imageResId;
        this.price = price;
        this.progressStep = progressStep;
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

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProgressStep() {
        return progressStep;
    }

    public void setProgressStep(int progressStep) {
        this.progressStep = progressStep;
    }
}