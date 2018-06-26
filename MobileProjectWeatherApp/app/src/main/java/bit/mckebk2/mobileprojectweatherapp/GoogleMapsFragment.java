package bit.mckebk2.mobileprojectweatherapp;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/*----------------------------------------------------
-- A Fragment used to display the google map
----------------------------------------------------*/
public class GoogleMapsFragment extends Fragment implements OnMapReadyCallback {

    // Constants
    private static final int MAP_TYPE = 1;
    private static final int LINE_WIDTH = 5;
    private static final float DEFAULT_DEPTH = 7.0f;
    public static final String TIME = "Time";

    // Google map instance
    public static GoogleMap mMap;

    @SuppressWarnings("ConstantConditions")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_google_maps, container, false);
        MapView mMapView = view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        // Check if it's first time running this fragment
        if (MainActivity.onFirstRun) FirstTimeRun();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);
        // Create onclick listeners for the buttons
        CreateOnClickListeners(view);
        return view;
    } // End onCreate

    /*----------------------------------------------------
    -- CreateOnClickListeners()
    -- Creates listeners for the fragment
    ----------------------------------------------------*/
    @SuppressWarnings("ConstantConditions")
    public void FirstTimeRun() {
        // Set default name
        MainActivity.routeName = getResources().getString(R.string.default_route_name); ////////
        AddRouteDialog markerRouteDialog = new AddRouteDialog();
        markerRouteDialog.setTargetFragment(GoogleMapsFragment.this, 1449);
        markerRouteDialog.show(getFragmentManager(), TIME);
        MainActivity.onFirstRun = false;
    } // End FirstTimeRun

    /*----------------------------------------------------
    -- CreateOnClickListeners()
    -- Creates listeners for the fragment
    ----------------------------------------------------*/
    public void CreateOnClickListeners(View view) {
        (view.findViewById(R.id.addActionButton)).setOnClickListener(new AddActionButtonPress());
        (view.findViewById(R.id.saveActionButton)).setOnClickListener(new SaveActionButtonPress());
        (view.findViewById(R.id.loadActionButton)).setOnClickListener(new LoadActionButtonPress());
    } // End CreateOnClickListeners

    /*----------------------------------------------------
    -- onMapReady()
    -- getMapAsync
    -- Runs once when map is ready
    ----------------------------------------------------*/
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Move camera to new location
        mMap.setOnMapClickListener(new PlaceMarkerAt());
        // Check if there's markers and display them
        mMap.setMapType(MAP_TYPE);
        // Move camera to s
        MoveCameraToStart();
    } // End onMapReady

    /*----------------------------------------------------
    -- PlaceMarkerAt()
    -- implements OnMapClickListener
    -- Allows user to place markers given the clicked position
    ----------------------------------------------------*/
    public class PlaceMarkerAt implements GoogleMap.OnMapClickListener {
        private static final String TIME = GoogleMapsFragment.TIME;
        private static final String LONG = "long";
        private static final String LAT = "lat";

        @SuppressWarnings("ConstantConditions")
        @Override
        public void onMapClick(LatLng location) {
            Bundle bundle = new Bundle();
            // Params to give to the dialog fragment
            bundle.putDouble(LAT, location.latitude);
            bundle.putDouble(LONG, location.longitude);
            // Create the fragment
            AddMarkerDialog editNameDialogFragment = new AddMarkerDialog();
            editNameDialogFragment.setArguments(bundle);
            // Show the dialog fragment
            editNameDialogFragment.setTargetFragment(GoogleMapsFragment.this, 1337);
            editNameDialogFragment.show(getFragmentManager(), TIME);
        } // End onMapClick
    } // End PlaceMarkerAt

    /*----------------------------------------------------
    -- AddActionButtonPress()
    -- implements OnClickListener
    -- Allows the user to create a new route
    ----------------------------------------------------*/
    public class AddActionButtonPress implements View.OnClickListener {

        private static final String TIME = GoogleMapsFragment.TIME;

        @SuppressWarnings("ConstantConditions")
        @Override
        public void onClick(View view) {
            AddRouteDialog AddRouteDialog = new AddRouteDialog();
            // Show the dialog fragment
            AddRouteDialog.setTargetFragment(GoogleMapsFragment.this, 1449);
            AddRouteDialog.show(getFragmentManager(), TIME);
        }
    } // End AddActionButtonPress

    /*----------------------------------------------------
    -- SaveActionButtonPress()
    -- implements OnClickListener
    -- Allows the user to create a new route
    ----------------------------------------------------*/
    public class SaveActionButtonPress implements View.OnClickListener {

        private static final String TIME = GoogleMapsFragment.TIME;

        @SuppressWarnings("ConstantConditions")
        @Override
        public void onClick(View view) {
            if (MainActivity.mapMakers.size() > 0) {
                SaveMarkerDialog saveRouteDialog = new SaveMarkerDialog();
                // Show the dialog fragment
                saveRouteDialog.setTargetFragment(GoogleMapsFragment.this, 1449);
                saveRouteDialog.show(getFragmentManager(), TIME);
            } else {
                Toast.makeText(getActivity(), R.string.toast_error_no_markers, Toast.LENGTH_LONG).show();
            }
        }
    } // End SaveActionButtonPress

    /*----------------------------------------------------
    -- LoadActionButtonPress()
    -- implements OnClickListener
    -- Allows the user to load a route
    ----------------------------------------------------*/
    public class LoadActionButtonPress implements View.OnClickListener {

        private static final String TIME = GoogleMapsFragment.TIME;

        @SuppressWarnings("ConstantConditions")
        @Override
        public void onClick(View view) {
            // Get all the routes the user has saved from the database
            DatabaseConnectionSingleton.getInstance().fetchAllRoutes();
            // Check if user has any
            if (DatabaseConnectionSingleton.getInstance().getAllRoutes().size() != 0) {
                LoadRouteDialog loadRouteDialog = new LoadRouteDialog();
                // Show the dialog fragment
                loadRouteDialog.setTargetFragment(GoogleMapsFragment.this, 1449);
                loadRouteDialog.show(getFragmentManager(), TIME);
            } else { // Do the following if user has no routes saved
                Toast.makeText(getActivity(), R.string.toast_error_no_routes, Toast.LENGTH_LONG).show();
            }
        } // End onClick
    } // End LoadActionButtonPress

    /*----------------------------------------------------
    -- LoadRoutes()
    -- Loads a given route from the index
    ----------------------------------------------------*/
    public void LoadRoutes(int index) {
        // Check if the user has any saved routes
        if (DatabaseConnectionSingleton.getInstance().getAllRoutes().size() != 0) {
            // Clear the map
            ClearMap();
            // Clear all the current markers
            MainActivity.mapMakers = new ArrayList<>();
            // Get the selected route given the index
            Route route = DatabaseConnectionSingleton.getInstance().getAllRoutes().get(index);
            // Itterate over all the routes markers and add them to the map
            for (int i = 0; i < route.getLatitudes().size(); i++) {
                CreateMaker(new LatLng((route).getLatitudes().get(i), (route).getLongitudes().get(i)));
            }
            // Move camera to the start of new loaded route
            LatLng startingPosition = new LatLng((route).getLatitudes().get(0), (route).getLongitudes().get(0));
            MoveCamera(startingPosition, DEFAULT_DEPTH);
            Toast.makeText(getActivity(), R.string.toast_route_load, Toast.LENGTH_LONG).show();
        }
        else { // Do the following if the user does not have any saved routes
            Toast.makeText(getActivity(), R.string.toast_error_no_routes, Toast.LENGTH_LONG).show();
        }
    } // End LoadRoutes

    /*----------------------------------------------------
    -- MoveCameraToStart()
    -- Moves camera to the starting position
    ----------------------------------------------------*/
    public void MoveCameraToStart() {
        if (MainActivity.mapMakers.size() > 0) {
            // Draw all markers
            drawMarkers();
            // Draw all marker lines
            drawLines();
            // Move camera to first marker
            MoveCamera(MainActivity.mapMakers.get(0).getMarker().getPosition(), DEFAULT_DEPTH);
        } else {
            LatLng currentLocation = MainActivity.gps.getCurrentLatLng();
            if (currentLocation != null) {
                // Move camera to current position
                MoveCamera(MainActivity.gps.getCurrentLatLng(), DEFAULT_DEPTH);
            } else {
                Toast.makeText(getActivity(), R.string.toast_could_not_retrive_location, Toast.LENGTH_LONG).show();
            }
        }
    } // End MoveCameraToStart

    /*----------------------------------------------------
    -- MoveCamera()
    -- Moves camera to given location at a given depth
    -- @param location LatLng: location to move the camera
    -- @param depth Float: depth the camera should display at
    ----------------------------------------------------*/
    public void MoveCamera(LatLng location, float depth) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, depth));
    } // End MoveCamera

    /*----------------------------------------------------
    -- AddMarker()
    -- Adds a map maker to the map
    -- @param location LatLng: location at which to add the marker
    -- @param markerName String: name of the maker
    -- @return Boolean: if maker was created
    ----------------------------------------------------*/
    public void CreateMaker(LatLng location){
        // Check if given location is not null
        if (location == null) return;
        // Creates a marker
        MarkerOptions markerOptions = new MarkerOptions().position(location);
        getWeatherAtMarker(markerOptions);
    } // End AddMarker

    /*----------------------------------------------------
    -- AddMakerToList()
    -- Adds a marker to the list
    -- @param marker WeatherMarker: the weather marker to add
    ----------------------------------------------------*/
    public void AddMakerToList(WeatherMarker maker) {
        MainActivity.mapMakers.add(maker);
    } // End AddMakerToList

    /*----------------------------------------------------
    -- getWeatherAtMarker()
    -- Adds a given maker to the map
    -- @param marker WeatherMarker: the weather marker to
    -- get the weather for
    ----------------------------------------------------*/
    public void getWeatherAtMarker(MarkerOptions marker) {
        ASYNCGetWeatherAtLocation APITread = new ASYNCGetWeatherAtLocation(GoogleMapsFragment.this, marker);
        // Passing the location of the placed marker
        APITread.execute(marker.getPosition());
    } // End getWeatherAtMarker

    /*----------------------------------------------------
    -- addMarkerToMap()
    -- Adds a given maker to the map
    -- @param marker WeatherMarker: the weather marker to add
    ----------------------------------------------------*/
    public void addMarkerToMap(WeatherMarker wMarker) {
        ASYNCAddMarkerToMap APITread = new ASYNCAddMarkerToMap(GoogleMapsFragment.this, wMarker);
        // Passing the icon url
        APITread.execute(wMarker.getWeatherIconUrl());
    } // End addMarkerToMap

    /*----------------------------------------------------
    -- AddMarkerToMap()
    -- Adds a given maker to the map
    -- @param marker MarkerOptions: the marker to add
    ----------------------------------------------------*/
    public void AddMarkerToMap(MarkerOptions marker){
        mMap.addMarker(marker);
    } // End AddMarkerToMap

    /*----------------------------------------------------
    -- drawLineLast()
    -- Draws a last line between the two last placed lines
    -- @return Boolean: if the line was draw'd
    ----------------------------------------------------*/
    public void drawLineLast() {
        if (MainActivity.mapMakers.size() < 2) return;
        drawLine(MainActivity.mapMakers.size() - 2, MainActivity.mapMakers.size() - 1);
    } // End drawLine

    /*----------------------------------------------------
    -- drawLine()
    -- Draws a line between given indexes of markers within the markers array
    -- @param startIndex Int: index to draw the line from
    -- @param endIndex Int: index to draw the line to
    ----------------------------------------------------*/
    public void drawLine(int startIndex, int endIndex) {
        // Create a Polyline as the line between markers
        PolylineOptions polyLine = new PolylineOptions();
        // Two given points to plot the line
        polyLine.add(
                MainActivity.mapMakers.get(startIndex).getMarker().getPosition(),
                MainActivity.mapMakers.get(endIndex).getMarker().getPosition()
        );
        polyLine.width(LINE_WIDTH);
        polyLine.color(Color.BLACK);
        // Add the given line to the map
        mMap.addPolyline(polyLine);
    } // End drawLine

    /*----------------------------------------------------
    -- drawLines()
    -- Draws all lines between markers
    ----------------------------------------------------*/
    public void drawLines() {
        for(int i = 0; i < MainActivity.mapMakers.size() - 1; i++) {
            drawLine(i, i + 1);
        }
    } // End drawLines

    /*----------------------------------------------------
    -- drawMarkers()
    -- Draws all the markers in the markers array
    ----------------------------------------------------*/
    public void drawMarkers() {
        for (WeatherMarker marker : MainActivity.mapMakers) {
            AddMarkerToMap(marker.getMarker());
        }
    } // End drawMarkers

    /*----------------------------------------------------
    -- ClearMap()
    -- Clears the map of all added elements
    ----------------------------------------------------*/
    public void ClearMap() {
        mMap.clear();
    } // End ClearMap
} // End Class
