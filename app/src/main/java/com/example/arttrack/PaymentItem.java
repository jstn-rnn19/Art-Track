package com.example.arttrack;

/**
 * Data model representing a payment record in the Artist Dashboard.
 * Ready for database integration (Room / Firebase / REST API).
 */
public class PaymentItem {
    private String id;
    private String commissionTitle;
    private String clientName;
    private String paymentType; // "Down payment" or "Remaining balance"
    private double amount;
    private String status; // "VERIFIED", "PENDING", "DUE"
    private int imageResId;

    public PaymentItem(String id, String commissionTitle, String clientName, String paymentType, double amount, String status, int imageResId) {
        this.id = id;
        this.commissionTitle = commissionTitle;
        this.clientName = clientName;
        this.paymentType = paymentType;
        this.amount = amount;
        this.status = status;
        this.imageResId = imageResId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommissionTitle() {
        return commissionTitle;
    }

    public void setCommissionTitle(String commissionTitle) {
        this.commissionTitle = commissionTitle;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}