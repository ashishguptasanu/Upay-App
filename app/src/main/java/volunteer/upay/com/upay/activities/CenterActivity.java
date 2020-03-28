package volunteer.upay.com.upay.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import volunteer.upay.com.upay.adapters.AdapterCenters;
import volunteer.upay.com.upay.models.Centers;
import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.localdb.CenterRepository;

public class CenterActivity extends AppCompatActivity implements TextWatcher {
    OkHttpClient client = new OkHttpClient();
    List<Centers> centerList = new ArrayList<>();
    AdapterCenters adapterCenters;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    CenterRepository centerRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);

        EditText filterText = findViewById(R.id.filterText);
        filterText.addTextChangedListener(this);

        centerRepository = new CenterRepository(this);
        getCenterDetails();
        initViews();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_centers);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapterCenters = new AdapterCenters(getApplicationContext());
        recyclerView.setAdapter(adapterCenters);
    }

    private void getCenterDetails() {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("null", "")
                .build();
        Request request = new Request.Builder().url(getResources().getString(R.string.base_url) + "/get_center_details.php").addHeader("Token", getResources().getString(R.string.token)).post(requestBody).build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
                         @Override
                         public void onFailure(okhttp3.Call call, IOException e) {
                             System.out.println("Registration Error" + e.getMessage());
                         }

                         @Override
                         public void onResponse(Call call, Response response) throws IOException {
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
                                         final JSONArray center_array = obj_data.getJSONArray("centers");
                                         for (int i = 0; i < center_array.length(); i++) {
                                             JSONObject centerObject = center_array.getJSONObject(i);
                                             String center_name = centerObject.getString("center_name");
                                             String center_id = centerObject.getString("center_id");
                                             String center_address = centerObject.getString("center_address");
                                             String zone_name = centerObject.getString("zone_name");
                                             String zone_id = centerObject.getString("zone_id");
                                             String center_head_phone = centerObject.getString("center_head_phone");
                                             String center_head_name = centerObject.getString("center_head_name");
                                             String latitude = centerObject.getString("latitude");
                                             String longitude = centerObject.getString("longitude");
                                             String centerType = centerObject.getString("center_type");
                                             Centers centers = new Centers(center_name, center_id, zone_name, zone_id, latitude, longitude, center_head_name, center_head_phone, center_address, centerType);
                                             addCenterToLocalDb(centers);
                                             centerList.add(centers);
                                         }
                                         Collections.sort(centerList);
                                         runOnUiThread(new Runnable() {
                                             @Override
                                             public void run() {
                                                 adapterCenters.swapCenters(centerList);
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

    private void addCenterToLocalDb(@NonNull Centers centers) {
        centerRepository.insertTask(centers);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        adapterCenters.onFilterApplied(s.toString());
    }
}
