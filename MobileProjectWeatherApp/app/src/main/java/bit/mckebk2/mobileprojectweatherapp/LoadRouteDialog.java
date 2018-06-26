package bit.mckebk2.mobileprojectweatherapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/*----------------------------------------------------
-- A DialogFragment which is used to load, delete
-- saved routes
----------------------------------------------------*/
public class LoadRouteDialog extends DialogFragment {

    // The route the user has selected in the list
    public int routeIndex = -1;
    // Name of the selected route
    public String routeName = "";

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //noinspection ConstantConditions
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_load_route, null);
        ListView list = view.findViewById(R.id.list);
        // Get the route names
        ArrayList<String> routeNames = getRouteNames();
        // Get the adapter given the route names
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, routeNames);
        // Set the adapter
        list.setAdapter(adapter);
        // Set onSelect listener allowing users to select items
        list.setOnItemClickListener(new listItemSelect());
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.dialog_load_route_button, new positiveButtonClick())
                .setNeutralButton(R.string.dialog_delete_route_button, new neutralButtonClick())
                .setNegativeButton(R.string.dialog_cancel_button, new negativeButtonClick());
        return builder.create();
    } // End onCreateDialog

    /*----------------------------------------------------
    -- getRouteNames()
    -- Gets all the routes the user has saved
    -- @return routeNames ArrayList<String>: All the route names the user has stored
    ----------------------------------------------------*/
    private ArrayList<String> getRouteNames() {
        ArrayList<String> routeNames = new ArrayList<>();
        // Iterate through each route the user has saved, and get the name
        for (Route route : DatabaseConnectionSingleton.getInstance().getAllRoutes()) {
            routeNames.add(route.getRouteName());
        }
        return routeNames;
    }

    /*----------------------------------------------------
    -- listItemSelect()
    -- implements OnItemClickListener
    -- Called when user selects an item in a list
    ----------------------------------------------------*/
    public class listItemSelect implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            // Set all the background colors of the items in the list to transparent
            for(int a = 0; a < adapterView.getChildCount(); a++) {
                adapterView.getChildAt(a).setBackgroundColor(Color.TRANSPARENT);
            }
            // Set the colour of the selected item
            view.setBackgroundColor(Color.LTGRAY);
            routeName = (String) adapterView.getItemAtPosition(i);
            // Get the selected items index
            routeIndex = i;
        }
    }

    /*----------------------------------------------------
    -- positiveButtonClick()
    -- When user wants to load a selected route
    ----------------------------------------------------*/
    public class positiveButtonClick implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            GoogleMapsFragment activity = (GoogleMapsFragment)getTargetFragment();
            if (routeIndex != -1) {
                assert activity != null;
                activity.LoadRoutes(routeIndex);
                LoadRouteDialog.this.getDialog().dismiss();
            } else {
                Toast.makeText(getActivity(), R.string.toast_error_no_route_selected, Toast.LENGTH_LONG).show();
            }
        } // End onClick
    } // End positiveButtonClick

    /*----------------------------------------------------
    -- neutralButtonClick()
    -- When user wants to delete a route
    ----------------------------------------------------*/
    public class neutralButtonClick implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (routeIndex != -1) {
                DatabaseConnectionSingleton.getInstance().deleteSavedRoute(routeName);
                Toast.makeText(getActivity(), R.string.toast_route_delete, Toast.LENGTH_LONG).show();
                LoadRouteDialog.this.getDialog().dismiss();
            } else {
                Toast.makeText(getActivity(), R.string.toast_error_no_route_selected, Toast.LENGTH_LONG).show();
            }
        } // End onClick
    } // End neutralButtonClick

    /*----------------------------------------------------
    -- negativeButtonClick()
    -- When user presses cancel
    ----------------------------------------------------*/
    public class negativeButtonClick implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            LoadRouteDialog.this.getDialog().cancel();
        } // End onClick
    } // End negativeButtonClick
} // End Class
