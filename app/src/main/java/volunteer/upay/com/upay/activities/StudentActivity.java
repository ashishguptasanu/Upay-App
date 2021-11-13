package volunteer.upay.com.upay.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import volunteer.upay.com.upay.adapters.StudentsAdapter;
import volunteer.upay.com.upay.models.Student;
import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.rest.RetrofitAdapter;

public class StudentActivity extends BaseFilterActivity {
    OkHttpClient client = new OkHttpClient();
    List<Student> studentList = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    StudentsAdapter studentsAdapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int centerId = sharedPreferences.getInt("center_id", 0);
        if (Objects.equals(centerId, 0)) {
            getStudentsDetails("");
        } else {
            getStudentsDetails(String.valueOf(centerId));
        }
    }

    private void getStudentsDetails(String center_id) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("center_id", center_id)
                .build();
        Request request = new Request.Builder().url(RetrofitAdapter.BASE_URL + "/get_students_details.php").addHeader("Token", getResources().getString(R.string.token)).post(requestBody).build();
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

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_students);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        studentsAdapter = new StudentsAdapter(getApplicationContext(), studentList);
        recyclerView.setAdapter(studentsAdapter);
    }

    @Override
    public int getFilterEditTextId() {
        return R.id.filterText;
    }

    @Override
    public void textChanged(CharSequence text) {
        if (studentsAdapter != null)
            studentsAdapter.changeText(text);
    }
}
