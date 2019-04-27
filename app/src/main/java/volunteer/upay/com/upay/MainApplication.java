package volunteer.upay.com.upay;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by amanbansal on 18/06/18.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
