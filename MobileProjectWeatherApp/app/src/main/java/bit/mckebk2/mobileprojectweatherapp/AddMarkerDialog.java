package bit.mckebk2.mobileprojectweatherapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

/*----------------------------------------------------
-- A DialogFragment which is used to notify the user
-- if they wish to place a marker
----------------------------------------------------*/
public class AddMarkerDialog extends DialogFragment {

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //noinspection ConstantConditions
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_marker, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.dialog_ok, new AddMarkerDialog.positiveButtonClick())
                .setNegativeButton(R.string.dialog_cancel_button, new AddMarkerDialog.negativeButtonClick());
        return builder.create();
    } // End onCreateDialog

    /*----------------------------------------------------
    -- positiveButtonClick()
    -- When user presses place marker
    ----------------------------------------------------*/
    public class positiveButtonClick implements DialogInterface.OnClickListener {

        public static final String LONG = "long";
        public static final String LAT = "lat";

        @SuppressWarnings("ConstantConditions")
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            GoogleMapsFragment activity = (GoogleMapsFragment)getTargetFragment();
            // Get text given in short description
            double lat = getArguments().getDouble(LAT);
            double lng = getArguments().getDouble(LONG);
            activity.CreateMaker(new LatLng(lat, lng));
            // Hide dialog
            AddMarkerDialog.this.getDialog().dismiss();
        } // End onClick
    } // End positiveButtonClick

    /*----------------------------------------------------
    -- negativeButtonClick()
    -- When user presses cancel
    ----------------------------------------------------*/
    public class negativeButtonClick implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            AddMarkerDialog.this.getDialog().cancel();
        } // End onClick
    } // End negativeButtonClick
} // End Class