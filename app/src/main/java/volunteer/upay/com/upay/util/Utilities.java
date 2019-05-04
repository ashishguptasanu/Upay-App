package volunteer.upay.com.upay.util;

import android.location.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by amanbansal on 12/05/18.
 */

public class Utilities {

    public final static String TOKEN = "d75542712c868c1690110db641ba01a";


    public static Map<String, String> getHeaderMap() {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Token", TOKEN);
        return headerMap;
    }
}
