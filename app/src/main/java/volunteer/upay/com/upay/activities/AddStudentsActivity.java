package volunteer.upay.com.upay.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import volunteer.upay.com.upay.activities.ui.addstudents.AddStudentsFragment;
import volunteer.upay.com.upay.R;

public class AddStudentsActivity extends AppCompatActivity {

    private AddStudentsFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_students_activity);
        if (savedInstanceState == null) {
            mFragment = AddStudentsFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mFragment)
                    .commitNow();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_student_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.upload) {
            mFragment.uploadStudentsData();
        }
        return super.onOptionsItemSelected(item);
    }
}
