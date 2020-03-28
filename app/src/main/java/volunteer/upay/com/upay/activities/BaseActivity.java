package volunteer.upay.com.upay.activities;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

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


    public void showToast(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
