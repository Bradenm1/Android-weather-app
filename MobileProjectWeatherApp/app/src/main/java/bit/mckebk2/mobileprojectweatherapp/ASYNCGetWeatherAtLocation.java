package bit.mckebk2.mobileprojectweatherapp;

import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*----------------------------------------------------
-- A AsyncTask used to get the weather for a location
----------------------------------------------------*/
public class ASYNCGetWeatherAtLocation extends AsyncTask<LatLng,Void,JSONObject> {

    // Constants
    private static final String FEELS_LIKE_F = "FeelsLikeF";
    private static final String FEELS_LIKE_C = "FeelsLikeC";
    private static final String CLOUDCOVER = "cloudcover";
    private static final String PRESSURE = "pressure";
    private static final String HUMIDITY = "humidity";
    private static final String PRECIP_MM = "precipMM";
    private static final String WINDDIR_16_POINT = "winddir16Point";
    private static final String WINDDIR_DEGREE = "winddirDegree";
    private static final String WINDSPEED_KMPH = "windspeedKmph";
    private static final String WINDSPEED_MILES = "windspeedMiles";
    private static final String VALUE = "value";
    private static final String WEATHER_DESC = "weatherDesc";
    private static final String WEATHER_ICON_URL = "weatherIconUrl";
    private static final String WEATHER_CODE = "weatherCode";
    private static final String TEMP_F = "temp_F";
    private static final String TEMP_C = "temp_C";
    private static final String OBSERVATION_TIME = "observation_time";
    private static final String CURRENT_CONDITION = "current_condition";
    private static final String DATA = "data";
    private static final String CLIMATE_AVERAGES = "ClimateAverages";

    // Fields
    private final GoogleMapsFragment activity;
    // Created instance of the marker
    private final MarkerOptions marker;

    /*----------------------------------------------------
    -- Constructor()
    ----------------------------------------------------*/
    public ASYNCGetWeatherAtLocation(GoogleMapsFragment a, MarkerOptions b) {
        this.activity = a;
        this.marker = b;
    } // End ASYNCGetWeatherAtLocation

    @Override
    protected JSONObject doInBackground(LatLng... latlngParams) {
        //Looper.prepare();
        try {
            String position = this.activity.getResources().getString(R.string.url_append_01) + latlngParams[0].latitude + ","
                    + latlngParams[0].longitude + this.activity.getResources().getString(R.string.append_url_02) 
                    + R.string.api_format +  this.activity.getResources().getString(R.string.append_url_03);
            // Create the API call
            String apiURL = this.activity.getResources().getString(R.string.api_link) + this.activity.getResources().getString((R.string.api_ley)) + position;
            // Convert URL string to URL Object
            URL URLObject = new URL(apiURL);
            // Create HttpURLConnection object via open connection command of URLObject
            HttpURLConnection connection = (HttpURLConnection) URLObject.openConnection();
            // Send the URL
            connection.connect();
            // If it dosen't return 200, you don't have data
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) return null;
            //Toast.makeText(activity, responseCode, Toast.LENGTH_LONG).show();
            // Get an InputSteam from the HttpURLConnection object and set up a bufferedReader
            InputStream is = connection.getInputStream();
            InputStreamReader ir = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(ir);
            // Read the input, may only be one line
            String responeString;
            StringBuffer stringBuilder = new StringBuffer();
            while ((responeString = br.readLine()) != null)
                stringBuilder = stringBuilder.append(responeString);
            br.close();
            // Return the string as JSON
            return new JSONObject(stringBuilder.toString());
            //return new JSONObject(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    } // End doInBackground

    protected void onPostExecute(JSONObject json) {
        // Check if any data is returned
        if (json != null) {
            if (CheckIfReturnsData(json)) {
                // Get the the JSON created and put it into the Weather Marker
                WeatherMarker wMarker = GetWeatherMarker(json);
                // Set the marker to the Weather marker
                wMarker.setMarker(marker);
                // Set the marker title to the description
                marker.title(wMarker.getWeatherDesc());
                // Add the marker to the map
                activity.addMarkerToMap(wMarker);
                // Add the Weather Marker to the array
                activity.AddMakerToList(wMarker);
                // Apply the line between points
                activity.drawLineLast();
            } else {
                // Users tries to place a marker in a area which has no stats
                Toast.makeText(activity.getActivity(), R.string.toast_error_cannot_place_marker, Toast.LENGTH_LONG).show();
            }
        } else {
            // API Json return had an error
            //Toast.makeText(activity.getActivity(), R.string.toast_error, Toast.LENGTH_LONG).show();
        }
    } // End onPostExecute

    /*----------------------------------------------------
    -- CheckIfReturnsData()
    -- Returns Arraylist of strings containing the given key from the json
    ----------------------------------------------------*/
    private Boolean CheckIfReturnsData(JSONObject json) {
        try {
            // Get object of events
            JSONObject data = json.getJSONObject(DATA);
            if (data.isNull(CLIMATE_AVERAGES)) return false;
        } catch (JSONException e) { e.printStackTrace(); }
        return true;
    } // End CheckIfReturnsData

    /*----------------------------------------------------
    -- GetWeatherMarker()
    -- Gets weather information for a a marker
    ----------------------------------------------------*/
    private WeatherMarker GetWeatherMarker(JSONObject json) {
        // Create a new Weather Marker object
        WeatherMarker wMarker = new WeatherMarker();
        try {
            JSONObject data = json.getJSONObject(DATA);
            JSONArray currentConditions = data.getJSONArray(CURRENT_CONDITION);
            JSONObject currentData = currentConditions.getJSONObject(0);
            // Get the data and set it in the new Weather Marker
            wMarker.setTime(currentData.getString(OBSERVATION_TIME));
            wMarker.setTempC(currentData.getInt(TEMP_C));
            wMarker.setTempF(currentData.getInt(TEMP_F));
            wMarker.setWeatherCode(currentData.getInt(WEATHER_CODE));
            wMarker.setWeatherIconUrl((currentData.getJSONArray(WEATHER_ICON_URL).getJSONObject(0).getString(VALUE)));
            wMarker.setWeatherDesc((currentData.getJSONArray(WEATHER_DESC).getJSONObject(0).getString(VALUE)));
            wMarker.setWindspeedMiles(currentData.getInt(WINDSPEED_MILES));
            wMarker.setWindspeedKmph(currentData.getInt(WINDSPEED_KMPH));
            wMarker.setWindspeedKmph(currentData.getInt(WINDDIR_DEGREE));
            wMarker.setWinddir16Point(currentData.getString(WINDDIR_16_POINT));
            wMarker.setPrecipMM(currentData.getDouble(PRECIP_MM));
            wMarker.setHumidity(currentData.getInt(HUMIDITY));
            wMarker.setPressurel(currentData.getInt(PRESSURE));
            wMarker.setCloudcover(currentData.getInt(CLOUDCOVER));
            wMarker.setFeelsLikeC(currentData.getInt(FEELS_LIKE_C));
            wMarker.setFeelsLikeF(currentData.getInt(FEELS_LIKE_F));
            // Return the new Weather marker object
            return wMarker;
        } catch (JSONException e) { e.printStackTrace(); }
        // If failed return null
        return wMarker;
    } // End GetWeatherMarker
} // End Class