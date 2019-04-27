package volunteer.upay.com.upay.util;

import android.location.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by amanbansal on 12/05/18.
 */

public class Utilities {

    public final static String TOKEN = "d75542712c868c1690110db641ba01a";

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

    public static Map<String, String> getHeaderMap() {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Token", TOKEN);
        return headerMap;
    }
}
