package volunteer.upay.com.upay.Activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import volunteer.upay.com.upay.Models.Centers;
import volunteer.upay.com.upay.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    OkHttpClient client = new OkHttpClient();
    LinearLayout layoutSignIn, layoutSignUp;
    TextView tvSignIn, tvSignUp;
    Button btnSignIn, btnSignUp;
    AlertDialog alertDialog;
    EditText name, emailSignUp, phone, passwordSignUp, confirmPasswordSignUp, emailSignIn, passwordSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
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
        name = findViewById(R.id.edt_name_sign_up);
        emailSignUp = findViewById(R.id.edt_email_sign_up);
        phone = findViewById(R.id.edt_phone_sign_up);
        passwordSignUp = findViewById(R.id.edt_password_sign_up);
        confirmPasswordSignUp = findViewById(R.id.edt_password_confirm_sign_up);
        emailSignIn = findViewById(R.id.edt_email_sign_in);
        passwordSignIn = findViewById(R.id.edt_password_sign_in);
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
                validateSignInData(emailSignIn.getText().toString(), passwordSignIn.getText().toString());
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_sign_up:
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
                            if(phoneSignUp.length() < 9 ) {
                                if(Objects.equals(password, confirmPassword)) {
                                    showProgress("Signing up", "Please wait, you are signing up..");
                                    signUp(nameSignUp, email, phoneSignUp, password);
                                }else{showToast("Password doesn't match.");}
                            }else{showToast("Please enter a valid mobile number.");}
                        }else{showToast("Please enter a valid email.");}
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
            }else{showToast("Please enter a valid email.");}
        } else{emailSignIn.setError("Field can't be blank.");}

    }

    private void signIn(String email, String password) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("email_id", email)
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
                                         if(Objects.equals(msgType, "Success")){
                                             alertDialog.cancel();
                                             showToast(finalData);
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
    private void showToast(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void signUp(String name, String email, String phone, String password) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", name)
                .addFormDataPart("email_id", email)
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
                                         if(Objects.equals(msgType, "Success")){
                                             alertDialog.cancel();
                                             showToast(finalData);
                                             Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                             startActivity(intent);
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
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
    }
}
