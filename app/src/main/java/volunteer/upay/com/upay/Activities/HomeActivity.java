package volunteer.upay.com.upay.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;
import volunteer.upay.com.upay.Adapters.AdapterCategories;
import volunteer.upay.com.upay.Adapters.AdapterZones;
import volunteer.upay.com.upay.R;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerView, recyclerViewCategories;
    AdapterZones adapterZones;
    AdapterCategories adapterCategories;
    SharedPreferences sharedPreferences;
    ImageView imgBanner;
    TextView tvName, tvEmail;
    private Boolean exit = false;
    String[] zones = new String[]{"Nagpur", "Delhi","Gurgaon","Pune", "Mauda"};
    String[] categories = new String[]{"Events", "Volunteers", "Students", "Centers", "Contacts"};
    String[] categories_background = new String[]{
            "http://upay.org.in/api/app_images/star.png",
            "http://upay.org.in/api/app_images/volunteers.png",
            "http://upay.org.in/api/app_images/students.png",
            "http://upay.org.in/api/app_images/centers.png",
            "http://upay.org.in/api/app_images/contact.png"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if(!isNetworkConnected()){
            showToast("Please Check Your Internet Connection.");
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences.edit().putInt("center_id",0).apply();

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
        /*navigationView.removeHeaderView(null);
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_home, null);
        tvName = (TextView)navigationView.findViewById(R.id.tv_name_header);
        tvEmail = (TextView)navigationView.findViewById(R.id.tv_email_header);
        tvName.setText(sharedPreferences.getString("volunteer_name",""));
        tvEmail.setText(sharedPreferences.getString("login_email",""));*/

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
        /*BannerSlider bannerSlider = (BannerSlider) findViewById(R.id.banner_slider1);
        List<Banner> banners=new ArrayList<>();
        //add banner using image url
        banners.add(new RemoteBanner("http://upay.org.in/images/vinay/IMG_8480.JPG"));
        banners.add(new RemoteBanner("http://upay.org.in/images/vinay/IMG-20170813-WA0000.jpg"));
        banners.add(new RemoteBanner("http://upay.org.in/images/vinay/DSC_0194.JPG"));
        banners.add(new RemoteBanner("http://upay.org.in/images/vinay/IMG_20170319_074547.jpg"));
        banners.add(new RemoteBanner("http://upay.org.in/images/vinay/IMG-20170926-WA0037.jpg"));

        //add banner using resource drawable
        //banners.add(new DrawableBanner(R.drawable.yourDrawable));
        bannerSlider.setBanners(banners);*/
        imgBanner = findViewById(R.id.img_banner);
        Picasso.with(getApplicationContext()).load("http://upay.org.in/images/vinay/IMG_8480.JPG").into(imgBanner);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_zones);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterZones = new AdapterZones(getApplicationContext(), zones);
        recyclerView.setAdapter(adapterZones);
        recyclerViewCategories = (RecyclerView)findViewById(R.id.recycler_categories);
        recyclerViewCategories.setHasFixedSize(true);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterCategories = new AdapterCategories(getApplicationContext(), categories, categories_background);
        recyclerViewCategories.setAdapter(adapterCategories);
        recyclerViewCategories.smoothScrollBy(240,0);
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
        getMenuInflater().inflate(R.menu.home, menu);
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
        } else if (id == R.id.nav_attendance) {
            showToast("Coming Soon..");
        } else if (id == R.id.nav_center) {
            showToast("Coming Soon..");
        } else if (id == R.id.nav_fee) {
            showToast("Coming Soon..");
        } else if (id == R.id.nav_policy) {
            showToast("Coming Soon..");
        } else if (id == R.id.nav_website) {
            showToast("Coming Soon..");
        }else if (id == R.id.nav_logout) {
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
                            sharedPreferences.edit().putString("login_email","").apply();
                            sharedPreferences.edit().putString("volunteer_name","").apply();
                            sharedPreferences.edit().putInt("login", 0).apply();
                            dialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
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
}
