
package volunteer.upay.com.upay.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;


import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import volunteer.upay.com.upay.Adapters.VolunteerLogListAdapter;
import volunteer.upay.com.upay.Models.GeneralResponseModel;
import volunteer.upay.com.upay.Models.VolunteerLogModel;
import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.rest.RestCallback;
import volunteer.upay.com.upay.rest.RetrofitRequest;
import volunteer.upay.com.upay.util.LocationUtils;


import static volunteer.upay.com.upay.util.Utilities.getHeaderMap;


public class VolunteerLogHistoryActivity extends LocationActivity implements RestCallback.RestCallbacks<GeneralResponseModel> {


    private double currentLat;
    private double currentLong;
    private String centerId;
    private double centerLat;
    private double centerLong;
    private FloatingActionButton fab;
    private RecyclerView mAttendanceList;
    private VolunteerLogListAdapter mAdapter;
    private double lastLoggedDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);

        fab = findViewById(R.id.fabButton);
        mAttendanceList = findViewById(R.id.attendance_list);
        mAttendanceList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAttendanceList.setAdapter(mAdapter = new VolunteerLogListAdapter());
        fetchVolunteerLogHistory();
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
            lastLoggedDistance = LocationUtils.distance(currentLat, currentLong, centerLat, centerLong);
            if (lastLoggedDistance < LocationUtils.getMaxDistanceInMeters()) {
                stopLocationUpdates();
            }
        }
    }


    public void fetchVolunteerLogHistory() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String volunteerId = sharedPreferences.getString("login_email", "");
        Call<GeneralResponseModel> call = RetrofitRequest.getVolunteersDetails(getHeaderMap(), volunteerId);
        RestCallback<GeneralResponseModel> responseRestCallback = new RestCallback<>(this, call, this);
        responseRestCallback.executeRequest();
    }


    public void addLog(View view) {
        if (lastLoggedDistance > LocationUtils.getMaxDistanceInMeters()) {
            showToast("Please to go center for logging");
        } else {
            VolunteerLogFormActivity.open(this, centerId);
        }
    }


    @Override
    public void onResponse(Call<GeneralResponseModel> call, Response<GeneralResponseModel> response) {
        if (response != null && response.body() != null && response.body().getResponse() != null
                && response.body().getResponse().getData() != null
                && !TextUtils.isEmpty(response.body().getResponse().getData().getType())
                && response.body().getResponse().getData().getType().equalsIgnoreCase("success")) {
            List<VolunteerLogModel> volunteerLogModelList = response.body().getResponse().getData().getAttendance();
            mAdapter.swapItems(volunteerLogModelList);
        }
    }


    @Override
    public void onFailure(Call<GeneralResponseModel> call, Throwable t) {
        showToast("Some problem in fetching");
    }

}
