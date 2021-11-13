package volunteer.upay.com.upay.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import volunteer.upay.com.upay.adapters.AdapterCategories;
import volunteer.upay.com.upay.adapters.AdapterZones;
import volunteer.upay.com.upay.models.CategoryModel;
import volunteer.upay.com.upay.models.Zones;
import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.manager.NavigationManager;
import volunteer.upay.com.upay.rest.RetrofitAdapter;
import volunteer.upay.com.upay.util.AppConstants;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerView, recyclerViewCategories;
    AdapterZones adapterZones;
    AdapterCategories adapterCategories;
    SharedPreferences sharedPreferences;
    ImageView imgBanner;
    TextView tvName, tvEmail;
    private Boolean exit = false;
    OkHttpClient client = new OkHttpClient();


    List<Zones> zonesList = new ArrayList<>();
    List<String> zonesData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (!isNetworkConnected()) {
            showToast("Please Check Your Internet Connection.");
        } else {
            getZonalDetails("");
        }
//        Log.d("Token:", FirebaseInstanceId.getInstance().getToken());
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences.edit().putInt("center_id", 0).apply();

        initviews();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.removeHeaderView(null);
        View header = navigationView.getHeaderView(0);
        //View header = LayoutInflater.from(this).inflate(R.layout.nav_header_home, null);
        tvName = (TextView) header.findViewById(R.id.tv_name_header);
        tvEmail = (TextView) header.findViewById(R.id.tv_email_header);
        tvName.setText(sharedPreferences.getString("volunteer_name", ""));
        tvEmail.setText(sharedPreferences.getString("login_email", ""));

    }

    private void showToast(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initviews() {
        imgBanner = findViewById(R.id.img_banner);
        Glide.with(getApplicationContext()).load("https://www.upay.org.in/wp-content/uploads/2020/03/volunteers-1200x800.jpg")
                .placeholder(R.drawable.upay)
                .into(imgBanner);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_zones);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterZones = new AdapterZones(getApplicationContext(), zonesList);

        recyclerViewCategories = (RecyclerView) findViewById(R.id.recycler_categories);
//        recyclerViewCategories.setHasFixedSize(true);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapterCategories = new AdapterCategories(getApplicationContext(), getCategoriesList());
        recyclerViewCategories.setAdapter(adapterCategories);
//        recyclerViewCategories.smoothScrollBy(240, 0);
    }

    private List<CategoryModel> getCategoriesList() {
        List<CategoryModel> categoryModels = new ArrayList<>();
        categoryModels.add(new CategoryModel("Centers", "https://www.upay.org.in/wp-content/uploads/2019/01/Screen-Shot-2019-01-06-at-7.00.03-AM.png", "http://upay.org.in/api/app_images/centers.png"));
        categoryModels.add(new CategoryModel("Volunteers", "https://www.upay.org.in/wp-content/uploads/2019/01/volunteers.jpg", "http://upay.org.in/api/app_images/volunteers.png"));
        categoryModels.add(new CategoryModel("Students", "https://www.upay.org.in/wp-content/uploads/2019/01/sponsor-hilds.jpg", "http://upay.org.in/api/app_images/students.png"));
//        categoryModels.add(new CategoryModel("Events", "https://www.upay.org.in/wp-content/uploads/2018/11/g5.png", "http://upay.org.in/api/app_images/star.png"));
        categoryModels.add(new CategoryModel("Contacts", "https://www.upay.org.in/wp-content/uploads/slider5/IMG_4961.jpeg", "http://upay.org.in/api/app_images/contact.png"));
        return categoryModels;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (exit) {
                finishAffinity(); // finish activity
            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            showToast("Coming Soon..");
        } else if (id == R.id.nav_center) {
            NavigationManager.openCategory(this, new CategoryModel("centers", null, null));
        } else if (id == R.id.nav_fee) {
            WebviewActivity.open(this, AppConstants.PAYU_URL, "Pay Membership Fee");
        } else if (id == R.id.nav_policy) {
            WebviewActivity.open(this, "https://upay.org.in/upay-policies", "Upay Policies");
        } else if (id == R.id.nav_website) {
            WebviewActivity.open(this, "https://upay.org.in/", "Upay");
        } else if (id == R.id.footpathshala) {
            WebviewActivity.open(this, "https://www.upay.org.in/footpathshala-syllabus-level/", "Footpathshala Syllabus");
        } else if (id == R.id.reach_and_teach) {
            WebviewActivity.open(this, "https://www.upay.org.in/rt-syllabus/", "Reach & Teach Syllabus");
        } else if (id == R.id.nav_logout) {
            AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
            alertDialog.setTitle("Logout");
            alertDialog.setMessage("are you sure you want to logout??");
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Logout",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            sharedPreferences.edit().putString("login_email", "").apply();
                            sharedPreferences.edit().putString("volunteer_name", "").apply();
                            sharedPreferences.edit().putInt("login", 0).apply();
                            dialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                            startActivity(intent);
                        }
                    });
            alertDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void getZonalDetails(final String zone_id) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("zone_id", zone_id)
                .build();
        Request request = new Request.Builder().url(RetrofitAdapter.BASE_URL + "/get_zone_details.php").addHeader("Token", getResources().getString(R.string.token)).post(requestBody).build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
                         @Override
                         public void onFailure(okhttp3.Call call, IOException e) {
                             System.out.println("Registration Error" + e.getMessage());
                         }

                         @Override
                         public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                             String resp = response.body().string();
                             Log.d("resp", resp);

                             if (response.isSuccessful()) {
                                 JSONObject obj = null;
                                 try {
                                     obj = new JSONObject(resp);
                                     JSONObject obj_response = obj.getJSONObject("Response");
                                     final JSONObject obj_status = obj_response.getJSONObject("status");
                                     final String msgFinal = obj_status.getString("type");
                                     if (Objects.equals(msgFinal, "Success")) {
                                         final JSONObject obj_data = obj_response.getJSONObject("data");
                                         JSONArray center_array = obj_data.getJSONArray("zones");
                                         for (int i = 0; i < center_array.length(); i++) {
                                             JSONObject centerObject = center_array.getJSONObject(i);
                                             String id = centerObject.getString("id");
                                             String zoneId = centerObject.getString("zone_id");
                                             String zoneName = centerObject.getString("zone_name");
                                             String contactEmail = centerObject.getString("contact_email");
                                             String headName = centerObject.getString("head_name");
                                             String headPhone = centerObject.getString("head_phone");
                                             String headCoordinatorName = centerObject.getString("head_coordinator_name");
                                             String headCoordinatorPhone = centerObject.getString("head_coordinator_phone");
                                             String numCenters = centerObject.getString("num_centers");
                                             String zonalOfficeAddress = centerObject.getString("zonal_office_address");
                                             String numVolunteers = centerObject.getString("num_volunteers");
                                             String numStudents = centerObject.getString("num_students");
                                             Zones zone = new Zones(id, zoneId, zoneName, contactEmail, headName, headPhone, headCoordinatorName, headCoordinatorPhone, numCenters, zonalOfficeAddress, numVolunteers, numStudents);
                                             zonesList.add(zone);
                                         }


                                         runOnUiThread(new Runnable() {
                                             @Override
                                             public void run() {

                                                 recyclerView.setAdapter(adapterZones);

                                             }
                                         });
                                     }
                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }
                             }
                         }
                     }
        );
    }
}
