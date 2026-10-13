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
}