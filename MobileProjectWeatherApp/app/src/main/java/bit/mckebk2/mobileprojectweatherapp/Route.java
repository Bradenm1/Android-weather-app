package bit.mckebk2.mobileprojectweatherapp;

import java.util.List;

/*----------------------------------------------------
-- Class model for Firebase which saves routes
----------------------------------------------------*/
public class Route {

    // Fields
    private String routeName;
    private List<Double> latitudes;
    private List<Double> longitudes;

    /*----------------------------------------------------
    -- Empty Constructor for Firebase
    ----------------------------------------------------*/
    public Route() { }

    /*----------------------------------------------------
    -- Constructor
    ----------------------------------------------------*/
    public Route(String routeName, List<Double> latitudes, List<Double> longitudes) {

        this.routeName = routeName;
        this.latitudes = latitudes;
        this.longitudes = longitudes;
    }

    /*----------------------------------------------------
    -- Get and sets
    ----------------------------------------------------*/
    public String getRouteName() {
        return routeName;
    }

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public void setRouteName(String routeName) {
//        this.routeName = routeName;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

    public List<Double> getLatitudes() {
        return latitudes;
    }

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public void setLatitudes(List<Double> latitudes) {
//        this.latitudes = latitudes;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

    public List<Double> getLongitudes() {
        return longitudes;
    }

// --Commented out by Inspection START (13/06/2018 3:57 AM):
//    public void setLongitudes(List<Double> longitudes) {
//        this.longitudes = longitudes;
//    }
// --Commented out by Inspection STOP (13/06/2018 3:57 AM)

}
