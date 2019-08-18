package volunteer.upay.com.upay.Activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;
import volunteer.upay.com.upay.Models.GeneralResponseModel;
import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.localDb.VolunteerRepository;
import volunteer.upay.com.upay.rest.RestCallback;
import volunteer.upay.com.upay.rest.RetrofitRequest;

import static volunteer.upay.com.upay.util.Utilities.getHeaderMap;

public class VolunteerDetails extends AppCompatActivity implements RestCallback.RestCallbacks<GeneralResponseModel> {
    TextView volunteerName, upayId, email, zoneName, phone, centerName, accessType;
    String id, name, uid, mail, zone, center, mobile, access, photoUrl;
    CircularImageView ccpVoluImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_details);
        init();
    }

    private void init() {
        id = getIntent().getStringExtra("id");
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
        if (Objects.equals(access, "1")) {
            accessType.setText("Center Level");
        } else if (Objects.equals(access, "2")) {
            accessType.setText("Zonal Level");
        } else if (Objects.equals(access, "3")) {
            accessType.setText("Super Admin");
        } else {
            accessType.setText("None");
        }
        if (!TextUtils.isEmpty(photoUrl)) {
            Glide.with(getApplicationContext()).load(photoUrl).into(ccpVoluImage);
        } else {
            Glide.with(getApplicationContext()).load("http://upay.org.in/api/app_images/volunteer.png").into(ccpVoluImage);
        }
    }

    private void success() {
        Toast.makeText(this, "Successfully Deleted", Toast.LENGTH_LONG).show();
        finish();
    }

    private void failure() {
        Toast.makeText(this, "Some problem occurred, Please try again later", Toast.LENGTH_LONG).show();
    }

    public void deleteVolunteer(View view) {
        new AlertDialog.Builder(this)
                .setMessage("Do you really want to delete " + name+ "?")
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
        VolunteerRepository volunteerRepository = new VolunteerRepository(this);
        volunteerRepository.deleteTask(mail);

        Call<GeneralResponseModel> call = RetrofitRequest.deleteVolunteer(getHeaderMap(), id);
        RestCallback<GeneralResponseModel> restCallback = new RestCallback<>(this, call, this);
        restCallback.executeRequest();
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
