package volunteer.upay.com.upay.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.rest.RetrofitAdapter;

public class AddVolunteer extends AppCompatActivity implements View.OnClickListener{
    EditText edtEmail, edtUpayId;
    Button btnSubmit;
    String centerId, zoneId;
    LinearLayout layoutVolu, layoutBackground;
    SharedPreferences sharedPreferences;
    AlertDialog alertDialog;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_volunteer);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        layoutBackground = findViewById(R.id.layout_background);
        layoutVolu = findViewById(R.id.layout_add_volunteer);

        centerId = sharedPreferences.getString("center_id_volu","");
        Log.d("Center Id:", centerId);
        zoneId = sharedPreferences.getString("zone_id_volu", "");
        Log.d("Admin Access:", sharedPreferences.getString("admin_access",""));
        if(Objects.equals(Integer.parseInt(sharedPreferences.getString("admin_access","")), 1)){

            if(Objects.equals(centerId, String.valueOf(sharedPreferences.getInt("center_id", 0)))){
                initAddVolunteerView();
            }else{
                layoutVolu.setVisibility(View.GONE);
                layoutBackground.setVisibility(View.VISIBLE);
            }
        }
        else if(Objects.equals(Integer.parseInt(sharedPreferences.getString("admin_access","")), 2)){
            if(Objects.equals(zoneId, String.valueOf(sharedPreferences.getInt("zone_id",0)))){
                initAddVolunteerView();
            }else{
                layoutVolu.setVisibility(View.GONE);
                layoutBackground.setVisibility(View.VISIBLE);
            }
        }
        else if(Objects.equals(Integer.parseInt(sharedPreferences.getString("admin_access","")), 3)){
            initAddVolunteerView();
        }
        else{
            layoutVolu.setVisibility(View.GONE);
            layoutBackground.setVisibility(View.VISIBLE);
        }
    }

    private void initAddVolunteerView() {
        layoutVolu.setVisibility(View.VISIBLE);
        layoutBackground.setVisibility(View.GONE);
        edtUpayId = findViewById(R.id.edt_add_volunteer_upay_id);
        edtEmail = findViewById(R.id.edt_add_volunteer);
        btnSubmit = findViewById(R.id.btn_add_volunteer);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_volunteer:
                checkVolunteerData(edtEmail.getText().toString(), edtUpayId.getText().toString());
                break;
        }
    }

    private void checkVolunteerData(String emailId, String upayId) {
        if(!TextUtils.isEmpty(emailId)){
            if(isEmailValid(emailId)){
                addVolunteerDetails(emailId, upayId);
            }else{
                edtEmail.setError("Email is not valid.");
            }
        }else{
            edtEmail.setError("Email can't be blank.");
        }

    }
    private void addVolunteerDetails(String emailId, String upayId) {
        showProgress("", "Please wait..");
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("email_volunteer", emailId)
                .addFormDataPart("added_by", sharedPreferences.getString("login_email",""))
                .addFormDataPart("upay_id", upayId)
                .addFormDataPart("phone", "9876543210")
                .addFormDataPart("center_name", sharedPreferences.getString("center_name",""))
                .addFormDataPart("center_id", String.valueOf(sharedPreferences.getInt("center_id",0)))
                .addFormDataPart("zone_name", sharedPreferences.getString("zone_name",""))
                .addFormDataPart("zone_id", String.valueOf(sharedPreferences.getInt("zone_id",0)))
                .build();
        Request request = new Request.Builder().url(RetrofitAdapter.BASE_URL+ "/add_volunteer_admin.php").addHeader("Token", getResources().getString(R.string.token)).post(requestBody).build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
                         @Override
                         public void onFailure(okhttp3.Call call, IOException e) {
                             System.out.println("Registration Error" + e.getMessage());
                             showToast("Registration failed.");
                             alertDialog.cancel();
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
                                         String msgType = obj_data.getString("type");
                                         String finalData = obj_data.getString("data");
                                         /*String adminAccess = obj_data.getString("admin_access");
                                         String name = obj_data.getString("volunteer_name");*/
                                         if(Objects.equals(msgType, "Success")){
                                             alertDialog.cancel();
                                             showToast(finalData);
                                             Intent intent = new Intent(getApplicationContext(), MyCenterActivity.class);
                                             intent.putExtra("center_id", String.valueOf(sharedPreferences.getInt("center_id",0)));
                                             intent.putExtra("latitude", String.valueOf(sharedPreferences.getString("latitude","")));
                                             intent.putExtra("longitude", String.valueOf(sharedPreferences.getString("longitude","")));
                                             startActivity(intent);
                                         }else{
                                             showToast(finalData);
                                             alertDialog.cancel();
                                         }
                                     }
                                 } catch (JSONException e) {
                                     showToast("Registration failed.");
                                     alertDialog.cancel();
                                     e.printStackTrace();
                                 }
                             }else{
                                 showToast("Registration failed.");
                                 alertDialog.cancel();
                             }
                         }
                     }
        );
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private void showProgress(String title, String msg){
        alertDialog = new ProgressDialog(this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.show();
    }
    private void showToast(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_LONG).show();
            }
        });
    }
}
