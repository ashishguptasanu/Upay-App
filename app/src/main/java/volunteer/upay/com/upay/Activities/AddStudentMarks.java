package volunteer.upay.com.upay.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import java.util.Calendar;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;
import volunteer.upay.com.upay.R;

public class AddStudentMarks extends AppCompatActivity implements VerticalStepperForm {
    String[] mySteps;
    int colorPrimary, colorPrimaryDark;
    VerticalStepperFormLayout verticalStepperForm;
    EditText testName, totalMarks, marksObtained;
    String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_marks);

        mySteps = new String[]{"Test Name", "Marks Obtained", "Total Marks", "Date"};
        colorPrimary = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        colorPrimaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);

        verticalStepperForm =  findViewById(R.id.vertical_stepper_form);

        // Setting up and initializing the form
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, mySteps, this, this)
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .displayBottomNavigation(true) // It is true by default, so in this case this line is not necessary
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
                view = createMarkObtainedStep();
                break;
            case 2:
                view = createTotalMarksStep();
                break;
            case 3:
                view = createTestDateStep();
                break;
        }
        return view;
    }

    private View createTestDateStep() {
        final String[] date = {""};
        Calendar mcurrentDate=Calendar.getInstance();
        final int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth=mcurrentDate.get(Calendar.MONTH);
        int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog mDatePicker=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                selectedDate = selectedyear +"-"+(selectedmonth+1)+"-"+selectedday;
            }
        },mYear, mMonth, mDay);
        mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        //mDatePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDatePicker.setTitle("Select Test Date");
        mDatePicker.show();
        return mDatePicker.getDatePicker();
    }

    private View createTotalMarksStep() {
        totalMarks = new EditText(this);
        totalMarks.setSingleLine(true);
        totalMarks.setHint("Total Marks");
        return totalMarks;

    }

    private View createMarkObtainedStep() {
        marksObtained = new EditText(this);
        marksObtained.setSingleLine(true);
        marksObtained.setHint("Marks Obtained");
        return marksObtained;

    }

    private View createTestNameStep() {
        testName = new EditText(this);
        testName.setSingleLine(true);
        testName.setHint("Name of the Test");
        return testName;

    }

    @Override
    public void onStepOpening(int stepNumber) {
        switch (stepNumber){
            case 0:
            break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                verticalStepperForm.setStepAsCompleted(3);
                break;

        }
    }

    @Override
    public void sendData() {

    }
    private void frontDatePicker() {


    }

}
