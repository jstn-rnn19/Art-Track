package com.example.arttrack;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class ArtistPaymentsView extends AppCompatActivity {

    private LinearLayout container;
    private Button tabAll, tabToVerify, tabHistory;
    private CommissionRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_artist_payments_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        }

        container = findViewById(R.id.paymentsContainer);
        tabAll = findViewById(R.id.tabAll);
        tabToVerify = findViewById(R.id.tabToVerify);
        tabHistory = findViewById(R.id.tabHistory);
        repository = new CommissionRepository();

        tabAll.setOnClickListener(v -> switchTab("ALL"));
        tabToVerify.setOnClickListener(v -> switchTab("TO_VERIFY"));
        tabHistory.setOnClickListener(v -> switchTab("HISTORY"));

        loadPayments("ALL");

        // Bottom Navigation Bar wiring
        View navTabHome = findViewById(R.id.navTabHome);
        if (navTabHome != null) {
            navTabHome.setOnClickListener(v -> {
                Intent intent = new Intent(ArtistPaymentsView.this, ArtistHomeDashboardView.class);
                startActivity(intent);
                finish();
            });
        }

        View navTabRequests = findViewById(R.id.navTabRequests);
        if (navTabRequests != null) {
            navTabRequests.setOnClickListener(v -> {
                Intent intent = new Intent(ArtistPaymentsView.this, ArtistRequestsView.class);
                startActivity(intent);
                finish();
            });
        }

        View navTabCommissions = findViewById(R.id.navTabCommissions);
        if (navTabCommissions != null) {
            navTabCommissions.setOnClickListener(v -> {
                Intent intent = new Intent(ArtistPaymentsView.this, ArtistCommissionsView.class);
                startActivity(intent);
                finish();
            });
        }

        View navTabPayments = findViewById(R.id.navTabPayments);
        if (navTabPayments != null) {
            navTabPayments.setOnClickListener(v -> {
                // already on payments
            });
        }

        View navTabProfile = findViewById(R.id.navTabProfile);
        if (navTabProfile != null) {
            navTabProfile.setOnClickListener(v -> Toast.makeText(this, "Profile tab tapped", Toast.LENGTH_SHORT).show());
        }
    }

    private void switchTab(String tab) {
        tabAll.setBackgroundColor(Color.TRANSPARENT);
        tabAll.setTextColor(Color.parseColor("#5A5760"));
        tabToVerify.setBackgroundColor(Color.TRANSPARENT);
        tabToVerify.setTextColor(Color.parseColor("#5A5760"));
        tabHistory.setBackgroundColor(Color.TRANSPARENT);
        tabHistory.setTextColor(Color.parseColor("#5A5760"));

        if (tab.equals("ALL")) {
            tabAll.setBackgroundResource(R.drawable.role_card_selected);
            tabAll.setTextColor(Color.parseColor("#7351B5"));
        } else if (tab.equals("TO_VERIFY")) {
            tabToVerify.setBackgroundResource(R.drawable.role_card_selected);
            tabToVerify.setTextColor(Color.parseColor("#7351B5"));
        } else if (tab.equals("HISTORY")) {
            tabHistory.setBackgroundResource(R.drawable.role_card_selected);
            tabHistory.setTextColor(Color.parseColor("#7351B5"));
        }

        loadPayments(tab);
    }

    private void loadPayments(String tab) {
        if (container == null) return;
        container.removeAllViews();

        List<PaymentItem> rawItems = repository.getArtistPayments();
        List<PaymentItem> items = new ArrayList<>();

        for (PaymentItem item : rawItems) {
            if (tab.equals("TO_VERIFY") && !item.getStatus().equals("PENDING")) continue;
            if (tab.equals("HISTORY") && !item.getStatus().equals("VERIFIED")) continue;
            items.add(item);
        }

        for (PaymentItem item : items) {
            View cardView = getLayoutInflater().inflate(R.layout.item_dashboard_commission, container, false);

            ImageView ivArtwork = cardView.findViewById(R.id.ivArtwork);
            TextView tvTitle = cardView.findViewById(R.id.tvCommissionTitle);
            TextView tvArtist = cardView.findViewById(R.id.tvArtistName);
            TextView tvStatus = cardView.findViewById(R.id.tvStatusBadge);
            TextView tvDue = cardView.findViewById(R.id.tvDueDate);

            if (ivArtwork != null) ivArtwork.setImageResource(item.getImageResId());
            if (tvTitle != null) tvTitle.setText(item.getCommissionTitle());
            if (tvArtist != null) tvArtist.setText(item.getClientName());
            if (tvStatus != null) tvStatus.setText(item.getStatus());
            if (tvDue != null) tvDue.setText(item.getPaymentType() + " • ₱" + item.getAmount());

            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(ArtistPaymentsView.this, ArtistPaymentDetailView.class);
                intent.putExtra("PAYMENT_ID", item.getId());
                intent.putExtra("TITLE", item.getCommissionTitle());
                intent.putExtra("CLIENT", item.getClientName());
                intent.putExtra("TYPE", item.getPaymentType());
                intent.putExtra("AMOUNT", "₱" + item.getAmount());
                startActivity(intent);
            });

            container.addView(cardView);
        }
    }
}