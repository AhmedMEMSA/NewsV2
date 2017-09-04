package memsa.ahmed.news.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import memsa.ahmed.news.Fragments.IntroFavoriteFragment;
import memsa.ahmed.news.Fragments.IntroWelcomeFragment;
import memsa.ahmed.news.R;

public class IntroActivity extends AppCompatActivity {

    public FloatingActionButton fabnext;
    IntroWelcomeFragment introWelcomeFragment;
    IntroFavoriteFragment introFavoriteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        introFavoriteFragment = new IntroFavoriteFragment();
        introWelcomeFragment = new IntroWelcomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_intro, introWelcomeFragment).commit();
        fabnext = (FloatingActionButton) findViewById(R.id.fab);
        fabnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSupportFragmentManager().findFragmentById(R.id.frame_intro) instanceof IntroWelcomeFragment) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_intro, introFavoriteFragment).commit();
                    fabnext.setImageDrawable(ContextCompat.getDrawable(IntroActivity.this, R.drawable.ic_done_white_24dp));
                    fabnext.setEnabled(false);

                } else if (getSupportFragmentManager().findFragmentById(R.id.frame_intro) instanceof IntroFavoriteFragment) {
                    introFavoriteFragment.done();
                }
            }
        });
    }

}

