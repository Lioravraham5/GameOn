package com.example.gameon.Screens;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gameon.R;
import com.google.android.material.imageview.ShapeableImageView;

public class IntroActivity extends AppCompatActivity {

    private ShapeableImageView intro_IMG_game_on;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();
        startAnimation(intro_IMG_game_on);
    }

    private void startAnimation(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics(); //object that save Dimensions of the screen
        //note: getWindowManager() -> return the manager of the window in the operation system
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics); // return screen Dimensions
        int height = displayMetrics.heightPixels; //height of the screen

        // start state of the logo:
        view.setY(-height/2.0f);
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        view.setAlpha(0.0f); //transparency

        // animation definition:
        view.animate()
                .alpha(1.0f)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .translationY(0) // move until the initial location of the view in the xml
                .setDuration(4000)// time in miliseconds
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {
                        moveToWelcomeActivity();

                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {

                    }
                });
    }


    private void findViews() {
        intro_IMG_game_on = findViewById(R.id.intro_IMG_game_on);
    }

    private void moveToWelcomeActivity(){
        Intent welcomeIntent = new Intent(this, WelcomePageActivity.class);
        startActivity(welcomeIntent);
        finish();
    }
}