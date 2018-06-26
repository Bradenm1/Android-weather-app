package bit.mckebk2.mobileprojectweatherapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/*----------------------------------------------------
-- A Fragment which is used to display current
-- placed weather marker stats
----------------------------------------------------*/
public class RouteStatsFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        // Get markers
        ArrayList<String> markerList = CreateMarkerList();
        // Check if any stats exist, if not show no markers textview
        if (markerList.size() == 0) noMarkers(view);
        ApplyAdapter(view);
        return view;
    } // End onCreateView

    /*----------------------------------------------------
    -- noMarkers()
    -- Display for no markers
    ----------------------------------------------------*/
    private void noMarkers(View view) {
        view.findViewById(R.id.textNoMarkers).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textHeader).setVisibility(View.GONE);
    }

    /*----------------------------------------------------
    -- CreateMarkerList()
    -- Creates a list of marker information
    -- @returns titles ArrayList<String>: The information
    ----------------------------------------------------*/
    private ArrayList<String> CreateMarkerList() {
        // Get all weather markers
        ArrayList<WeatherMarker> markers = MainActivity.mapMakers;
        // Create new arraylist to hold titles of markers
        ArrayList<String> titles = new ArrayList<>();

        // Get all marker names
        for(int i = 0; i < markers.size(); i++){
            WeatherMarker marker = markers.get(i);
            titles.add(getString(R.string.Marker) + i + getString(R.string.Dash) + marker.toString());
        }

        return titles;
    } // End CreateMarkerList

    /*----------------------------------------------------
    -- CreateMarkerList()
    -- Creates a list of marker information
    -- @param view View: The view for the class fragment
    -- @param list ArrayList<String>: The list to display on the fragment
    ----------------------------------------------------*/
    private void ApplyAdapter(View view) {
        ArrayList<WeatherMarker> markers = MainActivity.mapMakers;
        // Create the adapter to convert the array to views
        WeatherStatsAdapter adapter = new WeatherStatsAdapter(getActivity(), markers);
        // Attach the adapter to a ListView
        ListView listView = view.findViewById(R.id.list);
        listView.setAdapter(adapter);
    } // End CreateAdapter
} // End Class
