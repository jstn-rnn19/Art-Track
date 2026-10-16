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

public class ClientCommissionsView extends AppCompatActivity {

    private LinearLayout container;
    private Button tabActive, tabPending, tabCompleted, tabClosed;
    private EditText etSearchCommissions;
    private CommissionRepository repository;
    private String currentTab = "ACTIVE";
    private String searchQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_commissions_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        }

        container = findViewById(R.id.clientCommissionsContainer);
        tabActive = findViewById(R.id.tabActive);
        tabPending = findViewById(R.id.tabPending);
        tabCompleted = findViewById(R.id.tabCompleted);
        tabClosed = findViewById(R.id.tabClosed);
        etSearchCommissions = findViewById(R.id.etSearchCommissions);

        repository = new CommissionRepository();

        tabActive.setOnClickListener(v -> switchTab("ACTIVE"));
        tabPending.setOnClickListener(v -> switchTab("PENDING"));
        tabCompleted.setOnClickListener(v -> switchTab("COMPLETED"));
        tabClosed.setOnClickListener(v -> switchTab("CLOSED"));

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
                Intent intent = new Intent(ClientCommissionsView.this, ClientHomeDashboardView.class);
                startActivity(intent);
                finish();
            });
        }

        View navTabCommissions = findViewById(R.id.navTabCommissions);
        if (navTabCommissions != null) {
            navTabCommissions.setOnClickListener(v -> {
                // already on commissions
            });
        }

        View navTabPayments = findViewById(R.id.navTabPayments);
        if (navTabPayments != null) {
            navTabPayments.setOnClickListener(v -> {
                Intent intent = new Intent(ClientCommissionsView.this, ClientPaymentsView.class);
                startActivity(intent);
                finish();
            });
        }

        View navTabAlerts = findViewById(R.id.navTabAlerts);
        if (navTabAlerts != null) {
            navTabAlerts.setOnClickListener(v -> {
                Intent intent = new Intent(ClientCommissionsView.this, ClientAlertsView.class);
                startActivity(intent);
                finish();
            });
        }

        View navTabProfile = findViewById(R.id.navTabProfile);
        if (navTabProfile != null) {
            navTabProfile.setOnClickListener(v -> {
                Intent intent = new Intent(ClientCommissionsView.this, ClientProfileView.class);
                startActivity(intent);
                finish();
            });
        }
    }

    private void switchTab(String tab) {
        currentTab = tab;
        tabActive.setBackgroundColor(Color.TRANSPARENT);
        tabActive.setTextColor(Color.parseColor("#5A5760"));
        tabPending.setBackgroundColor(Color.TRANSPARENT);
        tabPending.setTextColor(Color.parseColor("#5A5760"));
        tabCompleted.setBackgroundColor(Color.TRANSPARENT);
        tabCompleted.setTextColor(Color.parseColor("#5A5760"));
        tabClosed.setBackgroundColor(Color.TRANSPARENT);
        tabClosed.setTextColor(Color.parseColor("#5A5760"));

        if (tab.equals("ACTIVE")) {
            tabActive.setBackgroundResource(R.drawable.role_card_selected);
            tabActive.setTextColor(Color.parseColor("#7351B5"));
        } else if (tab.equals("PENDING")) {
            tabPending.setBackgroundResource(R.drawable.role_card_selected);
            tabPending.setTextColor(Color.parseColor("#7351B5"));
        } else if (tab.equals("COMPLETED")) {
            tabCompleted.setBackgroundResource(R.drawable.role_card_selected);
            tabCompleted.setTextColor(Color.parseColor("#7351B5"));
        } else if (tab.equals("CLOSED")) {
            tabClosed.setBackgroundResource(R.drawable.role_card_selected);
            tabClosed.setTextColor(Color.parseColor("#7351B5"));
        }

        loadCommissions();
    }

    private void loadCommissions() {
        if (container == null) return;
        container.removeAllViews();

        List<CommissionItem> rawItems = repository.getClientCommissions(currentTab);
        List<CommissionItem> filteredItems = new ArrayList<>();

        for (CommissionItem item : rawItems) {
            boolean matchesSearch = searchQuery.isEmpty() ||
                    item.getTitle().toLowerCase().contains(searchQuery) ||
                    item.getArtistName().toLowerCase().contains(searchQuery);

            if (!matchesSearch) continue;
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