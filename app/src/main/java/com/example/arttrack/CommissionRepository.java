package com.example.arttrack;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for fetching commission items.
 * When integrating a database (Room / Firebase), update this repository
 * to query your database DAO or Firestore collection instead of returning mock lists.
 */
public class CommissionRepository {

    public List<CommissionItem> getNeedsActionCommissions() {
        List<CommissionItem> list = new ArrayList<>();
        list.add(new CommissionItem("1", "Moonlit Kitsune Portrait", "Artist: Mika Reyes", "AWAITING APPROVAL", "Due Oct 20", R.drawable.finished, 1500.0, 8));
        list.add(new CommissionItem("2", "Tabletop Party Illustration", "Artist: Mika Reyes", "SKETCH READY", "Due Oct 26", R.drawable.lineart, 2200.0, 3));
        return list;
    }

    public List<CommissionItem> getInProgressCommissions() {
        List<CommissionItem> list = new ArrayList<>();
        list.add(new CommissionItem("3", "Watercolor Wedding Invitation", "Artist: Mika Reyes", "VERIFYING PAYMENT", "Due Oct 15", R.drawable.in_progress, 1800.0, 6));
        return list;
    }

    public List<CommissionItem> getIncomingRequests() {
        List<CommissionItem> list = new ArrayList<>();
        list.add(new CommissionItem("r1", "Cyberpunk Street Scene", "Client: Jonas Villanueva", "NEW REQUEST", "Wanted by Nov 15", R.drawable.lineart_two, 5000.0, 1));
        list.add(new CommissionItem("r2", "Chibi Emote Set (6)", "Client: Priya Natarajan", "NEW REQUEST", "Wanted by Nov 01", R.drawable.sketch, 3000.0, 1));
        return list;
    }

    public List<CommissionItem> getActiveCommissions() {
        List<CommissionItem> list = new ArrayList<>();
        list.add(new CommissionItem("c1", "Moonlit Kitsune Portrait", "Client: Jonas Villanueva", "AWAITING APPROVAL", "Due Oct 20", R.drawable.finished, 4500.0, 8));
        list.add(new CommissionItem("c2", "Tabletop Party Illustration", "Client: Priya Natarajan", "SKETCH READY", "Due Oct 26", R.drawable.lineart, 6000.0, 6));
        list.add(new CommissionItem("c3", "Mecha Concept Sheet", "Client: Leo Tan", "SKETCHING", "Due Oct 30", R.drawable.sketch_two, 2400.0, 4));
        return list;
    }

    public List<CommissionItem> getCompletedCommissions() {
        List<CommissionItem> list = new ArrayList<>();
        list.add(new CommissionItem("c4", "Pet Portrait – Biscuit", "Client: Jonas Villanueva", "COMPLETED", "Due Sep 18", R.drawable.finished_one, 1800.0, 10));
        return list;
    }

    public List<CommissionItem> getClosedCommissions() {
        List<CommissionItem> list = new ArrayList<>();
        list.add(new CommissionItem("c5", "Album Cover – Static Bloom", "Client: Mark Ramos", "REJECTED", "Closed", R.drawable.lineart_two, 3500.0, 2));
        return list;
    }

    public List<CommissionItem> getClientCommissions(String tab) {
        List<CommissionItem> list = new ArrayList<>();
        if (tab.equals("ACTIVE")) {
            list.add(new CommissionItem("cc1", "Moonlit Kitsune Portrait", "Artist: Mika Reyes", "AWAITING APPROVAL", "Due Oct 20", R.drawable.finished, 4500.0, 8));
            list.add(new CommissionItem("cc2", "Tabletop Party Illustration", "Artist: Mika Reyes", "SKETCH READY", "Due Oct 26", R.drawable.lineart, 6000.0, 6));
        } else if (tab.equals("PENDING")) {
            list.add(new CommissionItem("cc3", "Cyberpunk Street Scene", "Artist: Mika Reyes", "PENDING REVIEW", "Wanted by Nov 15", R.drawable.lineart_two, 5000.0, 1));
        } else if (tab.equals("COMPLETED")) {
            list.add(new CommissionItem("cc4", "Pet Portrait – Biscuit", "Artist: Mika Reyes", "COMPLETED", "Due Sep 18", R.drawable.finished_one, 1800.0, 10));
        } else if (tab.equals("CLOSED")) {
            list.add(new CommissionItem("cc5", "Album Cover – Static Bloom", "Artist: Mika Reyes", "REJECTED", "Closed", R.drawable.lineart_two, 3500.0, 2));
        }
        return list;
    }

    public List<PaymentItem> getArtistPayments() {
        List<PaymentItem> list = new ArrayList<>();
        list.add(new PaymentItem("p1", "Watercolor Wedding Invitation", "Client: Jonas Villanueva", "Down payment", 1500.0, "PENDING", R.drawable.in_progress));
        list.add(new PaymentItem("p2", "Book Cover – The Salt Road", "Client: Mark Ramos", "Remaining balance", 2600.0, "VERIFIED", R.drawable.finished));
        list.add(new PaymentItem("p3", "Moonlit Kitsune Portrait", "Client: Jonas Villanueva", "Down payment", 2250.0, "VERIFIED", R.drawable.finished_one));
        return list;
    }

    public List<PaymentItem> getClientPayments() {
        List<PaymentItem> list = new ArrayList<>();
        list.add(new PaymentItem("cp1", "Fantasy Tavern Interior", "Artist: Mika Reyes", "Down payment", 1900.0, "DUE", R.drawable.sketch));
        list.add(new PaymentItem("cp2", "Watercolor Wedding Invitation", "Artist: Mika Reyes", "Down payment", 1500.0, "PENDING", R.drawable.in_progress));
        list.add(new PaymentItem("cp3", "Book Cover – The Salt Road", "Artist: Mika Reyes", "Remaining balance", 2600.0, "VERIFIED", R.drawable.finished));
        return list;
    }

    public List<NotificationItem> getClientAlerts() {
        List<NotificationItem> list = new ArrayList<>();
        list.add(new NotificationItem("n1", "Your final artwork is ready for review", "2h ago", true));
        list.add(new NotificationItem("n2", "Mika uploaded a revised sketch for Tabletop Party Illustration", "Yesterday", true));
        list.add(new NotificationItem("n3", "Mika sent you an agreement for Cozy Café Mascot", "Yesterday", true));
        list.add(new NotificationItem("n4", "Payment verified. Mika will release your file soon.", "Oct 11", false));
        return list;
    }
}