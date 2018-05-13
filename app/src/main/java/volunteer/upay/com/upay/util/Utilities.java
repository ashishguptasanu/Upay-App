package volunteer.upay.com.upay.util;

import android.location.Location;

/**
 * Created by amanbansal on 12/05/18.
 */

public class Utilities {

    // distance in meters
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        Location startPoint = new Location("locationA");
        startPoint.setLatitude(lat1);
        startPoint.setLongitude(lon1);

        Location endPoint = new Location("locationA");
        endPoint.setLatitude(lat2);
        endPoint.setLongitude(lon2);

        return startPoint.distanceTo(endPoint);
    }

}
