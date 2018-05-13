package volunteer.upay.com.upay.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import volunteer.upay.com.upay.R;

public class StudentDetails extends AppCompatActivity {
    CircularImageView ccpStudentImage;
    String id, centerName, zoneName, name, age, parentName, clss, school, photoUrl, comments;
    TextView tvStudentName, tvStudentAge, tvCenterName, tvStudentClass, tvStudentSchool, tvStudentParentName, tvZoneName, tvComments;
    TextView tvMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        if(getIntent().getExtras() != null){
            id = getIntent().getStringExtra("student_id");
            centerName = getIntent().getStringExtra("center_name");
            zoneName = getIntent().getStringExtra("zone_name");
            name = getIntent().getStringExtra("name");
            parentName = getIntent().getStringExtra("parent_name");
            clss  = getIntent().getStringExtra("class");
            school = getIntent().getStringExtra("school");
            age = getIntent().getStringExtra("age");
            photoUrl = getIntent().getStringExtra("photo_url");
            comments = getIntent().getStringExtra("comments");
        }
        initViews();

        Glide.with(getApplicationContext()).load("http://upay.org.in/api/images_api/student_icon.png").into(ccpStudentImage);
    }

    private void initViews() {
        ccpStudentImage = findViewById(R.id.view_student_image);
        tvStudentName = findViewById(R.id.tv_student_name);
        tvCenterName = findViewById(R.id.tv_student_center_name);
        tvStudentAge = findViewById(R.id.tv_student_age);
        tvStudentClass = findViewById(R.id.tv_student_class);
        tvStudentSchool = findViewById(R.id.tv_student_school);
        tvStudentParentName = findViewById(R.id.tv_student_parent_name);
        tvZoneName = findViewById(R.id.tv_student_zone_name);
        tvComments = findViewById(R.id.tv_student_comments);
        tvMarks = findViewById(R.id.btn_view_marks);
        tvMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudentMarksDetails.class);
                intent.putExtra("student_id", id);
                startActivity(intent);
            }
        });
        setDataToViews();

    }

    private void setDataToViews(){
        tvStudentName.setText(name + ", ");
        tvCenterName.setText(centerName);
        tvStudentAge.setText(age);
        tvStudentParentName.setText(parentName);
        tvStudentClass.setText(clss);
        tvStudentSchool.setText(school);
        tvZoneName.setText(zoneName);
        tvComments.setText(comments);
        if(!TextUtils.isEmpty(photoUrl)){
            Glide.with(getApplicationContext()).load(photoUrl).into(ccpStudentImage);
            //Log.d("True", "Yes");
        }else{
            Glide.with(getApplicationContext()).load("http://upay.org.in/api/images_api/student_icon.png").into(ccpStudentImage);
        }

    }
}
