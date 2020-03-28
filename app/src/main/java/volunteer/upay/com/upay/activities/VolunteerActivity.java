package volunteer.upay.com.upay.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

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
import volunteer.upay.com.upay.adapters.VolunteerAdapter;
import volunteer.upay.com.upay.models.Volunteer;
import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.localdb.VolunteerRepository;

public class VolunteerActivity extends BaseFilterActivity {
    OkHttpClient client = new OkHttpClient();
    List<Volunteer> volunteerList = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    VolunteerAdapter volunteerAdapter;
    SharedPreferences sharedPreferences;
    EditText filterText;
    private VolunteerRepository mVolunteerRepository;

    public static void open(@NonNull Context context, @Nullable String centerId) {
        Intent intent = new Intent(context, VolunteerActivity.class);
        if (TextUtils.isEmpty(centerId)) {
            centerId = "";
        }
        intent.putExtra("center_id", centerId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVolunteerRepository = new VolunteerRepository(this);
        setContentView(R.layout.activity_valunteer);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String centerId = getIntent().getStringExtra("center_id");
        getVolunteersDetails(centerId);
    }

    private void getVolunteersDetails(String center_id) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("center_id", center_id)
                .build();
        Request request = new Request.Builder().url(getResources().getString(R.string.base_url) + "/get_volunteer_details.php").addHeader("Token", getResources().getString(R.string.token)).post(requestBody).build();
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
                                         JSONArray center_array = obj_data.getJSONArray("volunteers");
                                         for (int i = 0; i < center_array.length(); i++) {
                                             JSONObject centerObject = center_array.getJSONObject(i);
                                             String id = centerObject.getString("id");
                                             String centerName = centerObject.getString("center_name");
                                             String centerId = centerObject.getString("center_id");
                                             String zoneName = centerObject.getString("zone_name");
                                             String zoneId = centerObject.getString("zone_id");
                                             String upayId = centerObject.getString("upay_id");
                                             String emailId = centerObject.getString("email_id");
                                             String phone = centerObject.getString("phone");
                                             String password = centerObject.getString("password");
                                             String adminAccess = centerObject.getString("admin_access");
                                             String name = centerObject.getString("name");
                                             String addedBy = centerObject.getString("added_by");
                                             String photoUrl = centerObject.getString("photo_url");
                                             if (TextUtils.isEmpty(name)) {
                                                 continue;
                                             }
                                             Volunteer volunteer = new Volunteer(id, centerName, centerId, zoneName, zoneId, upayId, emailId, phone, password, adminAccess, name, addedBy, photoUrl);
                                             addVolunteerToLocalDb(volunteer);
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


    private void addVolunteerToLocalDb(@NonNull Volunteer volunteer) {
        mVolunteerRepository.insertTask(volunteer);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_volunteers);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        volunteerAdapter = new VolunteerAdapter(getApplicationContext(), volunteerList);
        recyclerView.setAdapter(volunteerAdapter);
    }


    @Override
    public int getFilterEditTextId() {
        return R.id.filterText;
    }

    @Override
    public void textChanged(CharSequence text) {
        if (volunteerAdapter != null)
            volunteerAdapter.changeText(text);
    }
}
