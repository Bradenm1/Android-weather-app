package bit.mckebk2.mobileprojectweatherapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

/*----------------------------------------------------
-- A DialogFragment which is used to create new routes
----------------------------------------------------*/
public class AddRouteDialog extends DialogFragment {

    // Fields
    private View view;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //noinspection ConstantConditions
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_new_route, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.dialog_ok, new AddRouteDialog.positiveButtonClick())
                .setNegativeButton(R.string.dialog_cancel_button, new AddRouteDialog.negativeButtonClick());
        return builder.create();
    } // End onCreateDialog

    /*----------------------------------------------------
    -- positiveButtonClick()
    -- When user presses place marker
    ----------------------------------------------------*/
    public class positiveButtonClick implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            MainActivity.mapMakers = new ArrayList<>();
            EditText txt = view.findViewById(R.id.routeText);
            MainActivity.routeName = txt.getText().toString();
            GoogleMapsFragment.mMap.clear();
            // Hide dialog
            AddRouteDialog.this.getDialog().dismiss();
        } // End onClick
    } // End positiveButtonClick

    /*----------------------------------------------------
    -- negativeButtonClick()
    -- When user presses cancel
    ----------------------------------------------------*/
    public class negativeButtonClick implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            AddRouteDialog.this.getDialog().cancel();
        } // End onClick
    } // End negativeButtonClick
} // End Class