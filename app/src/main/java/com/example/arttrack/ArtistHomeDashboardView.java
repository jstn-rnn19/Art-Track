package com.example.arttrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ArtistHomeDashboardView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_artist_home_dashboard_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tvArtistGreeting = findViewById(R.id.tvArtistGreeting);
        Intent intent = getIntent();
        String name = intent != null ? intent.getStringExtra("DISPLAY_NAME") : null;
        if (name == null || name.trim().isEmpty()) {
            name = intent != null ? intent.getStringExtra("FULL_NAME") : null;
        }

        if (name != null && !name.trim().isEmpty()) {
            String firstName = name.trim().split("\\s+")[0];
            tvArtistGreeting.setText(getString(R.string.greeting_format, firstName));
        } else {
            tvArtistGreeting.setText(getString(R.string.greeting_format, "Mika"));
        }

        View btnQuickUpload = findViewById(R.id.btnQuickUpload);
        if (btnQuickUpload != null) {
            btnQuickUpload.setOnClickListener(v -> Toast.makeText(this, "Upload artwork tapped", Toast.LENGTH_SHORT).show());
        }

        View btnQuickRequests = findViewById(R.id.btnQuickRequests);
        if (btnQuickRequests != null) {
            btnQuickRequests.setOnClickListener(v -> openRequests());
        }

        View cardStatPending = findViewById(R.id.cardStatPending);
        if (cardStatPending != null) {
            cardStatPending.setOnClickListener(v -> openRequests());
        }

        View navTabRequests = findViewById(R.id.navTabRequests);
        if (navTabRequests != null) {
            navTabRequests.setOnClickListener(v -> openRequests());
        }

        View navTabCommissions = findViewById(R.id.navTabCommissions);
        if (navTabCommissions != null) {
            navTabCommissions.setOnClickListener(v -> {
                Intent commIntent = new Intent(ArtistHomeDashboardView.this, ArtistCommissionsView.class);
                startActivity(commIntent);
            });
        }

        View navTabPayments = findViewById(R.id.navTabPayments);
        if (navTabPayments != null) {
            navTabPayments.setOnClickListener(v -> {
                Intent payIntent = new Intent(ArtistHomeDashboardView.this, ArtistPaymentsView.class);
                startActivity(payIntent);
            });
        }

        View navTabProfile = findViewById(R.id.navTabProfile);
        if (navTabProfile != null) {
            navTabProfile.setOnClickListener(v -> {
                Intent profileIntent = new Intent(ArtistHomeDashboardView.this, ArtistProfileView.class);
                startActivity(profileIntent);
            });
        }

        View btnQuickVerify = findViewById(R.id.btnQuickVerify);
        if (btnQuickVerify != null) {
            btnQuickVerify.setOnClickListener(v -> {
                Intent payIntent = new Intent(ArtistHomeDashboardView.this, ArtistPaymentsView.class);
                startActivity(payIntent);
            });
        }

        View btnQuickDeadlines = findViewById(R.id.btnQuickDeadlines);
        if (btnQuickDeadlines != null) {
            btnQuickDeadlines.setOnClickListener(v -> Toast.makeText(this, "Deadlines tapped", Toast.LENGTH_SHORT).show());
        }
    }

    private void openRequests() {
        Intent reqIntent = new Intent(ArtistHomeDashboardView.this, ArtistRequestsView.class);
        startActivity(reqIntent);
    }
}