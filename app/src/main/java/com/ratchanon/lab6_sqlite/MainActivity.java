package com.ratchanon.lab6_sqlite;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.Manifest;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_STORAGE_PERMISSION = 112;
    FrameLayout frameLayout;
    public BottomNavigationView bottomNavigation;
    Fragment selectedFragment = null;
    DatabaseHelper databaseHelper;
    SQLiteDatabase mDb;

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

        databaseHelper = new DatabaseHelper(this);


        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        loadFragment(new ViewFragment(this));

        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment tempSelectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.item_1) {
                tempSelectedFragment = new ViewFragment(this);
            } else if (itemId == R.id.item_2) {
                tempSelectedFragment = new AddFragment(this);
            } else if (itemId == R.id.item_3) {
//                databaseHelper.importDatabase();
//                Toast.makeText(this, "Import database complete.", Toast.LENGTH_SHORT).show();
//                bottomNavigation.setSelectedItemId(R.id.item_1);
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Database")
                        .setNeutralButton("Cancel", (dialog, which) -> {
                        })
                        .setPositiveButton("Export", (dialog, which) -> {
                            checkAndRequestStoragePermission();
                            bottomNavigation.setSelectedItemId(R.id.item_1);
                        })
                        .setNegativeButton("Import", (dialog, which) -> {
                            databaseHelper.importDatabase();
                            bottomNavigation.setSelectedItemId(R.id.item_1);
                        })
                        .show();
            }

            if (selectedFragment != null && Objects.equals(tempSelectedFragment, selectedFragment))
                return true;

            if (tempSelectedFragment != null) {
                selectedFragment = tempSelectedFragment;
                loadFragment(selectedFragment);
            }
            return true;
        });

    }

    private void checkAndRequestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION);
        } else {
            databaseHelper.exportDatabase();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            databaseHelper.exportDatabase();
        } else {
            Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to load a fragment into the container
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
}