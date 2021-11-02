package volunteer.upay.com.upay.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

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
import volunteer.upay.com.upay.Models.Centers;
import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.rest.RetrofitAdapter;

public class CenterActivity extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();
    List<Centers> centerList = new ArrayList<>();
    AdapterCenters adapterCenters;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);
        getCenterDetails();

    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_centers);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterCenters = new AdapterCenters(getApplicationContext(), centerList);
        recyclerView.setAdapter(adapterCenters);
    }

    private void getCenterDetails() {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("null", "")
                .build();
        Request request = new Request.Builder().url(RetrofitAdapter.BASE_URL+ "/get_center_details.php").addHeader("Token", getResources().getString(R.string.token)).post(requestBody).build();
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
                                             String latitude = centerObject.getString("latitude");
                                             String longitude = centerObject.getString("longitude");
                                             String centerType = centerObject.getString("center_type");
                                             Centers centers = new Centers(center_name, center_id, zone_name, zone_id, latitude, longitude, center_head_name, center_head_phone, center_address, centerType);
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
                             }
                         }
                     }
        );
    }
}
