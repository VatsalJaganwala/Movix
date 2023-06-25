package com.example.movix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView nav;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nav = findViewById(R.id.nav);
        ImageView logo = findViewById(R.id.logo);
        int nightModeFlags =
                this.getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                logo.setImageResource(R.drawable.logo_white);
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                logo.setImageResource(R.drawable.logo_black);
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                logo.setImageResource(R.drawable.logo_black);
                break;
        }
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        selectedFragment = new homeScreen();
                        break;
                    case R.id.search:
                        selectedFragment = new recentScreen();
                        break;
                    case R.id.trending:
                        selectedFragment = new popularScreen();

                        break;
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentFrame, selectedFragment)
                        .commit();
                return true;
            }

        });
            nav.setSelectedItemId(R.id.home);



    }
}