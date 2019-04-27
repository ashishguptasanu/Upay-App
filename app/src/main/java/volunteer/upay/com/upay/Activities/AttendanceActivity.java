
package volunteer.upay.com.upay.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import volunteer.upay.com.upay.Models.GeneralResponseModel;
import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.rest.RestCallback;
import volunteer.upay.com.upay.rest.RetrofitRequest;
import volunteer.upay.com.upay.util.Utilities;

import static volunteer.upay.com.upay.util.Utilities.getHeaderMap;


public class AttendanceActivity extends LocationActivity implements RestCallback.RestCallbacks<GeneralResponseModel> {

    private static final double MAX_DISTANCE = Long.MAX_VALUE; //1km

    private double currentLat;
    private double currentLong;
    private Button mPresentButton;
    private ProgressBar mProgressBar;
    private TextView mStatusText;
    private String centerId;
    private double centerLat;
    private double centerLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
        mPresentButton = findViewById(R.id.presentButton);
        mProgressBar = findViewById(R.id.progressBar);
        mStatusText = findViewById(R.id.status_text);

        setDefaults();

        setTitle("Attendance");

    }

    private void setDefaults() {
        Intent intent = getIntent();
        centerId = intent.getStringExtra("center_id");
        centerLat = getDoubleExtra("lat", 0d);
        centerLong = intent.getDoubleExtra("long", 0d);
    }

    private double getDoubleExtra(String key, double def) {
        try {
            return Double.parseDouble(getIntent().getStringExtra(key));
        } catch (NumberFormatException e) {
            return def;
        }
    }

    @Override
    protected void locationChanged(double lat, double lon) {
        this.currentLat = lat;
        this.currentLong = lon;
        checkForDistance();
    }

    private void checkForDistance() {
        if (currentLat != 0 && currentLong != 0 && centerLong != 0 && centerLat != 0) {
            double distance = Utilities.distance(currentLat, currentLong, centerLat, centerLong);
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
        if (centerId != null)
            fieldMap.put("center_id", centerId);
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


    private void attendanceMarked() {
        mStatusText.setText("Attendance Marked");
        StudentAttendanceActivity.open(this, centerId);

        hideLoading();
    }

    private void attendanceNotMarked() {
        mStatusText.setText("Some problem occurred, please try again in some time");
        hideLoading();
    }

    @Override
    public void onResponse(Call<GeneralResponseModel> call, Response<GeneralResponseModel> response) {
        if (response != null && response.body() != null && response.body().getResponse() != null
                && response.body().getResponse().getData() != null
                && !TextUtils.isEmpty(response.body().getResponse().getData().getType())
                && response.body().getResponse().getData().getType().equalsIgnoreCase("success")) {
            attendanceMarked();
        } else {
            attendanceNotMarked();
        }
    }


    @Override
    public void onFailure(Call<GeneralResponseModel> call, Throwable t) {
        attendanceNotMarked();

    }

}
