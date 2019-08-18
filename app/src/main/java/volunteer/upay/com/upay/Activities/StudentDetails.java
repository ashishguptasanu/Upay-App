package volunteer.upay.com.upay.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Response;
import volunteer.upay.com.upay.Models.GeneralResponseModel;
import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.localDb.VolunteerRepository;
import volunteer.upay.com.upay.rest.RestCallback;
import volunteer.upay.com.upay.rest.RetrofitRequest;
import volunteer.upay.com.upay.util.AccessLevel;
import volunteer.upay.com.upay.util.Utilities;

import static volunteer.upay.com.upay.util.Utilities.getHeaderMap;

public class StudentDetails extends AppCompatActivity implements View.OnClickListener, RestCallback.RestCallbacks<GeneralResponseModel> {
    CircularImageView ccpStudentImage;
    String id, centerName, zoneName, name, age, parentName, clss, school, photoUrl, comments;
    Button deleteButton;
    TextView tvStudentName, tvStudentAge, tvCenterName, tvStudentClass, tvStudentSchool, tvStudentParentName, tvZoneName, tvComments;
    CardView cardStudentDetails, cardStudentReports, cardAddMarks, cardViewAttendance;
    LinearLayout layoutStudentDetails;
    ImageView imageExpand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        if (getIntent().getExtras() != null) {
            id = getIntent().getStringExtra("student_id");
            centerName = getIntent().getStringExtra("center_name");
            zoneName = getIntent().getStringExtra("zone_name");
            name = getIntent().getStringExtra("name");
            parentName = getIntent().getStringExtra("parent_name");
            clss = getIntent().getStringExtra("class");
            school = getIntent().getStringExtra("school");
            age = getIntent().getStringExtra("age");
            photoUrl = getIntent().getStringExtra("photo_url");
            comments = getIntent().getStringExtra("comments");
        }
        initViews();

        Glide.with(getApplicationContext()).load("http://upay.org.in/api/images_api/student_icon.png").into(ccpStudentImage);
    }

    private void initViews() {
        deleteButton = findViewById(R.id.deleteButton);
        ccpStudentImage = findViewById(R.id.view_student_image);
        tvStudentName = findViewById(R.id.tv_student_name);
        tvCenterName = findViewById(R.id.tv_student_center_name);
        tvStudentAge = findViewById(R.id.tv_student_age);
        tvStudentClass = findViewById(R.id.tv_student_class);
        tvStudentSchool = findViewById(R.id.tv_student_school);
        tvStudentParentName = findViewById(R.id.tv_student_parent_name);
        tvZoneName = findViewById(R.id.tv_student_zone_name);
        tvComments = findViewById(R.id.tv_student_comments);
        cardStudentDetails = findViewById(R.id.card_student_details);
        cardStudentDetails.setOnClickListener(this);
        cardStudentReports = findViewById(R.id.card_student_report);
        cardStudentReports.setOnClickListener(this);
        cardAddMarks = findViewById(R.id.card_add_student_marks);
        cardAddMarks.setOnClickListener(this);
        cardViewAttendance = findViewById(R.id.card_student_attendance);
        layoutStudentDetails = findViewById(R.id.layout_student_details);
        imageExpand = findViewById(R.id.image_student_details);

        if (Utilities.getAccessLevel(this) == AccessLevel.USER) {
            deleteButton.setVisibility(View.GONE);
        } else {
            deleteButton.setVisibility(View.VISIBLE);
        }

        setDataToViews();

    }

    private void setDataToViews() {
        tvStudentName.setText(name + ", ");
        tvCenterName.setText(centerName);
        tvStudentAge.setText(age);
        tvStudentParentName.setText(parentName);
        tvStudentClass.setText(clss);
        tvStudentSchool.setText(school);
        tvZoneName.setText(zoneName);
        tvComments.setText(comments);
        if (!TextUtils.isEmpty(photoUrl)) {
            Glide.with(getApplicationContext()).load(photoUrl).into(ccpStudentImage);
            //Log.d("True", "Yes");
        } else {
            Glide.with(getApplicationContext()).load("http://upay.org.in/api/images_api/student_icon.png").into(ccpStudentImage);
        }
    }

    public void deleteStudent(View view) {
        new AlertDialog.Builder(this)
                .setMessage("Do you really want to delete " + name + "?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete();
                    }
                })
                .setNegativeButton("No", null)
                .setCancelable(true)
                .show();
    }

    private void delete() {

        Call<GeneralResponseModel> call = RetrofitRequest.deleteStudent(getHeaderMap(), id);
        RestCallback<GeneralResponseModel> restCallback = new RestCallback<>(this, call, this);
        restCallback.executeRequest();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_student_details:
                if (layoutStudentDetails.getVisibility() == View.VISIBLE) {
                    layoutStudentDetails.setVisibility(View.GONE);
                    imageExpand.setImageResource(R.drawable.ic_expand_more_black_24dp);

                } else if (layoutStudentDetails.getVisibility() == View.GONE) {
                    layoutStudentDetails.setVisibility(View.VISIBLE);
                    imageExpand.setImageResource(R.drawable.ic_expand_less_black_24dp);
                }
                break;
            case R.id.card_student_report:
                Intent intent = new Intent(getApplicationContext(), StudentMarksDetails.class);
                intent.putExtra("student_id", id);
                startActivity(intent);
                break;
            case R.id.card_add_student_marks:
                Intent addMarksIntent = new Intent(getApplicationContext(), AddStudentMarks.class);
                addMarksIntent.putExtra("student_id", id);
                startActivity(addMarksIntent);
                break;
            case R.id.card_student_attendance:
                break;
        }
    }

    private void success() {
        Toast.makeText(this, "Successfully Deleted", Toast.LENGTH_LONG).show();
        finish();
    }

    private void failure() {
        Toast.makeText(this, "Some problem occurred, Please try again later", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onResponse(Call<GeneralResponseModel> call, Response<GeneralResponseModel> response) {
        if (response != null && response.isSuccessful()) {
            success();
        } else {
            failure();
        }
    }


    @Override
    public void onFailure(Call<GeneralResponseModel> call, Throwable t) {
        failure();
    }
}
