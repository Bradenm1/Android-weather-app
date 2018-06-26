package bit.mckebk2.mobileprojectweatherapp;

import android.content.pm.PackageManager;
import android.location.Criteria;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;

/*----------------------------------------------------
-- A MainActivity class which contains the program
-- The basis for the navigation
----------------------------------------------------*/
public class MainActivity extends AppCompatActivity {

    // If it's the first time running
    public static boolean onFirstRun = true;
    // Create array to hold current route
    public static ArrayList<WeatherMarker> mapMakers = new ArrayList<>();
    // Name of the current route
    public static String routeName;
    // GPS class which allows calls to get current location
    public static GPS gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get navigation bar
        BottomNavigationView navigation = findViewById(R.id.navigation);
        // Set onclick for navigation
        navigation.setOnNavigationItemSelectedListener(new NavigationSelection());
        // Create new array list, in-case one already exists
        DatabaseConnectionSingleton.getInstance().setAllRoutes(new ArrayList<Route>());
        // Create GPS class to get location
        gps = new GPS(this);
        // Deletes all markers on creation of this activity
        mapMakers = new ArrayList<>();
        // Pick default fragment
        pickFragment(R.id.navigation_home);
    } // End onCreate

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            gps.checkLocationPermission();
            {
                GPS.providerName = GPS.locationManager.getBestProvider(new Criteria(), false);
                GPS.locationManager.requestLocationUpdates(GPS.providerName, 500, 1, new GPS.CustomLocationListener());
            }
        }
    } // End onRequestPermissionsResult

    /*----------------------------------------------------
    -- pickFragment()
    -- implements OnNavigationItemSelectedListener
    -- Changes the fragment for the navigation
    ----------------------------------------------------*/
    private class NavigationSelection implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return pickFragment(item.getItemId());
        } // End onNavigationItemSelected
    } // End BottomNavigationView

    /*----------------------------------------------------
    -- pickFragment()
    -- Changes the fragment
    -- @param index Int: the fragment to select
    -- @return Boolean: if a fragment was loaded
    ----------------------------------------------------*/
    private boolean pickFragment(int index) {
        // Start fragment transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (index) {
            // Open Home (Google Map) tab
            case R.id.navigation_home: {
                GoogleMapsFragment fragment = new GoogleMapsFragment();
                transaction.replace(R.id.fragment, fragment);
                break;
            }
            // Open Weather Stats tab
            case R.id.navigation_dashboard: {
                RouteStatsFragment fragment = new RouteStatsFragment();
                transaction.replace(R.id.fragment, fragment);
                break;
            }
            // Open help tab
            case R.id.navigation_help: {
                HelpFragment fragment = new HelpFragment();
                transaction.replace(R.id.fragment, fragment);
                break;
            }
            default:
                return false;
        }
        transaction.commit();
        return true;
    } // End pickFragment
} // End Class
