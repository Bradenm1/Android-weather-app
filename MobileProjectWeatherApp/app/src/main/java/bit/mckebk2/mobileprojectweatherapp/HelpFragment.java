package bit.mckebk2.mobileprojectweatherapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*----------------------------------------------------
-- A Fragment used to display help information
----------------------------------------------------*/
public class HelpFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        CreateOnClickListeners(view);
        // Inflate the layout for this fragment
        return view;
    } // End onCreateView

    /*----------------------------------------------------
    -- CreateOnClickListeners()
    -- Creates listeners for the fragment
    -- @param view View: the fragment view
    ----------------------------------------------------*/
    public void CreateOnClickListeners(View view) {
        (view.findViewById(R.id.buttonSave)).setOnClickListener(new SavingRoutesHelpButtonPress());
        (view.findViewById(R.id.buttonLoad)).setOnClickListener(new LoadingRoutesHelpButtonPress());
        (view.findViewById(R.id.buttonDelete)).setOnClickListener(new DeleteRoutesHelpButtonPress());
        (view.findViewById(R.id.buttonMarker)).setOnClickListener(new AddingMarkerHelpButtonPress());
        (view.findViewById(R.id.buttonCreate)).setOnClickListener(new CreateNewRouteHelpButtonPress());
    } // End CreateOnClickListeners

    /*----------------------------------------------------
    -- SavingRoutesHelpButtonPress()
    -- implements OnClickListener
    -- Shows a help dialog
    ----------------------------------------------------*/
    public class SavingRoutesHelpButtonPress implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            openMenu(getString(R.string.help_menu_saving_routes));
        } // End onClick
    } // End SavingRoutesHelpButtonPress

    /*----------------------------------------------------
    -- LoadingRoutesHelpButtonPress()
    -- implements OnClickListener
    -- Shows a help dialog
    ----------------------------------------------------*/
    public class LoadingRoutesHelpButtonPress implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            openMenu(getString(R.string.help_menu_loading_routes));
        } // End onClick
    } // End LoadingRoutesHelpButtonPress

    /*----------------------------------------------------
    -- DeleteRoutesHelpButtonPress()
    -- implements OnClickListener
    -- Shows a help dialog
    ----------------------------------------------------*/
    public class DeleteRoutesHelpButtonPress implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            openMenu(getString(R.string.help_menu_delete_routes));
        } // End onClick
    } // End DeleteRoutesHelpButtonPress

    /*----------------------------------------------------
    -- DeleteRoutesHelpButtonPress()
    -- implements OnClickListener
    -- Shows a help dialog
    ----------------------------------------------------*/
    public class AddingMarkerHelpButtonPress implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            openMenu(getString(R.string.help_menu_adding_marker));
        } // End onClick
    } // End AddingMarkerHelpButtonPress

    /*----------------------------------------------------
    -- DeleteRoutesHelpButtonPress()
    -- implements OnClickListener
    -- Shows a help dialog
    ----------------------------------------------------*/
    public class CreateNewRouteHelpButtonPress implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            openMenu(getString(R.string.help_menu_create_route));
        } // End onClick
    } // End CreateNewRouteHelpButtonPress

    /*----------------------------------------------------
    -- openMenu()
    -- Opens a dialog fragment given the description
    ----------------------------------------------------*/
    @SuppressWarnings("ConstantConditions")
    public void openMenu(String description) {
        Bundle args = new Bundle();
        args.putString("description", description);
        HelpDialog newFragment = new HelpDialog();
        newFragment.setArguments(args);
        newFragment.show(getFragmentManager(), "tag");
    } // End openMenu
} // End Class
