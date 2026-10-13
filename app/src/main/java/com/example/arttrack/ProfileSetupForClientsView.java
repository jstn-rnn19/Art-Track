package com.example.arttrack;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileSetupForClientsView extends AppCompatActivity {

    private FrameLayout avatarCircle;
    private TextView tvAvatarLetter;
    private EditText etDisplayName;

    private final FrameLayout[] swatches = new FrameLayout[6];
    private final View[] rings = new View[6];
    private final int[] swatchColors = new int[]{
            Color.parseColor("#7A52C7"),
            Color.parseColor("#56369A"),
            Color.parseColor("#6242B2"),
            Color.parseColor("#936FE0"),
            Color.parseColor("#A24B00"),
            Color.parseColor("#1B6C3D")
    };

    private int selectedColorIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_setup_for_clients_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        }

        avatarCircle = findViewById(R.id.avatarCircle);
        tvAvatarLetter = findViewById(R.id.tvAvatarLetter);
        etDisplayName = findViewById(R.id.etDisplayName);
        EditText etAboutYou = findViewById(R.id.etAboutYou);

        // Retrieve full name passed from RegistrationView -> RoleSelectionView
        String extraName = getIntent() != null ? getIntent().getStringExtra("FULL_NAME") : "";
        String fullName = extraName != null ? extraName : "";

        // Pre-fill display name
        if (!fullName.isEmpty()) {
            etDisplayName.setText(fullName);
            // Move cursor to the end
            etDisplayName.setSelection(fullName.length());
        }

        // Update initial avatar letter
        updateAvatarLetter(fullName);

        // Listen for changes in display name field to update letter dynamically
        etDisplayName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateAvatarLetter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Initialize color swatches
        initColorSwatches();

        Button btnSaveAndContinue = findViewById(R.id.btnSaveAndContinue);
        if (btnSaveAndContinue != null) {
            btnSaveAndContinue.setOnClickListener(v -> {
                String finalDisplayName = etDisplayName.getText().toString().trim();
                String aboutText = etAboutYou != null ? etAboutYou.getText().toString().trim() : "";

                Toast.makeText(this, "Profile saved for " + (finalDisplayName.isEmpty() ? "Client" : finalDisplayName), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ProfileSetupForClientsView.this, OnBoardingViewForClient.class);
                intent.putExtra("DISPLAY_NAME", finalDisplayName);
                intent.putExtra("ABOUT_YOU", aboutText);
                intent.putExtra("SELECTED_COLOR", swatchColors[selectedColorIndex]);
                startActivity(intent);
                finish();
            });
        }
    }

    private void updateAvatarLetter(String name) {
        String letter = getFirstLetter(name);
        if (tvAvatarLetter != null) {
            tvAvatarLetter.setText(letter);
        }
    }

    private String getFirstLetter(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "A";
        }
        return String.valueOf(name.trim().charAt(0)).toUpperCase();
    }

    private void initColorSwatches() {
        swatches[0] = findViewById(R.id.swatch1);
        swatches[1] = findViewById(R.id.swatch2);
        swatches[2] = findViewById(R.id.swatch3);
        swatches[3] = findViewById(R.id.swatch4);
        swatches[4] = findViewById(R.id.swatch5);
        swatches[5] = findViewById(R.id.swatch6);

        rings[0] = findViewById(R.id.ring1);
        rings[1] = findViewById(R.id.ring2);
        rings[2] = findViewById(R.id.ring3);
        rings[3] = findViewById(R.id.ring4);
        rings[4] = findViewById(R.id.ring5);
        rings[5] = findViewById(R.id.ring6);

        for (int i = 0; i < swatches.length; i++) {
            final int index = i;
            if (swatches[i] != null) {
                swatches[i].setOnClickListener(v -> selectColorSwatch(index));
            }
        }

        selectColorSwatch(0);
    }

    private void selectColorSwatch(int index) {
        selectedColorIndex = index;
        int color = swatchColors[index];

        // Update main avatar background color tint
        if (avatarCircle != null) {
            ViewCompat.setBackgroundTintList(avatarCircle, ColorStateList.valueOf(color));
        }

        // Toggle selection ring visibility
        for (int i = 0; i < rings.length; i++) {
            if (rings[i] != null) {
                rings[i].setVisibility(i == index ? View.VISIBLE : View.GONE);
            }
        }
    }
}