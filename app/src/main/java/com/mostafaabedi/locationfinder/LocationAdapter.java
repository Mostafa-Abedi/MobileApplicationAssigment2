package com.mostafaabedi.locationfinder;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private final List<Location> locationList;
    private final DatabaseHelper dbHelper;

    public LocationAdapter(DatabaseHelper dbHelper, List<Location> locationList) {
        this.dbHelper = dbHelper;
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Location location = locationList.get(position);
        holder.addressText.setText(location.address);
        holder.latitudeText.setText("Latitude: " + location.latitude);
        holder.longitudeText.setText("Longitude: " + location.longitude);

        // Ensure latitude and longitude are always visible
        holder.latitudeText.setVisibility(View.VISIBLE);
        holder.longitudeText.setVisibility(View.VISIBLE);

        // Delete button functionality
        holder.deleteButton.setOnClickListener(v -> {
            dbHelper.deleteLocation(location.address);  // Delete from database
            locationList.remove(position);  // Remove from list
            notifyItemRemoved(position);  // Notify adapter
            Toast.makeText(holder.itemView.getContext(), "Location deleted", Toast.LENGTH_SHORT).show();
        });

        // Edit button functionality
        holder.editButton.setOnClickListener(v -> showEditDialog(holder, location, position));
    }

    private void showEditDialog(ViewHolder holder, Location location, int position) {
        View dialogView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.dialog_edit_location, null);
        EditText editAddress = dialogView.findViewById(R.id.editAddress);
        EditText editLatitude = dialogView.findViewById(R.id.editLatitude);
        EditText editLongitude = dialogView.findViewById(R.id.editLongitude);

        // Pre-fill existing data
        editAddress.setText(location.address);
        editLatitude.setText(String.valueOf(location.latitude));
        editLongitude.setText(String.valueOf(location.longitude));

        new AlertDialog.Builder(holder.itemView.getContext())
                .setTitle("Edit Location")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    // Update location in the database and list
                    String newAddress = editAddress.getText().toString();
                    double newLatitude = Double.parseDouble(editLatitude.getText().toString());
                    double newLongitude = Double.parseDouble(editLongitude.getText().toString());

                    dbHelper.updateLocation(location.address, newAddress, newLatitude, newLongitude);  // Update in database
                    location.address = newAddress;
                    location.latitude = newLatitude;
                    location.longitude = newLongitude;
                    notifyItemChanged(position);  // Notify adapter
                    Toast.makeText(holder.itemView.getContext(), "Location updated", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView addressText;
        public final TextView latitudeText;
        public final TextView longitudeText;
        public final Button editButton;
        public final Button deleteButton;

        public ViewHolder(View view) {
            super(view);
            addressText = view.findViewById(R.id.addressText);
            latitudeText = view.findViewById(R.id.latitudeText);
            longitudeText = view.findViewById(R.id.longitudeText);
            editButton = view.findViewById(R.id.editButton);
            deleteButton = view.findViewById(R.id.deleteButton);
        }
    }
}
