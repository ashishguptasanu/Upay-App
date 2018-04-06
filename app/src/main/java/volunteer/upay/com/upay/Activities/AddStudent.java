package volunteer.upay.com.upay.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import volunteer.upay.com.upay.R;

public class AddStudent extends AppCompatActivity implements View.OnClickListener, PermissionListener{
    EditText edtName, edtParentName, edtAge, edtClss, edtSchool, edtComments;
    CircularImageView imgStudent;
    Button btnAddStudent;
    OkHttpClient client = new OkHttpClient();
    AlertDialog alertDialog;
    Camera camera;
    String filePath = "";
    Uri tempUri;
    SharedPreferences sharedPreferences;
    LinearLayout layoutAccessDenied, layoutAddStudent;
    String centerId, zoneId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        centerId = sharedPreferences.getString("center_id_volu","");
        zoneId = sharedPreferences.getString("zone_id_volu", "");
        init();
    }

    private void init() {
        layoutAccessDenied = findViewById(R.id.layout_background_student);
        layoutAddStudent = findViewById(R.id.layout_add_student);
        if(Objects.equals(Integer.parseInt(sharedPreferences.getString("admin_access","")), 1)){

            if(Objects.equals(centerId, String.valueOf(sharedPreferences.getInt("center_id", 0)))){
                initViews();
            }else{
                layoutAddStudent.setVisibility(View.GONE);
                layoutAccessDenied.setVisibility(View.VISIBLE);
            }
        }
        else if(Objects.equals(Integer.parseInt(sharedPreferences.getString("admin_access","")), 2)){
            if(Objects.equals(zoneId, String.valueOf(sharedPreferences.getInt("zone_id",0)))){
                initViews();
            }else{
                layoutAddStudent.setVisibility(View.GONE);
                layoutAccessDenied.setVisibility(View.VISIBLE);
            }
        }
        else if(Objects.equals(Integer.parseInt(sharedPreferences.getString("admin_access","")), 3)){
            initViews();
        }
        else{
            layoutAddStudent.setVisibility(View.GONE);
            layoutAccessDenied.setVisibility(View.VISIBLE);
        }

    }

    private void initViews() {
        layoutAddStudent.setVisibility(View.VISIBLE);
        layoutAccessDenied.setVisibility(View.GONE);
        edtName = findViewById(R.id.edt_student_name);
        edtParentName = findViewById(R.id.edt_student_parent_name);
        edtAge = findViewById(R.id.edt_student_age);
        edtClss = findViewById(R.id.edt_student_class);
        edtSchool = findViewById(R.id.edt_student_school);
        edtComments = findViewById(R.id.edt_student_comments);
        imgStudent = findViewById(R.id.add_student_image);
        btnAddStudent = findViewById(R.id.btn_add_student);
        imgStudent.setOnClickListener(this);
        btnAddStudent.setOnClickListener(this);
        Picasso.with(getApplicationContext())
                .load("http://upay.org.in/api/images_api/student_icon.png")
                .into(imgStudent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_student:
                validateStudentData(edtName.getText().toString(), edtParentName.getText().toString(), edtAge.getText().toString(), edtClss.getText().toString(), edtSchool.getText().toString(), edtComments.getText().toString());
                break;
            case R.id.add_student_image:
                checkCameraPermissions();
                break;
        }
    }

    private void validateStudentData(String name, String parentName, String age, String clss, String school, String comments) {
        if(!name.isEmpty()) {
            if (!parentName.isEmpty()) {
                if(!age.isEmpty()){
                    if(!clss.isEmpty()) {
                        if(!school.isEmpty()) {
                            if(!comments.isEmpty()) {
                                showProgress("Adding Student", "Please wait, Adding new student..");
                                checkFile(name, parentName, age, clss, school, comments);

                            }else{edtComments.setError("Field can't be blank.");}
                        }else{edtSchool.setError("Please enter a valid email.");}
                    }else{edtClss.setError("Field can't be blank.");}
                }else{ edtAge.setError("Field can't be blank.");}
            } else {edtParentName.setError("Field can't be blank.");}
        }else{edtName.setError("Field can't be blank.");}
    }

    private void checkFile(String name, String parentName, String age, String clss, String school, String comments) {
        if(filePath.length() > 0){
            String timeStamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + "";
            uploadFileToServer(timeStamp, tempUri, name, parentName, age, clss, school, comments);
        }else{
            uploadStudentData(name, parentName, age, clss, school, comments, "");
        }
    }

    private void checkCameraPermissions() {
        TedPermission.with(this)
                .setPermissionListener(this)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void uploadStudentData(String name, String parentName, String age, String clss, String school, String comments, String finalData) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("student_name", name)
                .addFormDataPart("parent_name", parentName)
                .addFormDataPart("age", age)
                .addFormDataPart("class", clss)
                .addFormDataPart("school", school)
                .addFormDataPart("center_name", sharedPreferences.getString("center_name",""))
                .addFormDataPart("center_id", String.valueOf(sharedPreferences.getInt("center_id",0)))
                .addFormDataPart("zone_name", sharedPreferences.getString("zone_name",""))
                .addFormDataPart("zone_id", sharedPreferences.getString("zone_id",""))
                .addFormDataPart("photo_url", finalData)
                .addFormDataPart("comments", comments)
                .addFormDataPart("added_by", sharedPreferences.getString("login_email",""))
                .build();
        Request request = new Request.Builder().url(getResources().getString(R.string.base_url)+ "//submit_students_details.php").addHeader("Token", getResources().getString(R.string.token)).post(requestBody).build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
                         @Override
                         public void onFailure(okhttp3.Call call, IOException e) {
                             System.out.println("Registration Error" + e.getMessage());
                             showToast("Adding Student failed.");
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
                                             Intent intent = new Intent(getApplicationContext(), MyCenterActivity.class);
                                             intent.putExtra("center_id", String.valueOf(sharedPreferences.getInt("center_id",0)));
                                             intent.putExtra("latitude", String.valueOf(sharedPreferences.getString("latitude","")));
                                             intent.putExtra("longitude", String.valueOf(sharedPreferences.getString("longitude","")));
                                             startActivity(intent);
                                             //saveLoginDetails(email);
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

    @Override
    public void onPermissionGranted() {
        initCamera();

    }

    private void initCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    @Override
    public void onPermissionDenied(ArrayList<String> deniedPermissions) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgStudent.setImageBitmap(imageBitmap);
            tempUri = getImageUri(getApplicationContext(), imageBitmap);
            filePath = getRealPathFromURI(tempUri);
            // CALL THIS METHOD TO GET THE ACTUAL PATH
            //File finalFile = new File(getRealPathFromURI(tempUri));

            System.out.println(tempUri);

        }
    }



    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Upay", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    private void uploadFileToServer(String timeStamp, Uri finalFile, final String name, final String parentName, final String age, final String clss, final String school, final String comments) {
        File file = new File(getRealPathFromURI(finalFile));
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file_name", timeStamp + ".jpg")
                .addFormDataPart("file", finalFile.getLastPathSegment(),
                        RequestBody.create(MediaType.parse("image/jpeg"), file))
                .build();
        Request request = new Request.Builder()
                .url(getResources().getString(R.string.base_url)+"/upload_photo.php")
                .addHeader("Token", getResources().getString(R.string.token))
                .post(requestBody).build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
                         @Override
                         public void onFailure(okhttp3.Call call, IOException e) {

                             System.out.println("Registration Error" + e.getMessage());
                             showToast("Adding Student failed.");
                             alertDialog.cancel();
                         }
                         @Override
                         public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                             String resp = response.body().string();
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
                                             /*alertDialog.cancel();
                                             showToast(finalData);*/
                                             uploadStudentData(name, parentName, age, clss, school, comments, finalData);
                                             /*Intent intent = new Intent(getApplicationContext(), MyCenterActivity.class);
                                             startActivity(intent);*/
                                         }else{
                                             showToast(finalData);
                                             alertDialog.cancel();
                                         }
                                     }
                                 } catch (JSONException e) {
                                     showToast("Adding Student failed.");
                                     alertDialog.cancel();
                                     e.printStackTrace();
                                 }
                             }else{
                                 showToast("Adding Student failed.");
                                 alertDialog.cancel();
                             }

                             }
                         }

        );
    }
}
