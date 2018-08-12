package volunteer.upay.com.upay.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import volunteer.upay.com.upay.Activities.HomeActivity;
import volunteer.upay.com.upay.Activities.MyCenterActivity;
import volunteer.upay.com.upay.Activities.StudentDetails;
import volunteer.upay.com.upay.Models.Student;
import volunteer.upay.com.upay.R;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder> {
    List<Student> studentList = new ArrayList<>();
    Context context;
    FloatingActionButton fabSubmitAttendance;
    SharedPreferences sharedPreferences;
    boolean isItemSelected = false;
    ProgressDialog alertDialog;

    public AttendanceAdapter(Context context, List<Student> studentList, FloatingActionButton fabSubmitAttendance) {
        this.context = context;
        this.studentList = studentList;
        this.fabSubmitAttendance = fabSubmitAttendance;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_student_attendance, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.tvStudentName.setText(studentList.get(position).getStudentName());
        holder.tvStudentDetails.setText("Class: " + studentList.get(position).getClss() + " | Age: " + studentList.get(position).getAge());
        String imgUrl = studentList.get(position).getPhotoUrl();
        imgUrl = imgUrl.replace("\\", "");
        if (!TextUtils.isEmpty(studentList.get(position).getPhotoUrl())) {
            Picasso.with(context).load(imgUrl).into(holder.profileImage);
            //Log.d("True", "Yes");
        } else {
            Picasso.with(context).load("http://upay.org.in/api/images_api/student_icon.png").into(holder.profileImage);
        }
        holder.btnStudentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Status:", String.valueOf(studentList.get(position).isSelected()));
                if(!studentList.get(position).isSelected()){
                    holder.btnStudentDetails.setBackgroundResource(R.drawable.gradient_green);
                    holder.btnStudentDetails.setText("P");
                    studentList.get(position).setSelected(true);
                }else{
                    holder.btnStudentDetails.setBackgroundResource(R.drawable.gradient_red_attendance);
                    holder.btnStudentDetails.setText("A");
                    studentList.get(position).setSelected(false);
                }


            }
        });


        fabSubmitAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                try {
                    jsonObject.put("added_by",sharedPreferences.getString("login_email",""));
                    jsonObject.put("center_id", String.valueOf(sharedPreferences.getInt("center_id", 0)));
                    JSONArray attendanceArray = new JSONArray();

                    for(int i=0; i<studentList.size(); i++){
                        JSONObject attendanceObject = new JSONObject();
                        attendanceObject.put("student_id", studentList.get(i).getId());

                        if(studentList.get(i).isSelected()){
                            isItemSelected = true;
                            attendanceObject.put("status", "1");
                        }
                        else{
                            attendanceObject.put("status", "0");
                        }
                        attendanceArray.put(attendanceObject);
                        jsonObject.put("students",attendanceArray);


                    }
                    if(isItemSelected){
                        postAttendanceData(jsonObject);
                    }else{
                        Toast.makeText(context, "No Students Selected", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void postAttendanceData(final JSONObject jsonObject) {
        showToast("Please Wait..");
        OkHttpClient client = new OkHttpClient();
        String POST_URL = context.getResources().getString(R.string.base_url)+ "/submit_student_attendance.php";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        okhttp3.RequestBody requestBody = RequestBody.create(JSON,jsonObject.toString());
        Request request = new Request.Builder().url(POST_URL)
                .addHeader("Token","d75542712c868c1690110db641ba01a")
                .post(requestBody).build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
                         @Override
                         public void onFailure(okhttp3.Call call, IOException e) {
                             postAttendanceData(jsonObject);
                             System.out.println("Registration Error" + e.getMessage());
                             showToast("Something Went Wrong!");
                         }
                         @Override
                         public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                             String resp = response.body().string();
                             Log.d("resp",resp);

                             if (response.isSuccessful()) {
                                 JSONObject obj = null;
                                 try {
                                     obj = new JSONObject(resp);
                                     JSONObject obj_response=obj.getJSONObject("Response");
                                     final JSONObject obj_status=obj_response.getJSONObject("status");
                                     final String msgFinal = obj_status.getString("type");
                                     if(Objects.equals(msgFinal, "Success")){
                                         final JSONObject obj_data=obj_response.getJSONObject("data");
                                         String msgType = obj_data.getString("type");
                                         if(Objects.equals(msgType, "Success")){
                                             Intent myCenterIntent = new Intent(context, MyCenterActivity.class);
                                             myCenterIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                             myCenterIntent.putExtra("center_id", String.valueOf(sharedPreferences.getInt("center_id",0)));
                                             myCenterIntent.putExtra("latitude", String.valueOf(sharedPreferences.getString("latitude","")));
                                             myCenterIntent.putExtra("longitude", String.valueOf(sharedPreferences.getString("longitude","")));
                                             context.startActivity(myCenterIntent);
                                         }else{
                                             showToast("Something Went Wrong!");
                                         }
                                     }
                                 } catch (JSONException e) {
                                     showToast("Something Went Wrong!");

                                     e.printStackTrace();
                                 }
                             }else{
                                 showToast("Something Went Wrong!");
                             }

                         }
                     }
        );
    }

    private void showToast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName, tvStudentDetails;
        Button btnStudentDetails;
        CircularImageView profileImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tv_student_name);
            tvStudentDetails = itemView.findViewById(R.id.tv_student_details);
            btnStudentDetails = itemView.findViewById(R.id.btn_student_attendance);
            profileImage = itemView.findViewById(R.id.profile_image);

        }
    }
}