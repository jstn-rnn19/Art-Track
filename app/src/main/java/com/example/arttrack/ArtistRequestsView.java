package com.example.arttrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import java.util.List;

public class ArtistRequestsView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_artist_requests_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        }

        LinearLayout container = findViewById(R.id.requestsContainer);
        CommissionRepository repository = new CommissionRepository();
        List<CommissionItem> requests = repository.getIncomingRequests();

        if (container != null) {
            for (CommissionItem item : requests) {
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

                cardView.setOnClickListener(v -> {
                    Intent intent = new Intent(ArtistRequestsView.this, ArtistRequestDetailView.class);
                    intent.putExtra("REQUEST_ID", item.getId());
                    intent.putExtra("TITLE", item.getTitle());
                    intent.putExtra("CLIENT", item.getArtistName());
                    intent.putExtra("BUDGET", "₱" + item.getPrice());
                    intent.putExtra("DEADLINE", item.getDueDate());
                    startActivity(intent);
                });

                container.addView(cardView);
            }
        }

        // Bottom Navigation Bar wiring
        View navTabHome = findViewById(R.id.navTabHome);
        if (navTabHome != null) {
            navTabHome.setOnClickListener(v -> finish());
        }

        View navTabRequests = findViewById(R.id.navTabRequests);
        if (navTabRequests != null) {
            navTabRequests.setOnClickListener(v -> {
                // already on requests
            });
        }

        View navTabCommissions = findViewById(R.id.navTabCommissions);
        if (navTabCommissions != null) {
            navTabCommissions.setOnClickListener(v -> {
                Intent intent = new Intent(ArtistRequestsView.this, ArtistCommissionsView.class);
                startActivity(intent);
                finish();
            });
        }

        View navTabPayments = findViewById(R.id.navTabPayments);
        if (navTabPayments != null) {
            navTabPayments.setOnClickListener(v -> {
                Intent intent = new Intent(ArtistRequestsView.this, ArtistPaymentsView.class);
                startActivity(intent);
                finish();
            });
        }

        View navTabProfile = findViewById(R.id.navTabProfile);
        if (navTabProfile != null) {
            navTabProfile.setOnClickListener(v -> {
                Intent intent = new Intent(ArtistRequestsView.this, ArtistProfileView.class);
                startActivity(intent);
                finish();
            });
        }
    }
}