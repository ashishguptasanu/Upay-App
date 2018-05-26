package volunteer.upay.com.upay.manager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

/**
 * Created by aman on 27/8/17.
 */

public class PermissionManager {




    public static boolean checkForPermission(Context context, String permission) {
        int permissionCheck = ContextCompat.checkSelfPermission(context, permission);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    public static void askPermission(Activity context, String permission, int reqCode) {
        if (!TextUtils.isEmpty(permission) && !checkForPermission(context, permission)) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                //todo Explain why you need this.
            }
            ActivityCompat.requestPermissions(context, new String[]{permission}, reqCode);
        }
    }

}
