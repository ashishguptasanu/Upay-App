package volunteer.upay.com.upay.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import volunteer.upay.com.upay.Models.GeneralResponseModel;
import volunteer.upay.com.upay.Models.VolunteerLogModel;
import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.rest.RestCallback;
import volunteer.upay.com.upay.rest.RetrofitRequest;

import static volunteer.upay.com.upay.util.Utilities.getHeaderMap;

public class VolunteerLogFormActivity extends BaseActivity implements RestCallback.RestCallbacks<GeneralResponseModel> {

    private String mCenterId;
    private EditText subject, thought, work_don, in_time, out_time, class_taught, no_of_students;
    private Button submit_button;
    private ProgressBar progressBar;

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
        initViews();
    }

    private void initViews() {
        subject = findViewById(R.id.subject);
        thought = findViewById(R.id.thought);
        work_don = findViewById(R.id.work_don);
        in_time = findViewById(R.id.in_time);
        out_time = findViewById(R.id.out_time);
        class_taught = findViewById(R.id.class_taught);
        progressBar = findViewById(R.id.progressBar);
        submit_button = findViewById(R.id.submit_button);
        no_of_students = findViewById(R.id.no_of_students);
    }

    public void addLogClicked(View View) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String loginId = sharedPreferences.getString("login_email", "");

        String subjectValue = subject.getText().toString();
        String thoughtValue = thought.getText().toString();
        String workDone = work_don.getText().toString();
        String inTime = in_time.getText().toString();
        String outTime = out_time.getText().toString();
        String classTaught = class_taught.getText().toString();
        String noOfStudents = no_of_students.getText().toString();
        VolunteerLogModel volunteerLogModel = new VolunteerLogModel();
        volunteerLogModel.setAttendance_status("P");
        volunteerLogModel.setVolunteer_id(loginId);
        volunteerLogModel.setCenter_id(mCenterId);
        volunteerLogModel.setSubject(subjectValue);
        volunteerLogModel.setThought_of_day(thoughtValue);
        volunteerLogModel.setWork_done(workDone);
        volunteerLogModel.setIn_time(inTime);
        volunteerLogModel.setOut_time(outTime);
        volunteerLogModel.setClass_taught(classTaught);
        volunteerLogModel.setNo_of_students(noOfStudents);
        String error = validate(volunteerLogModel);
        if (TextUtils.isEmpty(error)) {
            submitAttendance(volunteerLogModel);
        } else {
            showToast(error);
        }
    }

    private String validate(@NonNull VolunteerLogModel volunteerLogModel) {
        if (TextUtils.isEmpty(volunteerLogModel.getSubject())) {
            return "Subject cannot be empty";
        } else if (TextUtils.isEmpty(volunteerLogModel.getNo_of_students())) {
            return "Number of students cannot be empty";
        } else if (TextUtils.isEmpty(volunteerLogModel.getIn_time())) {
            return "In time cannot be empty";
        } else if (TextUtils.isEmpty(volunteerLogModel.getOut_time())) {
            return "Out time cannot be empty";
        }
        return null;
    }


    private void stopLoader() {
        submit_button.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }


    private void showLoader() {
        submit_button.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }


    private void submitAttendance(VolunteerLogModel volunteerLogModel) {
        showLoader();
        Map<String, String> headers = getHeaderMap();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("volunteer_id", volunteerLogModel.getVolunteer_id());
        fieldMap.put("attendance_status", volunteerLogModel.getAttendance_status());
        fieldMap.put("in_time", volunteerLogModel.getIn_time());
        fieldMap.put("out_time", volunteerLogModel.getOut_time());
        fieldMap.put("class_taught", volunteerLogModel.getClass_taught());
        fieldMap.put("subject", volunteerLogModel.getSubject());
        fieldMap.put("work_done", volunteerLogModel.getWork_done());
        fieldMap.put("thought_of_day", volunteerLogModel.getThought_of_day());
        fieldMap.put("no_of_students", volunteerLogModel.getNo_of_students());
        if (volunteerLogModel.getCenter_id() != null)
            fieldMap.put("center_id", volunteerLogModel.getCenter_id());
        fieldMap.put("timestmp", String.valueOf(System.currentTimeMillis()));

        Call<GeneralResponseModel> call = RetrofitRequest.markAttendance(headers, fieldMap);
        RestCallback<GeneralResponseModel> responseRestCallback = new RestCallback<>(this, call, this);
        responseRestCallback.executeRequest();
    }


    @Override
    public void onResponse(Call<GeneralResponseModel> call, Response<GeneralResponseModel> response) {
        if (response != null && response.body() != null && response.body().getResponse() != null
                && response.body().getResponse().getData() != null
                && !TextUtils.isEmpty(response.body().getResponse().getData().getType())
                && response.body().getResponse().getData().getType().equalsIgnoreCase("success")) {
            showToast("Logged successfully");
            finish();
        }
        stopLoader();
    }


    @Override
    public void onFailure(Call<GeneralResponseModel> call, Throwable t) {
        showToast("Some problem occurred, Please try again later");
        stopLoader();
    }
}
