package com.example.arttrack;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class ArtistCommissionsView extends AppCompatActivity {

    private LinearLayout container;
    private Button tabActive, tabCompleted, tabClosed;
    private Button chipAll, chipActiveFilter, chipNeedsAction, chipWaiting, chipDelayed;
    private EditText etSearchCommissions;
    private CommissionRepository repository;
    private String currentTab = "ACTIVE";
    private String currentFilter = "ALL";
    private String searchQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_artist_commissions_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        }

        container = findViewById(R.id.commissionsContainer);
        tabActive = findViewById(R.id.tabActive);
        tabCompleted = findViewById(R.id.tabCompleted);
        tabClosed = findViewById(R.id.tabClosed);

        chipAll = findViewById(R.id.chipAll);
        chipActiveFilter = findViewById(R.id.chipActive);
        chipNeedsAction = findViewById(R.id.chipNeedsAction);
        chipWaiting = findViewById(R.id.chipWaiting);
        chipDelayed = findViewById(R.id.chipDelayed);
        etSearchCommissions = findViewById(R.id.etSearchCommissions);

        repository = new CommissionRepository();

        tabActive.setOnClickListener(v -> switchTab("ACTIVE"));
        tabCompleted.setOnClickListener(v -> switchTab("COMPLETED"));
        tabClosed.setOnClickListener(v -> switchTab("CLOSED"));

        chipAll.setOnClickListener(v -> switchFilter("ALL"));
        chipActiveFilter.setOnClickListener(v -> switchFilter("ACTIVE_CHIP"));
        chipNeedsAction.setOnClickListener(v -> switchFilter("NEEDS_ACTION"));
        chipWaiting.setOnClickListener(v -> switchFilter("WAITING"));
        chipDelayed.setOnClickListener(v -> switchFilter("DELAYED"));

        if (etSearchCommissions != null) {
            etSearchCommissions.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    searchQuery = s != null ? s.toString().trim().toLowerCase() : "";
                    loadCommissions();
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }

        loadCommissions();

        // Bottom Navigation Bar wiring
        View navTabHome = findViewById(R.id.navTabHome);
        if (navTabHome != null) {
            navTabHome.setOnClickListener(v -> {
                Intent intent = new Intent(ArtistCommissionsView.this, ArtistHomeDashboardView.class);
                startActivity(intent);
                finish();
            });
        }

        View navTabRequests = findViewById(R.id.navTabRequests);
        if (navTabRequests != null) {
            navTabRequests.setOnClickListener(v -> {
                Intent intent = new Intent(ArtistCommissionsView.this, ArtistRequestsView.class);
                startActivity(intent);
                finish();
            });
        }

        View navTabPayments = findViewById(R.id.navTabPayments);
        if (navTabPayments != null) {
            navTabPayments.setOnClickListener(v -> {
                Intent intent = new Intent(ArtistCommissionsView.this, ArtistPaymentsView.class);
                startActivity(intent);
                finish();
            });
        }

        View navTabProfile = findViewById(R.id.navTabProfile);
        if (navTabProfile != null) {
            navTabProfile.setOnClickListener(v -> {
                Intent intent = new Intent(ArtistCommissionsView.this, ArtistProfileView.class);
                startActivity(intent);
                finish();
            });
        }
    }

    private void switchTab(String tab) {
        currentTab = tab;
        tabActive.setBackgroundColor(Color.TRANSPARENT);
        tabActive.setTextColor(Color.parseColor("#5A5760"));
        tabCompleted.setBackgroundColor(Color.TRANSPARENT);
        tabCompleted.setTextColor(Color.parseColor("#5A5760"));
        tabClosed.setBackgroundColor(Color.TRANSPARENT);
        tabClosed.setTextColor(Color.parseColor("#5A5760"));

        if (tab.equals("ACTIVE")) {
            tabActive.setBackgroundResource(R.drawable.role_card_selected);
            tabActive.setTextColor(Color.parseColor("#7351B5"));
        } else if (tab.equals("COMPLETED")) {
            tabCompleted.setBackgroundResource(R.drawable.role_card_selected);
            tabCompleted.setTextColor(Color.parseColor("#7351B5"));
        } else if (tab.equals("CLOSED")) {
            tabClosed.setBackgroundResource(R.drawable.role_card_selected);
            tabClosed.setTextColor(Color.parseColor("#7351B5"));
        }

        loadCommissions();
    }

    private void switchFilter(String filter) {
        currentFilter = filter;
        Button[] chips = {chipAll, chipActiveFilter, chipNeedsAction, chipWaiting, chipDelayed};
        for (Button chip : chips) {
            if (chip != null) {
                chip.setBackgroundResource(R.drawable.chip_unselected_bg);
                chip.setTextColor(Color.parseColor("#5A5760"));
            }
        }

        Button activeChip = chipAll;
        if (filter.equals("ACTIVE_CHIP")) activeChip = chipActiveFilter;
        else if (filter.equals("NEEDS_ACTION")) activeChip = chipNeedsAction;
        else if (filter.equals("WAITING")) activeChip = chipWaiting;
        else if (filter.equals("DELAYED")) activeChip = chipDelayed;

        if (activeChip != null) {
            activeChip.setBackgroundResource(R.drawable.role_card_selected);
            activeChip.setTextColor(Color.parseColor("#7351B5"));
        }

        loadCommissions();
    }

    private void loadCommissions() {
        if (container == null) return;
        container.removeAllViews();

        List<CommissionItem> rawItems;
        if (currentTab.equals("ACTIVE")) {
            rawItems = repository.getActiveCommissions();
        } else if (currentTab.equals("COMPLETED")) {
            rawItems = repository.getCompletedCommissions();
        } else {
            rawItems = repository.getClosedCommissions();
        }

        List<CommissionItem> filteredItems = new ArrayList<>();
        for (CommissionItem item : rawItems) {
            // Search query filter
            boolean matchesSearch = searchQuery.isEmpty() ||
                    item.getTitle().toLowerCase().contains(searchQuery) ||
                    item.getArtistName().toLowerCase().contains(searchQuery);

            if (!matchesSearch) continue;

            // Filter chips
            if (currentFilter.equals("ACTIVE_CHIP")) {
                if (item.getStatus().contains("REJECTED") || item.getStatus().contains("COMPLETED")) continue;
            } else if (currentFilter.equals("NEEDS_ACTION")) {
                if (!item.getStatus().contains("APPROVAL") && !item.getStatus().contains("READY")) continue;
            } else if (currentFilter.equals("WAITING")) {
                if (!item.getStatus().contains("SKETCHING")) continue;
            } else if (currentFilter.equals("DELAYED")) {
                if (!item.getTitle().contains("Mecha")) continue;
            }

            filteredItems.add(item);
        }

        for (CommissionItem item : filteredItems) {
            View cardView = getLayoutInflater().inflate(R.layout.item_dashboard_commission, container, false);

            ImageView ivArtwork = cardView.findViewById(R.id.ivArtwork);
            TextView tvTitle = cardView.findViewById(R.id.tvCommissionTitle);
            TextView tvArtist = cardView.findViewById(R.id.tvArtistName);
            TextView tvStatus = cardView.findViewById(R.id.tvStatusBadge);
            TextView tvDue = cardView.findViewById(R.id.tvDueDate);

            if (ivArtwork != null) ivArtwork.setImageResource(item.getImageResId());
            if (tvTitle != null) tvTitle.setText(item.getTitle());
            if (tvArtist != null) tvArtist.setText(item.getArtistName());
            if (tvStatus != null) tvStatus.setText(item.getStatus());
            if (tvDue != null) tvDue.setText(item.getDueDate());

            cardView.setOnClickListener(v -> Toast.makeText(this, "Selected: " + item.getTitle(), Toast.LENGTH_SHORT).show());

            container.addView(cardView);
        }
    }
}