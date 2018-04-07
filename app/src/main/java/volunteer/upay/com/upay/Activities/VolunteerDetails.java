package volunteer.upay.com.upay.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import volunteer.upay.com.upay.R;

public class VolunteerDetails extends AppCompatActivity {
    TextView volunteerName, upayId, email, zoneName, phone, centerName, accessType;
    String name, uid, mail, zone, center, mobile, access, photoUrl;
    CircularImageView ccpVoluImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_details);
        init();
    }

    private void init() {
        name = getIntent().getStringExtra("name");
        uid = getIntent().getStringExtra("uid");
        mail = getIntent().getStringExtra("mail");
        zone = getIntent().getStringExtra("zone");
        center = getIntent().getStringExtra("center");
        mobile = getIntent().getStringExtra("mobile");
        access = getIntent().getStringExtra("access");
        photoUrl = getIntent().getStringExtra("photo_url");
        initViews();
    }

    private void initViews() {
        ccpVoluImage = findViewById(R.id.view_volunteer_image_details);
        volunteerName = findViewById(R.id.tv_volunteer_name_details);
        upayId = findViewById(R.id.tv_volunteer_upay_id_details);
        email = findViewById(R.id.tv_volunteer_email_details);
        zoneName = findViewById(R.id.tv_volunteer_zone_name_details);
        phone = findViewById(R.id.tv_volunteer_phone_details);
        centerName = findViewById(R.id.tv_volunteer_center_name_details);
        accessType = findViewById(R.id.tv_volunteer_access_type_details);
        setDatatoViews();
    }

    private void setDatatoViews() {
        volunteerName.setText(name);
        upayId.setText(uid);
        email.setText(mail);
        zoneName.setText(zone);
        phone.setText(mobile);
        centerName.setText(center);
        if(Objects.equals(access, "1")){
            accessType.setText("Center Level");
        }else if(Objects.equals(access, "2")){
            accessType.setText("Zonal Level");
        }else if(Objects.equals(access, "3")){
            accessType.setText("Super Admin");
        }else {
            accessType.setText("None");
        }
        if(!TextUtils.isEmpty(photoUrl)){
            Picasso.with(getApplicationContext()).load(photoUrl).into(ccpVoluImage);
        }else{
            Picasso.with(getApplicationContext()).load("http://upay.org.in/api/app_images/volunteer.png").into(ccpVoluImage);
        }
    }
}
