package volunteer.upay.com.upay.Activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.Objects;

import volunteer.upay.com.upay.R;

public class AddVolunteer extends AppCompatActivity {
    EditText edtEmail;
    Button btnSubmit;
    FrameLayout frame;
    LinearLayout layoutVolu, layoutBackground;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_volunteer);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        layoutBackground = findViewById(R.id.layout_background);
        layoutVolu = findViewById(R.id.layout_add_volunteer);
        if(Objects.equals(sharedPreferences.getString("admin_access",""), "1")){
            initAddVolunteerView();
        }else{
            layoutVolu.setVisibility(View.GONE);
            layoutBackground.setVisibility(View.VISIBLE);
        }



    }

    private void initAddVolunteerView() {
        layoutVolu.setVisibility(View.VISIBLE);
        layoutBackground.setVisibility(View.GONE);
        edtEmail = findViewById(R.id.edt_add_volunteer);
        btnSubmit = findViewById(R.id.btn_add_student);
    }
}
