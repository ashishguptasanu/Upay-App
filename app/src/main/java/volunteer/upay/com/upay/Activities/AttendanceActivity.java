
package volunteer.upay.com.upay.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import volunteer.upay.com.upay.Models.Centers;
import volunteer.upay.com.upay.Models.GeneralResponseModel;
import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.fragment.CenterListFragment;
import volunteer.upay.com.upay.rest.RestCallback;
import volunteer.upay.com.upay.rest.RetrofitRequest;
import volunteer.upay.com.upay.util.Utilities;

import static volunteer.upay.com.upay.util.Utilities.getHeaderMap;


public class AttendanceActivity extends LocationActivity implements CenterListFragment.OnCenterSelectListener, RestCallback.RestCallbacks<GeneralResponseModel> {

    private static final double MAX_DISTANCE = 1000; //1km

    private Centers mSelectedCenter;
    private double lat;
    private double lon;
    private CenterListFragment mCenterFrag;
    private Button mPresentButton;
    private ProgressBar mProgressBar;
    private TextView mStatusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
        mPresentButton = findViewById(R.id.presentButton);
        mProgressBar = findViewById(R.id.progressBar);
        mStatusText = findViewById(R.id.status_text);

        setTitle("Attendance");

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
                mProgressBar.setVisibility(View.GONE);
                mPresentButton.setVisibility(View.VISIBLE);
                stopLocationUpdates();
            } else {
                mPresentButton.setVisibility(View.GONE);
                mStatusText.setText("Please go to center and mark your attendance");
            }
        }
    }


    public void presentButton(View view) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String loginId = sharedPreferences.getString("login_email", "");

        Map<String, String> headers = getHeaderMap();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("volunteer_id", loginId);
        fieldMap.put("attendance_status", "P");
        if (mSelectedCenter != null)
            fieldMap.put("center_id", mSelectedCenter.getCenter_id());
        fieldMap.put("timestmp", String.valueOf(System.currentTimeMillis()));

        Call<GeneralResponseModel> call = RetrofitRequest.markAttendance(headers, fieldMap);
        RestCallback<GeneralResponseModel> responseRestCallback = new RestCallback<>(this, call, this);
        responseRestCallback.executeRequest();
        showLoading();
    }

    private void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mPresentButton.setVisibility(View.GONE);
    }

    private void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
        mPresentButton.setVisibility(View.GONE);
        mStatusText.setVisibility(View.VISIBLE);
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

    private void attendenceMarked() {
        mStatusText.setText("Attendance Marked");
        hideLoading();
    }

    private void attendenceNotMarked() {
        mStatusText.setText("Some problem occurred, please try again in some time");
        hideLoading();
    }

    @Override
    public void onResponse(Call<GeneralResponseModel> call, Response<GeneralResponseModel> response) {
        if (response != null && response.body() != null && response.body().getResponse() != null
                && response.body().getResponse().getData() != null
                && !TextUtils.isEmpty(response.body().getResponse().getData().getType())
                && response.body().getResponse().getData().getType().equalsIgnoreCase("success")) {
            attendenceMarked();
        } else {
            attendenceNotMarked();
        }
    }


    @Override
    public void onFailure(Call<GeneralResponseModel> call, Throwable t) {
        attendenceNotMarked();

    }

}
