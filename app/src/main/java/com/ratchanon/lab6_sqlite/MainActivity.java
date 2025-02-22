package com.ratchanon.lab6_sqlite;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomNavigationView bottomNavigation;
    Fragment selectedFragment = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        loadFragment(new ViewFragment());

        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment tempSelectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.item_1) {
                tempSelectedFragment = new ViewFragment();
            }else if(itemId == R.id.item_2) {
                tempSelectedFragment = new AddFragment();
            }

            if (selectedFragment != null && Objects.equals(tempSelectedFragment, selectedFragment)) return true;

            if (tempSelectedFragment != null) {
                loadFragment(tempSelectedFragment);
            }
            return true;
        });
    }
    // Method to load a fragment into the container
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
}