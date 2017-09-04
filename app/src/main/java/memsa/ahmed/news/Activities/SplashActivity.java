package memsa.ahmed.news.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import memsa.ahmed.news.R;
import memsa.ahmed.news.Utils.Preferences;

public class SplashActivity extends AppCompatActivity {


    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private Preferences prefrernces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        prefrernces = new Preferences(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (prefrernces.getFavoriteSources().size() != 3) {
                    Intent mainIntent = new Intent(SplashActivity.this, IntroActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();

                } else {
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
