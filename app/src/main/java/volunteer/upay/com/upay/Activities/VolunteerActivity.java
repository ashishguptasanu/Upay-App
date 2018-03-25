package volunteer.upay.com.upay.Activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import volunteer.upay.com.upay.Adapters.StudentsAdapter;
import volunteer.upay.com.upay.Adapters.VolunteerAdapter;
import volunteer.upay.com.upay.Models.Student;
import volunteer.upay.com.upay.Models.Volunteer;
import volunteer.upay.com.upay.R;

public class VolunteerActivity extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();
    List<Volunteer> volunteerList = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    VolunteerAdapter volunteerAdapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valunteer);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int centerId = sharedPreferences.getInt("center_id", 0);
        //getStudentsDetails(String.valueOf(centerId));
        Volunteer volunteer = new Volunteer("0","test", "test", "test", "test", "test", "test", "test", "test", "test", "test", "test", "");
        volunteerList.add(volunteer);
        volunteerList.add(volunteer);
        volunteerList.add(volunteer);
        initViews();
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
        recyclerView = findViewById(R.id.recycler_volunteers);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        volunteerAdapter = new VolunteerAdapter(getApplicationContext(), volunteerList);
        recyclerView.setAdapter(volunteerAdapter);
    }
}
