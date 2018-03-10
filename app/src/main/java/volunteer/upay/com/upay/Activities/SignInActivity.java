package volunteer.upay.com.upay.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import volunteer.upay.com.upay.R;

public class SignInActivity extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getCenterDetails();
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
                                 /*try {
                                     obj = new JSONObject(resp);
                                     JSONObject obj_response=obj.getJSONObject("Response");
                                     final JSONObject obj_status=obj_response.getJSONObject("status");
                                     //
                                     final String msgFinal = obj_status.getString("type");
                                     //final String msgDetail = obj_data.getString("message");
                                     if(Objects.equals(msgFinal, "Success")){
                                         final JSONObject obj_data=obj_response.getJSONObject("data");
                                         if(obj_data.get("type") == "Success"){
                                             progress.dismiss();
                                             showToast("Successfully user added.");
                                         }else{
                                             progress.dismiss();
                                             showToast(obj_data.getString("message"));
                                         }

                                     }else{
                                         runOnUiThread(new Runnable() {
                                             @Override
                                             public void run() {
                                                 progress.dismiss();

                                                 try {
                                                     showToast(obj_status.getString("message"));
                                                 } catch (JSONException e) {
                                                     e.printStackTrace();
                                                 }
                                             }
                                         });
                                     }
                                 } catch (JSONException e) {
                                     showToast("Something went wrong!");
                                     progress.dismiss();
                                     e.printStackTrace();
                                 }*/
                             }
                         }
                     }
        );
    }
}
