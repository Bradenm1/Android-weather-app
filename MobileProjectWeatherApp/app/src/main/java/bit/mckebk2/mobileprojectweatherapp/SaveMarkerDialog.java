package bit.mckebk2.mobileprojectweatherapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

/*----------------------------------------------------
-- A DialogFragment which is used to save current route
----------------------------------------------------*/
public class SaveMarkerDialog extends DialogFragment {

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //noinspection ConstantConditions
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_save_route, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.dialog_place_marker, new positiveButtonClick())
                .setNegativeButton(R.string.dialog_cancel_button, new negativeButtonClick());
        return builder.create();
    } // End onCreateDialog

    /*----------------------------------------------------
    -- positiveButtonClick()
    -- When user presses place marker
    ----------------------------------------------------*/
    public class positiveButtonClick implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            DatabaseConnectionSingleton.getInstance().writeNewRoute( MainActivity.mapMakers, MainActivity.routeName);
            Toast.makeText(getActivity(), R.string.toast_route_save, Toast.LENGTH_LONG).show();
            // Hide dialog
            SaveMarkerDialog.this.getDialog().dismiss();
        } // End onClick
    } // End positiveButtonClick

    /*----------------------------------------------------
    -- negativeButtonClick()
    -- When user presses cancel
    ----------------------------------------------------*/
    public class negativeButtonClick implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            SaveMarkerDialog.this.getDialog().cancel();
        } // End onClick
    } // End negativeButtonClick
} // End Class
