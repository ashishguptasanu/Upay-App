package volunteer.upay.com.upay.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import volunteer.upay.com.upay.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    OkHttpClient client = new OkHttpClient();
    List<Centers> centerList = new ArrayList<>();
    LinearLayout layoutSignIn, layoutSignUp;
    TextView tvSignIn, tvSignUp;
    Button btnSignIn, btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getCenterDetails();
        initViews();
    }

    private void initViews() {
        layoutSignIn = findViewById(R.id.layout_sign_in);
        layoutSignUp = findViewById(R.id.layout_sign_up);
        tvSignIn = findViewById(R.id.tv_sign_in);
        tvSignUp = findViewById(R.id.tv_sign_up);
        btnSignIn = findViewById(R.id.btn_sign_in);
        btnSignUp = findViewById(R.id.btn_sign_up);
        tvSignIn.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    private void getCenterDetails() {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("null", "")
                .build();
        Request request = new Request.Builder().url(getResources().getString(R.string.base_url)+ "/get_center_details.php").addHeader("Token", getResources().getString(R.string.token)).post(requestBody).build();
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
                                             double latitude = centerObject.getDouble("latitude");
                                             double longitude = centerObject.getDouble("longitude");
                                             Centers centers = new Centers(center_name, center_id, zone_name, zone_id, latitude, longitude, center_head_name, center_head_phone, center_address);
                                             centerList.add(centers);
                                         }
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
            case R.id.tv_sign_in:
                if(layoutSignUp.getVisibility() == View.VISIBLE){
                    layoutSignUp.setVisibility(View.GONE);
                    layoutSignIn.setVisibility(View.VISIBLE);
                }else if(layoutSignIn.getVisibility() == View.VISIBLE){
                    layoutSignUp.setVisibility(View.VISIBLE);
                    layoutSignIn.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_sign_up:
                if(layoutSignUp.getVisibility() == View.VISIBLE){
                    layoutSignUp.setVisibility(View.GONE);
                    layoutSignIn.setVisibility(View.VISIBLE);
                }else if(layoutSignIn.getVisibility() == View.VISIBLE){
                    layoutSignUp.setVisibility(View.VISIBLE);
                    layoutSignIn.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_sign_in:
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_sign_up:
                break;
        }
    }
}
