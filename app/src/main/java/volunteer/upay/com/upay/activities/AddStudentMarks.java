package volunteer.upay.com.upay.activities;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import volunteer.upay.com.upay.R;

public class AddStudentMarks extends AppCompatActivity implements VerticalStepperForm {
    private VerticalStepperFormLayout verticalStepperForm;
    private  EditText testName, testMarks, marksObtained, testDate;
    OkHttpClient client = new OkHttpClient();
    private  ProgressDialog alertDialog;
    String studentId;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_marks);
        String[] mySteps = {"Name of Test", "Total Marks", "Marks Obtained", "Date of Test"};
        int colorPrimary = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);

        studentId = getIntent().getStringExtra("student_id");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // Finding the view
        verticalStepperForm = findViewById(R.id.vertical_stepper_form);

        // Setting up and initializing the form
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, mySteps, this, this)
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .displayBottomNavigation(false)
                .init();


    }

    @Override
    public View createStepContentView(int stepNumber) {
        View view = null;
        switch (stepNumber) {
            case 0:
                view = createTestNameStep();
                break;
            case 1:
                view = createTotalMarksStep();
                break;
            case 2:
                view = createMarksObtainedStep();
                break;
            case 3:
                view = createTestDateStep();
                break;
        }
        return view;
    }

    private View createTestDateStep() {
        testDate = new EditText(this);
        testDate.setSingleLine(true);
        testDate.setHint("Enter Test Date");
        testDate.setFocusable(false);
        testDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(testDate);
            }
        });
        return testDate;
    }

    private View createTestNameStep() {
        // Here we generate programmatically the view that will be added by the system to the step content layout
        testName = new EditText(this);
        testName.setSingleLine(true);
        testName.setHint("Enter Test Name");

        return testName;
    }

    private View createTotalMarksStep() {
        testMarks = new EditText(this);
        testMarks.setSingleLine(true);
        testMarks.setHint("Enter Test Marks");

        return testMarks;


    }

    private View createMarksObtainedStep() {
        marksObtained = new EditText(this);
        marksObtained.setSingleLine(true);
        marksObtained.setHint("Enter Marks Obtained");

        return marksObtained;


    }

    @Override
    public void onStepOpening(int stepNumber) {
        switch (stepNumber) {
            case 0:
                checkTestName();
                break;
            case 1:
               checkTestMarks();
                break;
            case 2:
                checkMarksObtained();
                break;
            case 3:
                checkDate();
                break;
        }
    }

    private void checkDate() {
        verticalStepperForm.setActiveStepAsCompleted();
    }

    private void checkMarksObtained() {
        verticalStepperForm.setActiveStepAsCompleted();
    }

    private void checkTestMarks() {
        verticalStepperForm.setActiveStepAsCompleted();
    }

    private void checkTestName() {
        verticalStepperForm.setActiveStepAsCompleted();
    }

    @Override
    public void sendData() {
        if(!TextUtils.isEmpty(testDate.getText()) && !TextUtils.isEmpty(testName.getText()) && !TextUtils.isEmpty(testMarks.getText()) && !TextUtils.isEmpty(marksObtained.getText())){
            showProgress("", "Please wait..");
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("student_id", studentId)
                    .addFormDataPart("test_name", testName.getText().toString())
                    .addFormDataPart("marks_obtained", marksObtained.getText().toString())
                    .addFormDataPart("total_marks", testMarks.getText().toString())
                    .addFormDataPart("submitted_date", testDate.getText().toString())
                    .addFormDataPart("submitted_by", sharedPreferences.getString("login_email",""))
                    .build();
            Request request = new Request.Builder().url(getResources().getString(R.string.base_url)+ "/submit_student_marks.php").addHeader("Token", getResources().getString(R.string.token)).post(requestBody).build();
            okhttp3.Call call = client.newCall(request);
            call.enqueue(new okhttp3.Callback() {
                             @Override
                             public void onFailure(okhttp3.Call call, IOException e) {
                                 System.out.println("Registration Error" + e.getMessage());
                                 showToast("failed.");
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
                                                 Intent intent = new Intent(getApplicationContext(), StudentMarksDetails.class);
                                                 intent.putExtra("student_id", studentId);
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
        }else{
            showToast("Field's can not be empty");
        }

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
    private void datePicker(final EditText edtDate1) {
        Calendar mcurrentDate=Calendar.getInstance();
        final int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth=mcurrentDate.get(Calendar.MONTH);
        int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog mDatePicker=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                edtDate1.setText(selectedyear +"-"+(selectedmonth+1)+"-"+selectedday);
            }
        },mYear, mMonth, mDay);
        mDatePicker.setTitle("Select date");
        mDatePicker.show();
    }
}
