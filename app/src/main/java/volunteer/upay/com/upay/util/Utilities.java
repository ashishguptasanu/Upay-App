package volunteer.upay.com.upay.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    public static AccessLevel getAccessLevel(@NonNull Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String volunteerAccess = sharedPreferences.getString("admin_access", "");
        switch (volunteerAccess) {
            case "3":
                return AccessLevel.SUPER_ADMIN;
            case "2":
                return AccessLevel.ADMIN;
            default:
                return AccessLevel.USER;

        }
    }
}
