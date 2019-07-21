
package volunteer.upay.com.upay.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;


import java.util.Collections;
import java.util.List;

import bolts.Continuation;
import bolts.Task;
import retrofit2.Call;
import retrofit2.Response;
import volunteer.upay.com.upay.Adapters.VolunteerLogListAdapter;
import volunteer.upay.com.upay.Models.Centers;
import volunteer.upay.com.upay.Models.GeneralResponseModel;
import volunteer.upay.com.upay.Models.VolunteerLogModel;
import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.localDb.VolunteerAttendanceRepository;
import volunteer.upay.com.upay.rest.RestCallback;
import volunteer.upay.com.upay.rest.RetrofitRequest;
import volunteer.upay.com.upay.util.AccessLevel;
import volunteer.upay.com.upay.util.LocationUtils;
import volunteer.upay.com.upay.util.Utilities;

import static volunteer.upay.com.upay.util.FetchUtils.getCenterNameFromId;
import static volunteer.upay.com.upay.util.Utilities.getHeaderMap;


public class VolunteerLogHistoryActivity extends LocationActivity implements RestCallback.RestCallbacks<GeneralResponseModel>, SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefresh;

    private double currentLat;
    private double currentLong;
    private String centerId;
    private double centerLat;
    private double centerLong;
    private VolunteerLogListAdapter mAdapter;
    private double lastLoggedDistance;
    private AccessLevel accessLevel;
    private VolunteerAttendanceRepository volunteerLogRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        volunteerLogRepository = new VolunteerAttendanceRepository(this);
        setContentView(R.layout.activity_attendence);
        setAdminAccess();
        FloatingActionButton fab = findViewById(R.id.fabButton);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);
        RecyclerView mAttendanceList = findViewById(R.id.attendance_list);
        mAttendanceList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mAttendanceList.addItemDecoration(itemDecor);
        mAttendanceList.setAdapter(mAdapter = new VolunteerLogListAdapter(accessLevel));
        setDefaults();
        fetchVolunteerLogHistory();
        if (accessLevel != AccessLevel.USER)
            setTitle("Vollunteer Attendance");
        else
            setTitle("My Attendance");

        fetchCenterName();

    }

    private void fetchCenterName() {
        if (TextUtils.isEmpty(centerId)) {
            return;
        }
        getCenterNameFromId(this, centerId).onSuccess(new Continuation<Centers, Object>() {
            @Override
            public Object then(Task<Centers> task) throws Exception {
                setTitle(task.getResult().getCenter_name() + " Attendance");
                return null;
            }
        });
    }

    private void setAdminAccess() {
        accessLevel = Utilities.getAccessLevel(this);
    }

    @Override
    public void onRefresh() {
        fetchVolunteerLogHistory();
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
        Call<GeneralResponseModel> call;
        if (accessLevel == AccessLevel.ADMIN || accessLevel == AccessLevel.SUPER_ADMIN) {
            call = RetrofitRequest.getAllVolunteersDetails(getHeaderMap(), centerId);
        } else {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String volunteerId = sharedPreferences.getString("login_email", "");
            call = RetrofitRequest.getVolunteersDetails(getHeaderMap(), volunteerId);
        }
        RestCallback<GeneralResponseModel> responseRestCallback = new RestCallback<>(this, call, this);
        responseRestCallback.executeRequest();
    }


    public void addLog(View view) {
        if (lastLoggedDistance < LocationUtils.getMaxDistanceInMeters() || accessLevel == AccessLevel.SUPER_ADMIN) {
            VolunteerLogFormActivity.open(this, centerId);
        } else {
            showToast("Please to go center for logging");
        }
    }


    @Override
    public void onResponse(Call<GeneralResponseModel> call, Response<GeneralResponseModel> response) {
        if (response != null && response.body() != null && response.body().getResponse() != null
                && response.body().getResponse().getData() != null
                && !TextUtils.isEmpty(response.body().getResponse().getData().getType())
                && response.body().getResponse().getData().getType().equalsIgnoreCase("success")) {
            List<VolunteerLogModel> volunteerLogModelList = response.body().getResponse().getData().getAttendance();
            Collections.sort(volunteerLogModelList);
            addToLocalDb(volunteerLogModelList);
            mAdapter.swapItems(volunteerLogModelList);
        } else {
            showToast("Some problem in fetching");
        }
        swipeRefresh.setRefreshing(false);
    }

    private void addToLocalDb(@NonNull List<VolunteerLogModel> volunteerLogModelList) {
        for (VolunteerLogModel volunteerLogModel : volunteerLogModelList) {
            volunteerLogRepository.insertTask(volunteerLogModel);
        }
    }


    @Override
    public void onFailure(Call<GeneralResponseModel> call, Throwable t) {
        showToast("Some problem in fetching, Please try again after some time");
        swipeRefresh.setRefreshing(false);
    }

}
