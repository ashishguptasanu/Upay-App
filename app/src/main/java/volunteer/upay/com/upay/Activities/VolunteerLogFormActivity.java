package volunteer.upay.com.upay.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import volunteer.upay.com.upay.Models.GeneralResponseModel;
import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.rest.RestCallback;
import volunteer.upay.com.upay.rest.RetrofitRequest;

import static volunteer.upay.com.upay.util.Utilities.getHeaderMap;

public class VolunteerLogFormActivity extends BaseActivity implements RestCallback.RestCallbacks<GeneralResponseModel> {

    private String mCenterId;

    public static void open(@NonNull Context context, @NonNull String centerId) {
        Intent intent = new Intent(context, VolunteerLogFormActivity.class);
        intent.putExtra("center_id", centerId);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_volunteer_log_form);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("center_id")) {
            mCenterId = bundle.getString("center_id");
        }

    }


    private void submitAttendance() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String loginId = sharedPreferences.getString("login_email", "");

        Map<String, String> headers = getHeaderMap();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("volunteer_id", loginId);
        fieldMap.put("attendance_status", "P");
        if (mCenterId != null)
            fieldMap.put("center_id", mCenterId);
        fieldMap.put("timestmp", String.valueOf(System.currentTimeMillis()));

        Call<GeneralResponseModel> call = RetrofitRequest.markAttendance(headers, fieldMap);
        RestCallback<GeneralResponseModel> responseRestCallback = new RestCallback<>(this, call, this);
        responseRestCallback.executeRequest();
    }

    @Override
    public void onResponse(Call<GeneralResponseModel> call, Response<GeneralResponseModel> response) {

    }

    @Override
    public void onFailure(Call<GeneralResponseModel> call, Throwable t) {

    }
}
