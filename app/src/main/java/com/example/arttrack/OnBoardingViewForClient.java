package com.example.arttrack;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OnBoardingViewForClient extends AppCompatActivity {

    private ViewFlipper pageFlipper;
    private Button nextButton;
    private TextView btnSkip;
    private View dot1;
    private View dot2;
    private View dot3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_on_boarding_view_for_client);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        pageFlipper = findViewById(R.id.pageFlipper);
        nextButton = findViewById(R.id.nextButton);
        btnSkip = findViewById(R.id.btnSkip);

        dot1 = findViewById(R.id.dot1);
        dot2 = findViewById(R.id.dot2);
        dot3 = findViewById(R.id.dot3);

        updatePageState(0, false);

        if (nextButton != null) {
            nextButton.setOnClickListener(v -> goToNextPage());
        }

        if (btnSkip != null) {
            btnSkip.setOnClickListener(v -> navigateToHome());
        }

        if (dot1 != null) dot1.setOnClickListener(v -> goToPage(0));
        if (dot2 != null) dot2.setOnClickListener(v -> goToPage(1));
        if (dot3 != null) dot3.setOnClickListener(v -> goToPage(2));

        setupSwipeGesture();
    }

    private void setupSwipeGesture() {
        GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
                if (e1 == null) return false;
                float diffX = e2.getX() - e1.getX();
                float diffY = e2.getY() - e1.getY();
                if (Math.abs(diffX) > Math.abs(diffY) && Math.abs(diffX) > 100 && Math.abs(velocityX) > 100) {
                    if (diffX < 0) {
                        goToNextPage();
                    } else {
                        goToPreviousPage();
                    }
                    return true;
                }
                return false;
            }
        });

        if (pageFlipper != null) {
            pageFlipper.setOnTouchListener((v, event) -> {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.performClick();
                }
                return false;
            });
        }
    }

    private void goToNextPage() {
        if (pageFlipper == null) return;
        int currentDisplayedChild = pageFlipper.getDisplayedChild();
        if (currentDisplayedChild < 2) {
            pageFlipper.setInAnimation(this, R.anim.slide_in_right);
            pageFlipper.setOutAnimation(this, R.anim.slide_out_left);
            pageFlipper.showNext();
            updatePageState(pageFlipper.getDisplayedChild(), true);
        } else {
            navigateToHome();
        }
    }

    private void goToPreviousPage() {
        if (pageFlipper == null) return;
        int currentDisplayedChild = pageFlipper.getDisplayedChild();
        if (currentDisplayedChild > 0) {
            pageFlipper.setInAnimation(this, R.anim.slide_in_left);
            pageFlipper.setOutAnimation(this, R.anim.slide_out_right);
            pageFlipper.showPrevious();
            updatePageState(pageFlipper.getDisplayedChild(), true);
        }
    }

    private void goToPage(int targetPage) {
        if (pageFlipper == null) return;
        int current = pageFlipper.getDisplayedChild();
        if (targetPage == current) return;

        if (targetPage > current) {
            pageFlipper.setInAnimation(this, R.anim.slide_in_right);
            pageFlipper.setOutAnimation(this, R.anim.slide_out_left);
        } else {
            pageFlipper.setInAnimation(this, R.anim.slide_in_left);
            pageFlipper.setOutAnimation(this, R.anim.slide_out_right);
        }
        pageFlipper.setDisplayedChild(targetPage);
        updatePageState(targetPage, true);
    }

    private void updatePageState(int pageIndex, boolean animateDots) {
        updateIndicators(pageIndex, animateDots);

        if (nextButton != null) {
            if (pageIndex == 2) {
                nextButton.setText("Get Started");
            } else {
                nextButton.setText("Next");
            }
        }

        if (btnSkip != null) {
            btnSkip.setVisibility(pageIndex == 2 ? View.GONE : View.VISIBLE);
        }
    }

    private void updateIndicators(int pageIndex, boolean animateDots) {
        if (dot1 != null) updateDot(dot1, pageIndex == 0, animateDots);
        if (dot2 != null) updateDot(dot2, pageIndex == 1, animateDots);
        if (dot3 != null) updateDot(dot3, pageIndex == 2, animateDots);
    }

    private void updateDot(View dot, boolean isActive, boolean animate) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dot.getLayoutParams();
        if (params == null) return;

        int targetWidth = dpToPx(isActive ? 26 : 8);
        int currentWidth = params.width > 0 ? params.width : dpToPx(isActive ? 26 : 8);

        dot.setBackgroundResource(isActive ? R.drawable.onboarding_active : R.drawable.onboarding_inactive);

        if (!animate || currentWidth == targetWidth) {
            params.width = targetWidth;
            params.height = dpToPx(8);
            dot.setLayoutParams(params);
            return;
        }

        ValueAnimator animator = ValueAnimator.ofInt(currentWidth, targetWidth);
        animator.setDuration(250);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            params.width = (int) animation.getAnimatedValue();
            params.height = dpToPx(8);
            dot.setLayoutParams(params);
        });
        animator.start();
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    private void navigateToHome() {
        String displayName = getIntent() != null ? getIntent().getStringExtra("DISPLAY_NAME") : "";
        Intent intent = new Intent(OnBoardingViewForClient.this, ClientHomeDashboardView.class);
        intent.putExtra("DISPLAY_NAME", displayName);
        startActivity(intent);
        finish();
    }
}