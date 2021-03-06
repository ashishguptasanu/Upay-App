package volunteer.upay.com.upay.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

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
import volunteer.upay.com.upay.Models.ChatUser;
import volunteer.upay.com.upay.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    OkHttpClient client = new OkHttpClient();
    LinearLayout layoutSignIn, layoutSignUp;
    TextView tvSignIn, tvSignUp;
    Button btnSignIn, btnSignUp;
    ProgressDialog alertDialog;
    EditText name, emailSignUp, phone, passwordSignUp, confirmPasswordSignUp, emailSignIn, passwordSignIn;
    InputMethodManager imm;
    SharedPreferences sharedPreferences;
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //database = FirebaseDatabase.getInstance().getReference();
        initViews();
    }

    private void initViews() {
        if(!isNetworkConnected()){
           showToast("Please Check Your Internet Connection.");
        }
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
        name = findViewById(R.id.edt_name_sign_up);
        emailSignUp = findViewById(R.id.edt_email_sign_up);
        phone = findViewById(R.id.edt_phone_sign_up);
        passwordSignUp = findViewById(R.id.edt_password_sign_up);
        confirmPasswordSignUp = findViewById(R.id.edt_password_confirm_sign_up);
        emailSignIn = findViewById(R.id.edt_email_sign_in);
        passwordSignIn = findViewById(R.id.edt_password_sign_in);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);


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
                imm.hideSoftInputFromWindow(passwordSignIn.getWindowToken(), 0);
                validateSignInData(emailSignIn.getText().toString(), passwordSignIn.getText().toString());
                break;
            case R.id.btn_sign_up:
                imm.hideSoftInputFromWindow(passwordSignUp.getWindowToken(), 0);
                validateSignUpData(name.getText().toString(), emailSignUp.getText().toString(), phone.getText().toString(), passwordSignUp.getText().toString(), confirmPasswordSignUp.getText().toString());
                break;
        }
    }

    private void validateSignUpData(String nameSignUp, String email, String phoneSignUp, String password, String confirmPassword) {
        if(!nameSignUp.isEmpty()) {
            if (!email.isEmpty()) {
                if(!phoneSignUp.isEmpty()){
                    if(!password.isEmpty() && !confirmPassword.isEmpty()) {
                        if(isEmailValid(email)) {
                            if(phoneSignUp.length() > 9 ) {
                                if(Objects.equals(password, confirmPassword)) {
                                    showProgress("Signing up", "Please wait, you are signing up..");
                                    signUp(nameSignUp, email, phoneSignUp, password);
                                }else{showToast("Password doesn't match.");}
                            }else{showToast("Please enter a valid mobile number.");}
                        }else{emailSignUp.setError("Please enter a valid email.");}
                    }else{showToast("Password can't be blank.");}
                }else{ phone.setError("Field can't be blank.");}
            } else {emailSignUp.setError("Field can't be blank.");}
        }else{name.setError("Field can't be blank.");}
    }

    private void validateSignInData(String email, String password) {
        if (!email.isEmpty()) {
            if(isEmailValid(email)) {
                if(!password.isEmpty()){
                    showProgress("Signing in", "Please wait, you are signing in..");
                    signIn(email, password);
                }else{showToast("Password can't be blank.");}
            }else{emailSignIn.setError("Please enter a valid email.");}
        } else{emailSignIn.setError("Field can't be blank.");}

    }

    private void signIn(final String email, String password) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("email_volunteer", email)
                .addFormDataPart("password", password)
                .build();
        Request request = new Request.Builder().url(getResources().getString(R.string.base_url)+ "/sign_in_volunteer.php").addHeader("Token", getResources().getString(R.string.token)).post(requestBody).build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
                         @Override
                         public void onFailure(okhttp3.Call call, IOException e) {
                             System.out.println("Registration Error" + e.getMessage());
                             showToast("Sign in failed.");
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
                                         String adminAccess = obj_data.getString("admin_access");
                                         String name = obj_data.getString("volunteer_name");
                                         String centerId = obj_data.getString("center_id");
                                         String zoneId = obj_data.getString("zone_id");
                                         if(Objects.equals(msgType, "Success")){
                                             alertDialog.cancel();
                                             showToast(finalData);
                                             saveLoginDetails(email, adminAccess, name, centerId, zoneId);
                                             //writeUserToDatabase(email, name);
                                             Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                             startActivity(intent);
                                         }else{
                                            showToast(finalData);
                                             alertDialog.cancel();
                                         }
                                     }
                                 } catch (JSONException e) {
                                     showToast("Sign in failed.");
                                     alertDialog.cancel();
                                     e.printStackTrace();
                                 }
                             }else{
                                 showToast("Sign in failed.");
                                 alertDialog.cancel();
                             }
                         }
                     }
        );
    }

    private void writeUserToDatabase(String email, String name) {
        /*ChatUser chatUser = new ChatUser(email, name, FirebaseInstanceId.getInstance().getToken());
        *//*database.child("users").child(email.replace("@")).setValue(chatUser);*/
    }

    private void saveLoginDetails(String email, String adminAccess, String name, String centerId, String zone_id) {
        sharedPreferences.edit().putString("login_email", email).apply();
        sharedPreferences.edit().putString("admin_access", adminAccess).apply();
        sharedPreferences.edit().putString("volunteer_name", name).apply();
        sharedPreferences.edit().putString("center_id_volu", centerId).apply();
        sharedPreferences.edit().putString("zone_id_volu", zone_id).apply();
        sharedPreferences.edit().putInt("login", 1).apply();
        showToast("signed in as " + email);
    }

    private void showToast(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_LONG).show();
            }
        });
    }
    private void signUp(final String name, final String email, String phone, String password) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", name)
                .addFormDataPart("email_volunteer", email)
                .addFormDataPart("phone", phone)
                .addFormDataPart("password", password)
                .build();
        Request request = new Request.Builder().url(getResources().getString(R.string.base_url)+ "/sign_up_volunteer.php").addHeader("Token", getResources().getString(R.string.token)).post(requestBody).build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
                         @Override
                         public void onFailure(okhttp3.Call call, IOException e) {
                             System.out.println("Registration Error" + e.getMessage());
                             showToast("Sign up failed.");
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
                                         String adminAccess = obj_data.getString("admin_access");
                                         String centerId = obj_data.getString("center_id");
                                         String zone_id = obj_data.getString("zone_id");
                                         if(Objects.equals(msgType, "Success")){
                                             alertDialog.cancel();
                                             showToast(finalData);
                                             Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                             startActivity(intent);
                                             saveLoginDetails(email,adminAccess, name, centerId, zone_id);
                                         }else{
                                             showToast(finalData);
                                             alertDialog.cancel();
                                         }
                                     }
                                 } catch (JSONException e) {
                                     showToast("Sign up failed.");
                                     alertDialog.cancel();
                                     e.printStackTrace();
                                 }
                             }else{
                                 showToast("Sign up failed.");
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
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
