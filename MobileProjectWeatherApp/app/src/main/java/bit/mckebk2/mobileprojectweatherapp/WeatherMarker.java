package bit.mckebk2.mobileprojectweatherapp;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.MarkerOptions;

/*----------------------------------------------------
-- A class which holds weather marker information
----------------------------------------------------*/
public class WeatherMarker {

    // Fields
    private MarkerOptions marker;
    private Bitmap markerIcon;
    private String time;
    private String winddir16Point;
    private String weatherIconUrl;
    private String weatherDesc;
    private double precipMM;
    private int tempC;
    private int tempF;
    private int humidity;
    private int weatherCode;
    private int windspeedMiles;
    private int windspeedKmph;
    private int winddirDegree;
    private int visibility;
    private int pressurel;
    private int cloudcover;
    private int feelsLikeC;
    private int feelsLikeF;
    private int chanceofrain;
    private int chanceofremdry;
    private int chanceofwindyl;
    private int chanceofovercast;
    private int chanceofsunshine;
    private int chanceoffrost;
    private int chanceofhightemp;
    private int chanceoffog;
    private int chanceofsnow;
    private int chanceofthunder;

    /*----------------------------------------------------
    -- Constructor()
    ----------------------------------------------------*/
    public WeatherMarker() {}

    public Bitmap getMarkerIcon() {
        return markerIcon;
    }

    public void setMarkerIcon(Bitmap markerIcon) {
        this.markerIcon = markerIcon;
    }
    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public MarkerOptions getMarker() {
        return marker;
    }

    public void setMarker(MarkerOptions marker) {
        this.marker = marker;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setWinddir16Point(String winddir16Point) {
        this.winddir16Point = winddir16Point;
    }

    public void setWeatherIconUrl(String weatherIconUrl) {
        this.weatherIconUrl = weatherIconUrl;
    }

    public void setWeatherDesc(String weatherDesc) {this.weatherDesc = weatherDesc;}

    public void setPrecipMM(double precipMM) {
        this.precipMM = precipMM;
    }

    public void setTempC(int tempC) {
        this.tempC = tempC;
    }

    public void setTempF(int tempF) {
        this.tempF = tempF;
    }

    public void setWeatherCode(int weatherCode) {
        this.weatherCode = weatherCode;
    }

    public void setWindspeedMiles(int windspeedMiles) {
        this.windspeedMiles = windspeedMiles;
    }

    public void setWindspeedKmph(int windspeedKmph) {
        this.windspeedKmph = windspeedKmph;
    }

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public void setWinddirDegree(int winddirDegree) {
//        this.winddirDegree = winddirDegree;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public void setVisibility(int visibility) {
//        this.visibility = visibility;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

    public void setPressurel(int pressurel) {
        this.pressurel = pressurel;
    }

    public void setCloudcover(int cloudcover) {
        this.cloudcover = cloudcover;
    }

    public void setFeelsLikeC(int feelsLikeC) {
        this.feelsLikeC = feelsLikeC;
    }

    public void setFeelsLikeF(int feelsLikeF) {
        this.feelsLikeF = feelsLikeF;
    }

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public void setChanceofrain(int chanceofrain) {
//        this.chanceofrain = chanceofrain;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public void setChanceofremdry(int chanceofremdry) {
//        this.chanceofremdry = chanceofremdry;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public void setChanceofwindyl(int chanceofwindyl) {
//        this.chanceofwindyl = chanceofwindyl;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

    // --Commented out by Inspection (13/06/2018 3:57 AM):public void setChanceofovercast(int chanceofovercast) {this.chanceofovercast = chanceofovercast;}

    // --Commented out by Inspection (13/06/2018 3:57 AM):public void setChanceofsunshine(int chanceofsunshine) {this.chanceofsunshine = chanceofsunshine;}

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public void setChanceoffrost(int chanceoffrost) {
//        this.chanceoffrost = chanceoffrost;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

    // --Commented out by Inspection (13/06/2018 3:57 AM):public void setChanceofhightemp(int chanceofhightemp) {this.chanceofhightemp = chanceofhightemp;}

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public void setChanceoffog(int chanceoffog) {
//        this.chanceoffog = chanceoffog;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public void setChanceofsnow(int chanceofsnow) {
//        this.chanceofsnow = chanceofsnow;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public void setChanceofthunder(int chanceofthunder) {
//        this.chanceofthunder = chanceofthunder;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public String getTime() {
//        return time;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public String getWinddir16Point() {
//        return winddir16Point;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public double getPrecipMM() {
//        return precipMM;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

    public int getTempC() {
        return tempC;
    }

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public int getTempF() {
//        return tempF;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public int getWeatherCode() {
//        return weatherCode;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

    public int getWindspeedMiles() {
        return windspeedMiles;
    }

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public int getWindspeedKmph() {
//        return windspeedKmph;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public int getWinddirDegree() {
//        return winddirDegree;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public int getVisibility() {
//        return visibility;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public int getPressurel() {
//        return pressurel;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public int getCloudcover() {
//        return cloudcover;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public int getFeelsLikeC() {
//        return feelsLikeC;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public int getFeelsLikeF() {
//        return feelsLikeF;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public int getChanceofrain() {
//        return chanceofrain;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public int getChanceofremdry() {
//        return chanceofremdry;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public int getChanceofwindyl() {
//        return chanceofwindyl;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public int getChanceofovercast() {
//        return chanceofovercast;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public int getChanceofsunshine() {
//        return chanceofsunshine;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public int getChanceoffrost() {
//        return chanceoffrost;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public int getChanceofhightemp() {
//        return chanceofhightemp;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public int getChanceoffog() {
//        return chanceoffog;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public int getChanceofsnow() {
//        return chanceofsnow;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public int getChanceofthunder() {
//        return chanceofthunder;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)
} // End Class
