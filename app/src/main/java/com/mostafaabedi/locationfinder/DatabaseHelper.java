package com.mostafaabedi.locationfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "locationFinder.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "location_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_LATITUDE + " REAL, " +
                COLUMN_LONGITUDE + " REAL)";
        db.execSQL(createTable);

        if (isTableEmpty(db)) {
            // Run data insertion in a background thread to avoid blocking the main thread
            insertInitialDataInBatches(db);
        }
    }

    private boolean isTableEmpty(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        boolean isEmpty = false;
        if (cursor.moveToFirst()) {
            isEmpty = cursor.getInt(0) == 0;
        }
        cursor.close();
        return isEmpty;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void insertInitialDataInBatches(SQLiteDatabase db) {
        if (db == null) {
            throw new IllegalStateException("Database is null");
        }
        String[] addresses = {
                "Oshawa", "Ajax", "Pickering", "Scarborough", "Downtown Toronto",
                "Mississauga", "Brampton", "Markham", "Etobicoke", "Vaughan",
                "Richmond Hill", "Oakville", "Burlington", "Whitby", "Newmarket",
                "Aurora", "Milton", "Georgetown", "Bolton", "Caledon",
                "King City", "Stouffville", "Uxbridge", "Brooklin", "Unionville",
                "Woodbridge", "Thornhill", "North York", "York", "Toronto",
                "East York", "Port Union", "Clarington", "Port Perry", "Courtice",
                "Bowmanville", "Concord", "Maple", "Glen Abbey", "Joshua Creek",
                "Lorne Park", "Cooksville", "Port Credit", "Meadowvale", "Erin Mills",
                "Streetsville", "Applewood", "Long Branch", "Lambton", "Rexdale",
                "Kingsview Village", "Islington", "The Junction", "Kipling", "High Park",
                "Bloor West Village", "Swansea", "Roncesvalles", "Liberty Village", "Yonge and Eglinton",
                "Leaside", "Forest Hill", "Rosedale", "Yorkville", "Queen West",
                "The Annex", "Kensington Market", "Chinatown", "Little Italy", "Parkdale",
                "Cabbagetown", "Regent Park", "Moss Park", "St. Lawrence", "The Distillery District",
                "Corktown", "Danforth", "Greektown", "Riverdale", "Leslieville",
                "The Beaches", "Upper Beaches", "Birch Cliff", "Scarborough Village",
                "Guildwood", "Malvern", "West Hill", "Morningside", "Centennial Scarborough", "Wexford"
        };

        double[][] coordinates = {
                {43.8971, -78.8658}, {43.8509, -79.0204}, {43.8384, -79.0868}, {43.7768, -79.2318},
                {43.6532, -79.3832}, {43.5890, -79.6441}, {43.7315, -79.7624}, {43.8561, -79.3370},
                {43.6205, -79.5132}, {43.8361, -79.4983},
                {43.8865, -79.4280}, {43.4675, -79.6877}, {43.3255, -79.7990}, {43.8975, -78.9429},
                {44.0592, -79.4613}, {43.9995, -79.4660}, {43.5183, -79.8774}, {43.6505, -79.9252},
                {43.8759, -79.7350}, {43.8668, -79.9937},
                {43.9255, -79.5281}, {43.9688, -79.2443}, {44.1001, -79.1181}, {43.9570, -78.9440},
                {43.8785, -79.3100}, {43.7765, -79.5991}, {43.8040, -79.4160}, {43.7615, -79.4110},
                {43.6896, -79.4627}, {43.6532, -79.3832},
                {43.6913, -79.3188}, {43.7850, -79.1320}, {43.9314, -78.6880}, {44.1011, -78.9440},
                {43.9355, -78.7704}, {43.9127, -78.6880}, {43.7990, -79.4827}, {43.8554, -79.5098},
                {43.4690, -79.7021}, {43.4708, -79.6287},
                {43.5275, -79.6253}, {43.5773, -79.6082}, {43.5906, -79.6480}, {43.6005, -79.7396},
                {43.5912, -79.7131}, {43.6303, -79.5726}, {43.5890, -79.4968}, {43.6448, -79.4983},
                {43.7032, -79.5363}, {43.6890, -79.5884},
                {43.7231, -79.5294}, {43.6670, -79.4723}, {43.6880, -79.5322}, {43.6505, -79.4648},
                {43.6453, -79.4502}, {43.6440, -79.4104}, {43.6610, -79.4187}, {43.6534, -79.3842},
                {43.6539, -79.3791}, {43.6475, -79.4018},
                {43.6563, -79.4194}, {43.6785, -79.3528}, {43.6872, -79.3686}, {43.6901, -79.3213},
                {43.6720, -79.3301}, {43.6596, -79.3534}, {43.6505, -79.3773}, {43.6440, -79.3746},
                {43.6365, -79.4182}, {43.6425, -79.3613},
                {43.6598, -79.3497}, {43.6662, -79.3534}, {43.6616, -79.3415}, {43.6680, -79.3243},
                {43.6699, -79.2990}, {43.6748, -79.2895}, {43.6762, -79.2876}, {43.6847, -79.2906},
                {43.7021, -79.2840}, {43.7084, -79.2768},
                {43.7087, -79.2465}, {43.6892, -79.2365}, {43.6853, -79.2320}, {43.7153, -79.2110},
                {43.7252, -79.2265}, {43.7356, -79.2416}, {43.7398, -79.2528}, {43.7433, -79.2696},
                {43.7604, -79.2585}, {43.7695, -79.2574}
        };
        if (addresses.length != coordinates.length) {
            throw new IllegalArgumentException("Addresses and coordinates arrays must have the same length.");
        }


        db.beginTransaction();
        try {
            for (int i = 0; i < addresses.length; i++) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_ADDRESS, addresses[i]);
                values.put(COLUMN_LATITUDE, coordinates[i][0]);
                values.put(COLUMN_LONGITUDE, coordinates[i][1]);
                long result = db.insert(TABLE_NAME, null, values);

                if (result == -1) {
                    // Log the insert failure for debugging
                    System.out.println("Failed to insert row for " + addresses[i]);
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public List<Location> getAllLocations() {
        List<Location> locationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS));
                double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LATITUDE));
                double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LONGITUDE));
                Location location = new Location(address, latitude, longitude);
                locationList.add(location);
            }
            cursor.close();
        }
        return locationList;
    }
    public Location getLocationByAddress(String address) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_LATITUDE, COLUMN_LONGITUDE},
                COLUMN_ADDRESS + " = ? COLLATE NOCASE",  // case-insensitive search
                new String[]{address}, null, null, null);

        Location location = null;
        if (cursor != null && cursor.moveToFirst()) {
            double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LONGITUDE));
            location = new Location(address, latitude, longitude);
            cursor.close();
        }
        db.close();
        return location;
    }

    public void deleteLocation(String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ADDRESS + " = ?", new String[]{address});
        db.close();
    }

    public void updateLocation(String oldAddress, String newAddress, double latitude, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADDRESS, newAddress);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);
        db.update(TABLE_NAME, values, COLUMN_ADDRESS + " = ?", new String[]{oldAddress});
        db.close();
    }
    public void addLocation(String address, double latitude, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }


}
