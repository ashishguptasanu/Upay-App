package volunteer.upay.com.upay.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import volunteer.upay.com.upay.adapters.AttendanceAdapter;
import volunteer.upay.com.upay.models.Student;
import volunteer.upay.com.upay.R;

public class StudentAttendanceActivity extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();
    List<Student> studentList = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    AttendanceAdapter studentsAdapter;
    SharedPreferences sharedPreferences;
    FloatingActionButton fabSubmitAttendance;


    public static void open(@NonNull Context context, String centerId) {
        Intent intent = new Intent(context, StudentAttendanceActivity.class);
        intent.putExtra("center_id", centerId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String centerId = getIntent().getStringExtra("center_id");
        if (TextUtils.isEmpty(centerId)) {
            getStudentsDetails("");
        } else {
            getStudentsDetails(centerId);
        }
        fabSubmitAttendance = findViewById(R.id.fab_submit_attendance);
        initializeFab();

    }

    private void getStudentsDetails(String center_id) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("center_id", center_id)
                .build();
        Request request = new Request.Builder().url(getResources().getString(R.string.base_url) + "/get_students_details.php").addHeader("Token", getResources().getString(R.string.token)).post(requestBody).build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
                         @Override
                         public void onFailure(okhttp3.Call call, IOException e) {
                             System.out.println("Registration Error" + e.getMessage());
                         }

                         @Override
                         public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                             String resp = response.body().string();
                             Log.d("resp", resp);

                             if (response.isSuccessful()) {
                                 JSONObject obj = null;
                                 try {
                                     obj = new JSONObject(resp);
                                     JSONObject obj_response = obj.getJSONObject("Response");
                                     final JSONObject obj_status = obj_response.getJSONObject("status");
                                     final String msgFinal = obj_status.getString("type");
                                     if (Objects.equals(msgFinal, "Success")) {
                                         final JSONObject obj_data = obj_response.getJSONObject("data");
                                         JSONArray center_array = obj_data.getJSONArray("students");
                                         for (int i = 0; i < center_array.length(); i++) {
                                             JSONObject centerObject = center_array.getJSONObject(i);
                                             String id = centerObject.getString("id");
                                             String student_name = centerObject.getString("student_name");
                                             String parent_name = centerObject.getString("parent_name");
                                             String age = centerObject.getString("age");
                                             String clss = centerObject.getString("class");
                                             String school = centerObject.getString("school");
                                             String center_id = centerObject.getString("center_id");
                                             String zone_id = centerObject.getString("zone_id");
                                             String center_name = centerObject.getString("center_name");
                                             String zone_name = centerObject.getString("zone_name");
                                             String photo_url = centerObject.getString("photo_url");
                                             String comments = centerObject.getString("comments");
                                             Student student = new Student(id, student_name, parent_name, age, clss, school, center_name, center_id, zone_name, zone_id, photo_url, comments, false);
                                             studentList.add(student);
                                         }
                                         runOnUiThread(new Runnable() {
                                             @Override
                                             public void run() {
                                                 initViews();
                                             }
                                         });
                                     }
                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }
                             }
                         }
                     }
        );
    }

    private void initializeFab() {

        fabSubmitAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isItemSelected = false;
                JSONObject jsonObject = new JSONObject();
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(StudentAttendanceActivity.this);
                try {
                    jsonObject.put("added_by", sharedPreferences.getString("login_email", ""));
                    jsonObject.put("center_id", String.valueOf(sharedPreferences.getInt("center_id", 0)));
                    JSONArray attendanceArray = new JSONArray();

                    for (int i = 0; i < studentList.size(); i++) {
                        JSONObject attendanceObject = new JSONObject();
                        attendanceObject.put("student_id", studentList.get(i).getId());

                        if (studentList.get(i).isSelected()) {
                            isItemSelected = true;
                            attendanceObject.put("status", "1");
                        } else {
                            attendanceObject.put("status", "0");
                        }
                        attendanceArray.put(attendanceObject);
                        jsonObject.put("students", attendanceArray);


                    }
                    if (isItemSelected) {
                        postAttendanceData(jsonObject);
                    } else {
                        showToast("No Students Selected");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void postAttendanceData(final JSONObject jsonObject) {
        showToast("Please Wait..");
        OkHttpClient client = new OkHttpClient();
        String POST_URL = getResources().getString(R.string.base_url) + "/submit_student_attendance.php";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        okhttp3.RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().url(POST_URL)
                .addHeader("Token", "d75542712c868c1690110db641ba01a")
                .post(requestBody).build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
                         @Override
                         public void onFailure(okhttp3.Call call, IOException e) {
                             postAttendanceData(jsonObject);
                             System.out.println("Registration Error" + e.getMessage());
                             showToast("Something Went Wrong!");
                         }

                         @Override
                         public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                             String resp = response.body().string();
                             Log.d("resp", resp);

                             if (response.isSuccessful()) {
                                 JSONObject obj = null;
                                 try {
                                     obj = new JSONObject(resp);
                                     JSONObject obj_response = obj.getJSONObject("Response");
                                     final JSONObject obj_status = obj_response.getJSONObject("status");
                                     final String msgFinal = obj_status.getString("type");
                                     if (Objects.equals(msgFinal, "Success")) {
                                         final JSONObject obj_data = obj_response.getJSONObject("data");
                                         String msgType = obj_data.getString("type");
                                         if (Objects.equals(msgType, "Success")) {
                                             Intent myCenterIntent = new Intent(StudentAttendanceActivity.this, MyCenterActivity.class);
                                             myCenterIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                             myCenterIntent.putExtra("center_id", String.valueOf(sharedPreferences.getInt("center_id", 0)));
                                             myCenterIntent.putExtra("latitude", String.valueOf(sharedPreferences.getString("latitude", "")));
                                             myCenterIntent.putExtra("longitude", String.valueOf(sharedPreferences.getString("longitude", "")));
                                             startActivity(myCenterIntent);
                                         } else {
                                             showToast("Something Went Wrong!");
                                         }
                                     }
                                 } catch (JSONException e) {
                                     showToast("Something Went Wrong!");

                                     e.printStackTrace();
                                 }
                             } else {
                                 showToast("Something Went Wrong!");
                             }

                         }
                     }
        );
    }

    private void showToast(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_students_attendance);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        studentsAdapter = new AttendanceAdapter(getApplicationContext(), studentList, fabSubmitAttendance);
        recyclerView.setAdapter(studentsAdapter);
    }
}