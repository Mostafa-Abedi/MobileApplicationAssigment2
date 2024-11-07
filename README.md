# LocationFinder App

## Overview

The **LocationFinder** app is a simple Android application developed to demonstrate database management and CRUD operations in a mobile app. The app allows users to view, add, update, and delete location data, which includes addresses and their corresponding latitude and longitude coordinates. The app is implemented using SQLite for local data storage, and it initializes with 100 predefined locations in the Greater Toronto Area.

## Features

- **Database Initialization**: Initializes a local SQLite database with 100 entries of GTA locations on first launch.
- **View Locations**: Displays a list of locations, showing both the address and coordinates (latitude and longitude).
- **Add New Location**: Allows users to add new locations with their address, latitude, and longitude.
- **Update Location**: Enables users to edit the address or coordinates of existing locations.
- **Delete Location**: Users can remove any location from the database.

## Project Structure

- **MainActivity**: The main page that shows the search feature to query a location by its address and displays the result.
- **CrudActivity**: Contains a list of all locations with options to add, edit, or delete each entry.
- **DatabaseHelper**: Manages the SQLite database, including creating tables, initializing data, and handling CRUD operations.
- **LocationAdapter**: Adapter class for displaying location data in a RecyclerView.

## Technologies Used

- **Java**: Core programming language for Android.
- **SQLite**: For local database management.
- **RecyclerView**: For displaying a list of location entries.
- **Material Design**: UI components for a modern, responsive interface.

## Initial Setup

The project includes an initial set of 100 locations in the Greater Toronto Area. These are added to the database when the app is first launched. You can view, edit, or delete these entries through the CRUD interface.

### Directory Structure

- `MainActivity.java`: Main screen with search functionality.
- `CrudActivity.java`: Interface for viewing, adding, updating, and deleting locations.
- `DatabaseHelper.java`: Manages SQLite operations for storing and retrieving location data.
- `LocationAdapter.java`: RecyclerView adapter for displaying locations in a list.

## Installation and Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/username/locationfinder.git
