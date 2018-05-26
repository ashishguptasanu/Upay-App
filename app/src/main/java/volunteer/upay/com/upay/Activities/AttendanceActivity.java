
package volunteer.upay.com.upay.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import volunteer.upay.com.upay.Models.Centers;
import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.fragment.AttendanceFragment;
import volunteer.upay.com.upay.fragment.CenterListFragment;
import volunteer.upay.com.upay.rest.RetrofitRequest;
import volunteer.upay.com.upay.util.Utilities;


public class AttendanceActivity extends LocationActivity implements CenterListFragment.OnCenterSelectListener {

    private static final double MAX_DISTANCE = 10000000; //1000km

    private Centers mSelectedCenter;
    private double lat;
    private double lon;
    private CenterListFragment mCenterFrag;
    private Button mPresentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
        mPresentButton = findViewById(R.id.presentButton);

        pushFragment(null, mCenterFrag = CenterListFragment.newInstance(), R.id.container, "TAG", false);
    }

    @Override
    protected void locationChanged(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        checkForDistance();
    }

    private void checkForDistance() {
        if (mSelectedCenter != null && lat != 0 && lon != 0) {
            double distance = Utilities.distance(lat, lon, mSelectedCenter.getLatitudeDouble(), mSelectedCenter.getLongitudeDouble());
            if (distance < MAX_DISTANCE) {
                mPresentButton.setVisibility(View.VISIBLE);
            } else {
                mPresentButton.setVisibility(View.GONE);
            }

            Toast.makeText(this, "Distance is = " + distance, Toast.LENGTH_SHORT).show();
        }
    }


    private void markPresent() {
        //todo Present API call here.
    }

    @Override
    public void onCenterSelect(Centers item) {
        mSelectedCenter = item;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(mCenterFrag);
        transaction.commit();

        checkForDistance();

//        pushFragment(null, AttendanceFragment.newInstance(item), R.id.container, "oCS", true);
    }

}
