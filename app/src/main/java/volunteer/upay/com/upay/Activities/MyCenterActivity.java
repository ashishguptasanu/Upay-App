package volunteer.upay.com.upay.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import volunteer.upay.com.upay.Models.Centers;
import volunteer.upay.com.upay.R;

public class MyCenterActivity extends AppCompatActivity implements View.OnClickListener{
    OkHttpClient client = new OkHttpClient();
    LinearLayout layoutStudents, layoutVolunteers;
    CardView cardAddStudent, cardAddVolunteer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_center);
        initViews();
        String center_id = getIntent().getStringExtra("center_id");
        getStudentsDetails(center_id);
        getVolunteerDetails("");
        getVolunteerDetails("45");

    }

    private void initViews() {
        layoutStudents = findViewById(R.id.layout_students);
        layoutVolunteers = findViewById(R.id.layout_volunteer);
        cardAddStudent = findViewById(R.id.card_add_student);
        cardAddVolunteer = findViewById(R.id.card_add_volunteer);
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

                            /* if (response.isSuccessful()) {
                                 JSONObject obj = null;
                                 try {
                                     obj = new JSONObject(resp);
                                     JSONObject obj_response=obj.getJSONObject("Response");
                                     final JSONObject obj_status=obj_response.getJSONObject("status");
                                     final String msgFinal = obj_status.getString("type");
                                     if(Objects.equals(msgFinal, "Success")){
                                         final JSONObject obj_data=obj_response.getJSONObject("data");
                                         JSONArray center_array = obj_data.getJSONArray("centers");
                                         for (int i=0; i<center_array.length(); i++) {
                                             JSONObject centerObject = center_array.getJSONObject(i);
                                             String center_name = centerObject.getString("center_name");
                                             String center_id = centerObject.getString("center_id");
                                             String center_address = centerObject.getString("center_address");
                                             String zone_name = centerObject.getString("zone_name");
                                             String zone_id = centerObject.getString("zone_id");
                                             String center_head_phone = centerObject.getString("center_head_phone");
                                             String center_head_name = centerObject.getString("center_head_name");
                                             double latitude = centerObject.getDouble("latitude");
                                             double longitude = centerObject.getDouble("longitude");
                                             Centers centers = new Centers(center_name, center_id, zone_name, zone_id, latitude, longitude, center_head_name, center_head_phone, center_address);
                                             centerList.add(centers);
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
                             }*/
                         }
                     }
        );
    }
    private void getVolunteerDetails(String center_id) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("center_id", center_id)
                .build();
        Request request = new Request.Builder().url(getResources().getString(R.string.base_url)+ "/get_volunteers.php").addHeader("Token", getResources().getString(R.string.token)).post(requestBody).build();
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

                            /* if (response.isSuccessful()) {
                                 JSONObject obj = null;
                                 try {
                                     obj = new JSONObject(resp);
                                     JSONObject obj_response=obj.getJSONObject("Response");
                                     final JSONObject obj_status=obj_response.getJSONObject("status");
                                     final String msgFinal = obj_status.getString("type");
                                     if(Objects.equals(msgFinal, "Success")){
                                         final JSONObject obj_data=obj_response.getJSONObject("data");
                                         JSONArray center_array = obj_data.getJSONArray("centers");
                                         for (int i=0; i<center_array.length(); i++) {
                                             JSONObject centerObject = center_array.getJSONObject(i);
                                             String center_name = centerObject.getString("center_name");
                                             String center_id = centerObject.getString("center_id");
                                             String center_address = centerObject.getString("center_address");
                                             String zone_name = centerObject.getString("zone_name");
                                             String zone_id = centerObject.getString("zone_id");
                                             String center_head_phone = centerObject.getString("center_head_phone");
                                             String center_head_name = centerObject.getString("center_head_name");
                                             double latitude = centerObject.getDouble("latitude");
                                             double longitude = centerObject.getDouble("longitude");
                                             Centers centers = new Centers(center_name, center_id, zone_name, zone_id, latitude, longitude, center_head_name, center_head_phone, center_address);
                                             centerList.add(centers);
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
                             }*/
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
        }
    }
}
