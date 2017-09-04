package memsa.ahmed.news.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import memsa.ahmed.news.Fragments.FavoritesFragment;
import memsa.ahmed.news.Fragments.SourcesFragment;
import memsa.ahmed.news.R;
import memsa.ahmed.news.Sync.NewsSyncUtils;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new FavoritesFragment()).commit();
                    return true;
                case R.id.navigation_sources:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new SourcesFragment()).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NewsSyncUtils.initialize(this);

        if (findViewById(R.id.navigation) != null) {
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            navigation.setSelectedItemId(R.id.navigation_home);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_favorites, new FavoritesFragment()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_sources, new SourcesFragment()).commit();
        }
    }

}
