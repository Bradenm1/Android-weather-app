package bit.mckebk2.mobileprojectweatherapp;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/*----------------------------------------------------
-- A singleton class used to get the connection
-- for the Firebase database
----------------------------------------------------*/
class DatabaseConnectionSingleton {

    // Constants
    private static final String NAME_ADDITION = "Route";

    // The user
    private final FirebaseAuth mAuth;
    // Reference to the database
    private final DatabaseReference mDatabase;
    // All routes from the fetching of data
    private static ArrayList<Route> allRoutes;
    // Singleton instance
    private static final DatabaseConnectionSingleton ourInstance = new DatabaseConnectionSingleton();

    /*----------------------------------------------------
    -- Constructor()
    ----------------------------------------------------*/
    private DatabaseConnectionSingleton() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        allRoutes = new ArrayList<>();
        fetchAllRoutes();
    } // End Constructor


    /*----------------------------------------------------
    -- getInstance()
    -- Returns the singleton instance
    ----------------------------------------------------*/
    static DatabaseConnectionSingleton getInstance() {
        return ourInstance;
    }

    /*----------------------------------------------------
    -- writeNewRoute()
    -- Saves a given route to the users database
    ----------------------------------------------------*/
    @SuppressWarnings("ConstantConditions")
    public void writeNewRoute(ArrayList<WeatherMarker> mapMakers, String routeName) {
        //final Map<String, Route> markers = new HashMap<>();
        // lists to hold each markers lat and long
        List<Double> lats = new ArrayList<>();
        List<Double> lngs = new ArrayList<>();
        // Add all markers to an array
        for (WeatherMarker wMarker : mapMakers) {
            lats.add(wMarker.getMarker().getPosition().latitude);
            lngs.add(wMarker.getMarker().getPosition().longitude);
        }
        // Create the route given the data
        Route route = new Route(routeName, lats, lngs);
        // Generate a unquie ID for the data
        String routeKey = mDatabase.push().getKey();
        // Put that into the has map
        //markers.put(routeKey, route);
        DatabaseReference usersRef = mDatabase.child(mAuth.getUid()).child(NAME_ADDITION + ":" + routeName);
        usersRef.setValue(route);
    } // End writeNewRoute

    /*----------------------------------------------------
    -- deleteSavedRoute()
    -- Deletes a given route
    ----------------------------------------------------*/
    @SuppressWarnings("ConstantConditions")
    public void deleteSavedRoute(String route) {
        DatabaseReference childToDelete = mDatabase.child(mAuth.getUid()).child(NAME_ADDITION + ":" + route);
        childToDelete.removeValue();
    } // End deleteSavedRoute

    /*----------------------------------------------------
    -- fetchAllRoutes()
    -- Fetches a snapshot of the database and get the saved routes
    ----------------------------------------------------*/
    @SuppressWarnings("ConstantConditions")
    public void fetchAllRoutes() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(mAuth.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allRoutes = new ArrayList<>();
                // Put all the routes in the snapshot into the allroutes array
                getAllRoutes(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //handle databaseError
            }
        });
    } // End fetchAllRoutes

    /*----------------------------------------------------
    -- getAllRoutes()
    -- Gets all the routes from a given database snapshot
    ----------------------------------------------------*/
    private void getAllRoutes(DataSnapshot dbData) {
        // Loop through a all saved route
        for (DataSnapshot routeData : dbData.getChildren()) {
            // Unwrap the data into the Route class
            Route route = routeData.getValue(Route.class);
            // Add that route to all routes
            allRoutes.add(route);
        }
    } // End getAllRoutes

    // Returns all the current storied routes
    public ArrayList<Route> getAllRoutes() {
        return allRoutes;
    }

    // Returns all the current storied routes
    public void setAllRoutes(ArrayList<Route> routes) {allRoutes = routes;}
}
