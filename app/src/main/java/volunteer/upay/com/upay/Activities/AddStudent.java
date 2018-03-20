package volunteer.upay.com.upay.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mikhaellopez.circularimageview.CircularImageView;

import volunteer.upay.com.upay.R;

public class AddStudent extends AppCompatActivity implements View.OnClickListener{
    EditText name, parentName, age, clss, school, comments;
    CircularImageView imgStudent;
    Button btnAddStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        init();
    }

    private void init() {
        name = findViewById(R.id.edt_student_name);
        parentName = findViewById(R.id.edt_student_parent_name);
        age = findViewById(R.id.edt_student_age);
        clss = findViewById(R.id.edt_student_class);
        school = findViewById(R.id.edt_student_school);
        comments = findViewById(R.id.edt_student_comments);
        imgStudent = findViewById(R.id.add_student_image);
        btnAddStudent = findViewById(R.id.btn_add_student);
        imgStudent.setOnClickListener(this);
        btnAddStudent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_student:
                break;
            case R.id.add_student_image:
                break;
        }
    }
}
