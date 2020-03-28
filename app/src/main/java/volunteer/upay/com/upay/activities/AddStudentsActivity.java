package volunteer.upay.com.upay.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import volunteer.upay.com.upay.activities.ui.addstudents.AddStudentsFragment;
import volunteer.upay.com.upay.R;

public class AddStudentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_students_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AddStudentsFragment.newInstance())
                    .commitNow();
        }
    }
}
