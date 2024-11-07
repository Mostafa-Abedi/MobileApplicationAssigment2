package com.mostafaabedi.locationfinder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText addressInput;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        addressInput = findViewById(R.id.addressInput);
        resultText = findViewById(R.id.resultText);

        Button queryButton = findViewById(R.id.queryButton);
        queryButton.setOnClickListener(v -> queryLocation());

        ImageButton addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CrudActivity.class);
            startActivity(intent);
        });
    }

    private void queryLocation() {
        String address = addressInput.getText().toString().trim();
        Location location = dbHelper.getLocationByAddress(address);

        if (location != null) {
            resultText.setText("Latitude: " + location.latitude + ", Longitude: " + location.longitude);
        } else {
            resultText.setText("Location not found.");
        }
    }
}
