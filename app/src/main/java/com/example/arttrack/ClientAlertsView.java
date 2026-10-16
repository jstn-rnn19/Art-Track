package com.example.arttrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ClientAlertsView extends AppCompatActivity {

    private LinearLayout container;
    private List<NotificationItem> alerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_alerts_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        container = findViewById(R.id.alertsContainer);
        CommissionRepository repository = new CommissionRepository();
        alerts = repository.getClientAlerts();

        loadAlerts();

        Button btnMarkAllRead = findViewById(R.id.btnMarkAllRead);
        if (btnMarkAllRead != null) {
            btnMarkAllRead.setOnClickListener(v -> {
                for (NotificationItem item : alerts) {
                    item.setUnread(false);
                }
                loadAlerts();
                Toast.makeText(this, "All alerts marked as read", Toast.LENGTH_SHORT).show();
            });
        }

        // Bottom Navigation Bar wiring
        View navTabHome = findViewById(R.id.navTabHome);
        if (navTabHome != null) {
            navTabHome.setOnClickListener(v -> {
                Intent intent = new Intent(ClientAlertsView.this, ClientHomeDashboardView.class);
                startActivity(intent);
                finish();
            });
        }

        View navTabCommissions = findViewById(R.id.navTabCommissions);
        if (navTabCommissions != null) {
            navTabCommissions.setOnClickListener(v -> {
                Intent intent = new Intent(ClientAlertsView.this, ClientCommissionsView.class);
                startActivity(intent);
                finish();
            });
        }

        View navTabPayments = findViewById(R.id.navTabPayments);
        if (navTabPayments != null) {
            navTabPayments.setOnClickListener(v -> {
                Intent intent = new Intent(ClientAlertsView.this, ClientPaymentsView.class);
                startActivity(intent);
                finish();
            });
        }

        View navTabAlerts = findViewById(R.id.navTabAlerts);
        if (navTabAlerts != null) {
            navTabAlerts.setOnClickListener(v -> {
                // already on alerts
            });
        }

        View navTabProfile = findViewById(R.id.navTabProfile);
        if (navTabProfile != null) {
            navTabProfile.setOnClickListener(v -> {
                Intent intent = new Intent(ClientAlertsView.this, ClientProfileView.class);
                startActivity(intent);
                finish();
            });
        }
    }

    private void loadAlerts() {
        if (container == null) return;
        container.removeAllViews();

        for (NotificationItem item : alerts) {
            View cardView = getLayoutInflater().inflate(R.layout.item_dashboard_commission, container, false);

            View ivArtwork = cardView.findViewById(R.id.ivArtwork);
            if (ivArtwork != null) ivArtwork.setVisibility(View.GONE);

            TextView tvTitle = cardView.findViewById(R.id.tvCommissionTitle);
            TextView tvArtist = cardView.findViewById(R.id.tvArtistName);
            TextView tvStatus = cardView.findViewById(R.id.tvStatusBadge);
            TextView tvDue = cardView.findViewById(R.id.tvDueDate);

            if (tvTitle != null) tvTitle.setText(item.getTitle());
            if (tvArtist != null) tvArtist.setVisibility(View.GONE);
            if (tvStatus != null) {
                tvStatus.setText(item.isUnread() ? "NEW" : "READ");
                tvStatus.setBackgroundResource(item.isUnread() ? R.drawable.role_card_selected : R.drawable.role_card_unselected);
            }
            if (tvDue != null) tvDue.setText(item.getTime());

            if (item.isUnread()) {
                cardView.setBackgroundResource(R.drawable.role_card_selected);
            } else {
                cardView.setBackgroundResource(R.drawable.role_card_unselected);
            }

            cardView.setOnClickListener(v -> {
                item.setUnread(false);
                loadAlerts();
                Toast.makeText(this, "Opened alert: " + item.getTitle(), Toast.LENGTH_SHORT).show();
            });

            container.addView(cardView);
        }
    }
}