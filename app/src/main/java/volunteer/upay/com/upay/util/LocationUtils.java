package volunteer.upay.com.upay.util;

import android.location.Location;

import volunteer.upay.com.upay.BuildConfig;

public class LocationUtils {


    public static double getMaxDistanceInMeters() {
        if (BuildConfig.DEBUG) {
            return Long.MAX_VALUE;
        } else {
            return 1000;
        }
    }


    // distance in meters
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        Location startPoint = new Location("locationA");
        startPoint.setLatitude(lat1);
        startPoint.setLongitude(lon1);

        Location endPoint = new Location("locationB");
        endPoint.setLatitude(lat2);
        endPoint.setLongitude(lon2);

        return startPoint.distanceTo(endPoint);
    }
}
