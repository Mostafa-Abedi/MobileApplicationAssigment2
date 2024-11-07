package com.mostafaabedi.locationfinder;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class CrudActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private LocationAdapter locationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadLocations();

        // Set up Floating Action Button to add a new location
        FloatingActionButton addLocationButton = findViewById(R.id.addLocationButton);
        addLocationButton.setOnClickListener(v -> showAddDialog());
    }

    private void loadLocations() {
        List<Location> locations = dbHelper.getAllLocations();
        locationAdapter = new LocationAdapter(dbHelper, locations); // Pass dbHelper to adapter
        recyclerView.setAdapter(locationAdapter);
    }

    private void showAddDialog() {
        // Inflate the dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_location, null);
        EditText editAddress = dialogView.findViewById(R.id.editAddress);
        EditText editLatitude = dialogView.findViewById(R.id.editLatitude);
        EditText editLongitude = dialogView.findViewById(R.id.editLongitude);

        new AlertDialog.Builder(this)
                .setTitle("Add New Location")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    // Get input values
                    String newAddress = editAddress.getText().toString();
                    String latitudeStr = editLatitude.getText().toString();
                    String longitudeStr = editLongitude.getText().toString();

                    if (newAddress.isEmpty() || latitudeStr.isEmpty() || longitudeStr.isEmpty()) {
                        Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double newLatitude = Double.parseDouble(latitudeStr);
                    double newLongitude = Double.parseDouble(longitudeStr);

                    // Add to database and update the RecyclerView
                    dbHelper.addLocation(newAddress, newLatitude, newLongitude);
                    loadLocations(); // Reload list to include new item
                    Toast.makeText(this, "Location added", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
