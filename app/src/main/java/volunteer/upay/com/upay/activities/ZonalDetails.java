package volunteer.upay.com.upay.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;

import java.util.Objects;

import volunteer.upay.com.upay.R;

public class ZonalDetails extends AppCompatActivity {
    TextView tvAddress, tvHeadName, tvHeadPhone, tvCoordinatorName, tvCoordinatorPhone, tvNumCenter, tvNumStudents, tvNumVolunteers;
    ImageButton btnMail;
    String officeAddress, headName, headPhone, coordinatorName, coordinatorPhone, numCenter, numStudent, numVolunteers, zonalMail;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zonal_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getIntent().getStringExtra("zone_name") + " Zone");
        init();


    }

    private void init() {
        zonalMail = getIntent().getStringExtra("zonal_mail");
        officeAddress = getIntent().getStringExtra("office_address");
        headName = getIntent().getStringExtra("head_name");
        headPhone = getIntent().getStringExtra("head_phone");
        coordinatorName = getIntent().getStringExtra("coordinator_name");
        coordinatorPhone = getIntent().getStringExtra("coordinator_phone");
        numCenter = getIntent().getStringExtra("num_center");
        numStudent = getIntent().getStringExtra("num_student");
        numVolunteers = getIntent().getStringExtra("num_volunteer");
        initViews();
    }

    private void initViews() {
        tvAddress = findViewById(R.id.tv_zone_address_office);
        tvHeadName = findViewById(R.id.tv_zone_head_name);
        tvHeadPhone = findViewById(R.id.tv_head_phone);
        tvCoordinatorName = findViewById(R.id.tv_zone_coordinator_name);
        tvCoordinatorPhone = findViewById(R.id.tv_zone_coordinator_phone);
        tvNumCenter = findViewById(R.id.tv_zone_num_center);
        tvNumStudents = findViewById(R.id.tv_zone_num_students);
        tvNumVolunteers = findViewById(R.id.tv_zone_num_volunteer);
        btnMail = findViewById(R.id.btn_zone_mail);
        fab = findViewById(R.id.fab_zone);
        setDataToViews();
    }

    private void setDataToViews() {
        tvAddress.setText(officeAddress);
        tvHeadName.setText(headName);
        tvHeadPhone.setText(headPhone);
        tvCoordinatorName.setText(coordinatorName);
        tvCoordinatorPhone.setText(coordinatorPhone);
        tvNumCenter.setText(numCenter);
        tvNumStudents.setText(numStudent);
        tvNumVolunteers.setText(numVolunteers);
        setListeners();
    }

    private void setListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBottomSheetContact();
            }
        });
        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + zonalMail)); // only email apps should handle this
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            }
        });
    }

    private void openBottomSheetContact() {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setMinimumHeight(100);
        final TextView phone1 = new TextView(this);
        final TextView phone2 = new TextView(this);
        final TextView tvCallTo = new TextView(this);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.setMargins(30, 30, 10, 10); //substitute parameters for left, top, right, bottom
        tvCallTo.setTextSize(16);
        tvCallTo.setLayoutParams(params2);
        tvCallTo.setText("Connect on Call With:");
        linearLayout.addView(tvCallTo);
        linearLayout.addView(phone1);
        linearLayout.addView(phone2);
        phone1.setPadding(60, 20, 10, 20);
        phone2.setPadding(60, 20, 10, 40);
        phone1.setTextColor(Color.parseColor("#000000"));
        phone2.setTextColor(Color.parseColor("#000000"));
        phone1.setTextSize(16);
        phone2.setTextSize(16);
        phone1.setText(headPhone + " (" + headName + ")");
        phone2.setText(coordinatorPhone + " (" + coordinatorName + ")");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 0);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        new BottomSheet.Builder(this)
                .setView(linearLayout)
                .setTitle("Select Consultation Mode")
                .setListener(new BottomSheetListener() {
                    @Override
                    public void onSheetShown(@NonNull final BottomSheet bottomSheet) {
                        phone1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bottomSheet.dismiss();
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" + headPhone));
                                startActivity(intent);

                            }
                        });
                        phone2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bottomSheet.dismiss();
                                Intent intent2 = new Intent(Intent.ACTION_DIAL);
                                intent2.setData(Uri.parse("tel:" + coordinatorPhone));
                                startActivity(intent2);

                            }
                        });
                    }

                    @Override
                    public void onSheetItemSelected(@NonNull BottomSheet bottomSheet, MenuItem menuItem) {

                    }

                    @Override
                    public void onSheetDismissed(@NonNull BottomSheet bottomSheet, @DismissEvent int i) {
                    }
                })
                .show();
    }

}
