package bit.mckebk2.mobileprojectweatherapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/*----------------------------------------------------
-- A DialogFragment which is used to show help text
----------------------------------------------------*/
public class HelpDialog extends DialogFragment {

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //noinspection ConstantConditions
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_help, null);
        String description = getArguments().getString("description");
        ((TextView)view.findViewById(R.id.description)).setText(description);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.dialog_exit, new positiveButtonClick());
        return builder.create();
    } // End onCreateDialog

    /*----------------------------------------------------
    -- positiveButtonClick()
    -- When user presses place marker
    ----------------------------------------------------*/
    public class positiveButtonClick implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            // Hide dialog
            HelpDialog.this.getDialog().dismiss();
        } // End onClick
    } // End positiveButtonClick
} // End Class
