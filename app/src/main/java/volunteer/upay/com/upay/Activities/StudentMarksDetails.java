package volunteer.upay.com.upay.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import volunteer.upay.com.upay.Adapters.AdapterCenters;
import volunteer.upay.com.upay.Adapters.StudentMarksAdapter;
import volunteer.upay.com.upay.Models.Marks;
import volunteer.upay.com.upay.Models.Student;
import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.rest.RetrofitAdapter;

public class StudentMarksDetails extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();
    List<Marks> marksList = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    StudentMarksAdapter studentMarksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_marks_details);
        String studentId = getIntent().getStringExtra("student_id");

        fetchTestDetails(studentId);

    }

    private void fetchTestDetails(String studentId) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("student_id", studentId)
                .build();
        Request request = new Request.Builder().url(RetrofitAdapter.BASE_URL+ "/get_student_marks.php").addHeader("Token", getResources().getString(R.string.token)).post(requestBody).build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
                         @Override
                         public void onFailure(okhttp3.Call call, IOException e) {
                             System.out.println("Registration Error" + e.getMessage());
                         }
                         @Override
                         public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                             String resp = response.body().string();
                             Log.d("resp",resp);

                             if (response.isSuccessful()) {
                                 JSONObject obj = null;
                                 try {
                                     obj = new JSONObject(resp);
                                     JSONObject obj_response=obj.getJSONObject("Response");
                                     final JSONObject obj_status=obj_response.getJSONObject("status");
                                     final String msgFinal = obj_status.getString("type");
                                     if(Objects.equals(msgFinal, "Success")){
                                         final JSONObject obj_data=obj_response.getJSONObject("data");
                                         JSONArray center_array = obj_data.getJSONArray("marks");
                                         for (int i=0; i<center_array.length(); i++) {
                                             JSONObject centerObject = center_array.getJSONObject(i);
                                             String id = centerObject.getString("id");
                                             String student_id = centerObject.getString("student_id");
                                             String test_name = centerObject.getString("test_name");
                                             String total_marks = centerObject.getString("total_marks");
                                             String marks_obtained = centerObject.getString("marks_obtained");
                                             String submitted_date = centerObject.getString("submitted_date");
                                             String submitted_by  = centerObject.getString("submitted_by");
                                             Marks marks = new Marks(id, student_id, test_name, total_marks, marks_obtained, submitted_date, submitted_by);
                                             marksList.add(marks);
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
        recyclerView = findViewById(R.id.recycler_marks);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        studentMarksAdapter = new StudentMarksAdapter(getApplicationContext(), marksList);
        recyclerView.setAdapter(studentMarksAdapter);
    }
}
