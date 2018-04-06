package volunteer.upay.com.upay.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import volunteer.upay.com.upay.R;

public class StudentDetails extends AppCompatActivity {
    CircularImageView ccpStudentImage;
    String centerName, zoneName, name, age, parentName, clss, school, photoUrl, comments;
    TextView tvStudentName, tvStudentAge, tvCenterName, tvStudentClass, tvStudentSchool, tvStudentParentName, tvZoneName, tvComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        if(getIntent().getExtras() != null){
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

        Picasso.with(getApplicationContext()).load("http://upay.org.in/api/images_api/student_icon.png").into(ccpStudentImage);
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
        setDataToViews();

    }

    private void setDataToViews() {

    }
}
