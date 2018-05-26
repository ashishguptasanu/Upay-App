package volunteer.upay.com.upay.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by amanbansal on 11/05/18.
 */

public class BaseActivity extends AppCompatActivity {

    protected void pushFragment(Bundle bundle, Fragment destinationFragment,
                                int containerId, String tag, boolean addToBackstack) {
        if (destinationFragment == null) {
            throw new IllegalArgumentException("Destination Fragment is not defined.");
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (bundle != null) {
            destinationFragment.setArguments(bundle);
        }
        ft.replace(containerId, destinationFragment, tag);
        if (addToBackstack) {
            ft.addToBackStack(tag);
        }
        ft.commit();
    }


}
