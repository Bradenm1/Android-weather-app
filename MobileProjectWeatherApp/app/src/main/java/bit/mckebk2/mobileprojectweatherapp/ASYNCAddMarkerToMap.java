package bit.mckebk2.mobileprojectweatherapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import java.net.HttpURLConnection;
import java.net.URL;

/*----------------------------------------------------
-- A AsyncTask used to get the marker icon and
-- add it to the google map
----------------------------------------------------*/
public class ASYNCAddMarkerToMap extends AsyncTask<String,Void,Bitmap> {

    // Fields
    private final GoogleMapsFragment activity;
    private final WeatherMarker weatherMarker;

    /*----------------------------------------------------
    -- Constructor()
    ----------------------------------------------------*/
    public ASYNCAddMarkerToMap(GoogleMapsFragment a, WeatherMarker b) {
        this.activity = a;
        this.weatherMarker = b;
    } // End ASYNCAddMarkerToMap

    @Override
    protected Bitmap doInBackground(String... args) {
        try {
            // Convert URL string to URL Object
            URL URLObject = new URL(args[0]);
            // Create HttpURLConnection object via open connection command of URLObject
            HttpURLConnection connection = (HttpURLConnection) URLObject.openConnection();
            // Send the URL
            connection.connect();
            // If it dosen't return 200, you don't have data
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) return null;
            // BitmapDescriptorFactory.fromBitmap(
            return BitmapFactory.decodeStream(connection.getInputStream());

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity.getActivity(), R.string.toast_error_adding_marker, Toast.LENGTH_LONG).show();
        }
        return null;
    } // End doInBackground

    protected void onPostExecute(Bitmap bitmapImage) {
        // Set bitmap image
        weatherMarker.setMarkerIcon(bitmapImage);
        // Get marker
        MarkerOptions marker = weatherMarker.getMarker();
        // Set marker image
        marker.icon(BitmapDescriptorFactory.fromBitmap(bitmapImage));
        // Add marker to map
        activity.AddMarkerToMap(marker);
    } // End onPostExecute
} // End Class