package com.example.arttrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ClientHomeDashboardView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_home_dashboard_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tvGreeting = findViewById(R.id.tvGreeting);
        Intent intent = getIntent();
        String name = intent != null ? intent.getStringExtra("DISPLAY_NAME") : null;
        if (name == null || name.trim().isEmpty()) {
            name = intent != null ? intent.getStringExtra("FULL_NAME") : null;
        }

        if (name != null && !name.trim().isEmpty()) {
            String firstName = name.trim().split("\\s+")[0];
            tvGreeting.setText(getString(R.string.greeting_format, firstName));
        } else {
            tvGreeting.setText("Hi, Jonas");
        }

        ConstraintLayout bannerNewCommission = findViewById(R.id.bannerNewCommission);
        if (bannerNewCommission != null) {
            bannerNewCommission.setOnClickListener(v -> Toast.makeText(this, "New commission request tapped", Toast.LENGTH_SHORT).show());
        }

        View navTabCommissions = findViewById(R.id.navTabCommissions);
        if (navTabCommissions != null) {
            navTabCommissions.setOnClickListener(v -> {
                Intent commIntent = new Intent(ClientHomeDashboardView.this, ClientCommissionsView.class);
                startActivity(commIntent);
            });
        }

        View navTabPayments = findViewById(R.id.navTabPayments);
        if (navTabPayments != null) {
            navTabPayments.setOnClickListener(v -> {
                Intent payIntent = new Intent(ClientHomeDashboardView.this, ClientPaymentsView.class);
                startActivity(payIntent);
            });
        }

        View navTabAlerts = findViewById(R.id.navTabAlerts);
        if (navTabAlerts != null) {
            navTabAlerts.setOnClickListener(v -> {
                Intent alertIntent = new Intent(ClientHomeDashboardView.this, ClientAlertsView.class);
                startActivity(alertIntent);
            });
        }

        View navTabProfile = findViewById(R.id.navTabProfile);
        if (navTabProfile != null) {
            navTabProfile.setOnClickListener(v -> {
                Intent profileIntent = new Intent(ClientHomeDashboardView.this, ClientProfileView.class);
                startActivity(profileIntent);
            });
        }

        // Fetch commissions from repository (ready for Room / Firebase database integration)
        CommissionRepository repository = new CommissionRepository();
        List<CommissionItem> needsActionList = repository.getNeedsActionCommissions();
        List<CommissionItem> inProgressList = repository.getInProgressCommissions();

        View cardAction1 = findViewById(R.id.cardAction1);
        View cardAction2 = findViewById(R.id.cardAction2);
        View cardInProgress = findViewById(R.id.cardInProgress);

        if (!needsActionList.isEmpty()) {
            bindCommissionCard(cardAction1, needsActionList.get(0));
        }
        if (needsActionList.size() > 1) {
            bindCommissionCard(cardAction2, needsActionList.get(1));
        }
        if (!inProgressList.isEmpty()) {
            bindCommissionCard(cardInProgress, inProgressList.get(0));
        }
    }

    private void bindCommissionCard(View cardView, CommissionItem item) {
        if (cardView == null || item == null) return;
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
    }
}