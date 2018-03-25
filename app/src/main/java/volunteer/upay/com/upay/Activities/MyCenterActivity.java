package volunteer.upay.com.upay.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import volunteer.upay.com.upay.Models.Centers;
import volunteer.upay.com.upay.Models.Student;
import volunteer.upay.com.upay.Models.Volunteer;
import volunteer.upay.com.upay.R;

public class MyCenterActivity extends AppCompatActivity implements View.OnClickListener{
    OkHttpClient client = new OkHttpClient();
    LinearLayout layoutStudents, layoutVolunteers;
    CardView cardAddStudent, cardAddVolunteer, cardSyllabus, cardChat, cardAttendance;
    List<Student> studentList = new ArrayList<>();
    List<Volunteer> volunteerList = new ArrayList<>();
    TextView tvNumStudents, tvNumVolunteers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_center);
        initViews();
        String center_id = getIntent().getStringExtra("center_id");
        getStudentsDetails(center_id);
        getVolunteerDetails(center_id);

    }

    private void initViews() {


        layoutStudents = findViewById(R.id.layout_students);
        layoutVolunteers = findViewById(R.id.layout_volunteer);
        cardAddStudent = findViewById(R.id.card_add_student);
        cardAddVolunteer = findViewById(R.id.card_add_volunteer);
        cardSyllabus = findViewById(R.id.card_syllabus);
        cardAttendance = findViewById(R.id.card_attandence);
        cardChat = findViewById(R.id.card_chat);
        cardSyllabus.setOnClickListener(this);
        cardAttendance.setOnClickListener(this);
        cardChat.setOnClickListener(this);
        cardAddVolunteer.setOnClickListener(this);
        cardAddStudent.setOnClickListener(this);
        layoutStudents.setOnClickListener(this);
        layoutVolunteers.setOnClickListener(this);
    }

    private void getStudentsDetails(String center_id) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("center_id", center_id)
                .build();
        Request request = new Request.Builder().url(getResources().getString(R.string.base_url)+ "/get_students_details.php").addHeader("Token", getResources().getString(R.string.token)).post(requestBody).build();
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
                                         JSONArray center_array = obj_data.getJSONArray("students");
                                         for (int i=0; i<center_array.length(); i++) {
                                             JSONObject centerObject = center_array.getJSONObject(i);
                                             String id = centerObject.getString("id");
                                             String student_name = centerObject.getString("student_name");
                                             String parent_name = centerObject.getString("parent_name");
                                             String age = centerObject.getString("age");
                                             String clss = centerObject.getString("class");
                                             String school = centerObject.getString("school");
                                             String center_id  = centerObject.getString("center_id");
                                             String zone_id = centerObject.getString("zone_id");
                                             String center_name = centerObject.getString("center_name");
                                             String zone_name = centerObject.getString("zone_name");
                                             String photo_url = centerObject.getString("photo_url");
                                             String comments = centerObject.getString("comments");
                                             Student student = new Student(id, student_name, parent_name, age, clss, school, center_name, center_id, zone_name, zone_id, photo_url, comments);
                                             studentList.add(student);
                                         }
                                         runOnUiThread(new Runnable() {
                                             @Override
                                             public void run() {
                                                 tvNumStudents = findViewById(R.id.tv_num_students);

                                                 if(studentList.size()> 0){
                                                     tvNumStudents.setText(String.valueOf(studentList.size()));
                                                 }else{
                                                     tvNumStudents.setText("0");
                                                 }
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
    private void getVolunteerDetails(String center_id) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("center_id", center_id)
                .build();
        Request request = new Request.Builder().url(getResources().getString(R.string.base_url)+ "/get_volunteer_details.php").addHeader("Token", getResources().getString(R.string.token)).post(requestBody).build();
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
                                         JSONArray center_array = obj_data.getJSONArray("volunteers");
                                         for (int i=0; i<center_array.length(); i++) {
                                             JSONObject centerObject = center_array.getJSONObject(i);
                                             String id = centerObject.getString("id");
                                             String centerName = centerObject.getString("center_name");
                                             String centerId = centerObject.getString("center_id");
                                             String zoneName = centerObject.getString("zone_name");
                                             String zoneId = centerObject.getString("zone_id");
                                             String upayId = centerObject.getString("upay_id");
                                             String emailId = centerObject.getString("email_id");
                                             String phone  = centerObject.getString("phone");
                                             String password = centerObject.getString("password");
                                             String adminAccess= centerObject.getString("admin_access");
                                             String name = centerObject.getString("name");
                                             String addedBy = centerObject.getString("added_by");
                                             String photoUrl = centerObject.getString("photo_url");
                                             Volunteer volunteer = new Volunteer(id, centerName, centerId, zoneName, zoneId, upayId, emailId, phone, password, adminAccess, name, addedBy, photoUrl);
                                             volunteerList.add(volunteer);
                                         }
                                         Log.d("Volu", String.valueOf(volunteerList.size()));
                                         runOnUiThread(new Runnable() {
                                             @Override
                                             public void run() {
                                                 tvNumVolunteers = findViewById(R.id.tv_num_volunteers);
                                                 if(studentList.size()> 0){
                                                     tvNumVolunteers.setText(String.valueOf(volunteerList.size()));
                                                 }else{
                                                     tvNumVolunteers.setText("0");
                                                 }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_students:
                Intent studentIntent = new Intent(getApplicationContext(), StudentActivity.class);
                startActivity(studentIntent);
                break;
            case R.id.card_add_volunteer:
                Intent volunteerIntent = new Intent(getApplicationContext(), AddVolunteer.class);
                startActivity(volunteerIntent);
                break;
            case R.id.card_add_student:
                Intent intent = new Intent(getApplicationContext(), AddStudent.class);
                startActivity(intent);
                break;
            case R.id.layout_volunteer:
                Intent volunteer2Intent = new Intent(getApplicationContext(), VolunteerActivity.class);
                startActivity(volunteer2Intent);
                break;
            case R.id.card_syllabus:
                showToast("Coming Soon..");
                break;
            case R.id.card_attandence:
                showToast("Coming Soon..");
                break;
            case R.id.card_chat:
                showToast("Coming Soon..");
                break;
        }
    }

    private void showToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
