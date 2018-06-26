package bit.mckebk2.mobileprojectweatherapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

/*----------------------------------------------------
-- A class which holds the list adapter
-- for the stats fragment
----------------------------------------------------*/
public class WeatherStatsAdapter extends ArrayAdapter<WeatherMarker> {

    // Constants
    private static final String D = "%d";
    private static final String D_KMPH = "%d Kmph";
    private static final String NO_DESCRIPTION = "No Description";

    public WeatherStatsAdapter(Context context, List<WeatherMarker> objects) {super(context, 0, objects);}

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_item, parent, false);
        }

        // Get the data item for this position
        WeatherMarker weatherMarker = getItem(position);

        // Geeting view elements
        ImageView image = convertView.findViewById(R.id.imageView);
        TextView weatherdec = convertView.findViewById(R.id.weatherDesc);
        TextView temp = convertView.findViewById(R.id.temparture);
        TextView windSpeed = convertView.findViewById(R.id.windSpeed);
        TextView humidity = convertView.findViewById(R.id.humidity);

        // Applying Stats
        image.setImageBitmap(weatherMarker != null ? weatherMarker.getMarkerIcon() : null);
        weatherdec.setText(weatherMarker != null ? weatherMarker.getWeatherDesc() : NO_DESCRIPTION);

        // Send all output to the Appendable object sb
        temp.setText(String.format(Locale.US, D, weatherMarker != null ? weatherMarker.getTempC() : 0));
        windSpeed.setText(String.format(Locale.US, D_KMPH, weatherMarker != null ? weatherMarker.getWindspeedMiles() : 0));
        humidity.setText(String.format(Locale.US, D, weatherMarker != null ? weatherMarker.getHumidity() : 0));

        // Return the completed view to render on screen
        return convertView;
    } // End getView
} // End Class
