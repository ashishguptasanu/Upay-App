package volunteer.upay.com.upay.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import volunteer.upay.com.upay.R;

public class StudentDetails extends AppCompatActivity {
    CircularImageView ccpStudentImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        ccpStudentImage = findViewById(R.id.view_student_image);
        Picasso.with(getApplicationContext()).load("http://upay.org.in/api/images_api/student_icon.png").into(ccpStudentImage);
    }
}
