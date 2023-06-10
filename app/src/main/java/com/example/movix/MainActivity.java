package com.example.movix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
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
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        Toast.makeText(MainActivity.this, "Home is selected", Toast.LENGTH_SHORT).show();
                        selectedFragment = new homeScreen();
                        break;
                    case R.id.recent:
                        Toast.makeText(MainActivity.this, "Recent is Selected", Toast.LENGTH_SHORT).show();
                        selectedFragment = new recentScreen();
                        break;
                    case R.id.popular:
                        Toast.makeText(MainActivity.this, "Popular is selected", Toast.LENGTH_SHORT).show();
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