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
import volunteer.upay.com.upay.Activities.MyCenterActivity;
import volunteer.upay.com.upay.Models.Student;
import volunteer.upay.com.upay.R;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder> {
    private List<Student> studentList;
    private Context context;
    private FloatingActionButton fabSubmitAttendance;
    private SharedPreferences sharedPreferences;
    private boolean isItemSelected = false;
    private ProgressDialog alertDialog;

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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Student student = studentList.get(position);
        holder.tvStudentName.setText(student.getStudentName());
        holder.tvStudentDetails.setText("Class: " + student.getClss() + " | Age: " + student.getAge());
        String imgUrl = student.getPhotoUrl();
        imgUrl = imgUrl.replace("\\", "");
        if (!TextUtils.isEmpty(student.getPhotoUrl())) {
            Picasso.with(context).load(imgUrl).into(holder.profileImage);
            //Log.d("True", "Yes");
        } else {
            Picasso.with(context).load("http://upay.org.in/api/images_api/student_icon.png").into(holder.profileImage);
        }

        if (student.isSelected()) {
            holder.btnStudentDetails.setBackgroundResource(R.drawable.gradient_green);
            holder.btnStudentDetails.setText("P");
        } else {
            holder.btnStudentDetails.setBackgroundResource(R.drawable.gradient_red_attendance);
            holder.btnStudentDetails.setText("A");
        }

        holder.btnStudentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!student.isSelected()) {
                    holder.btnStudentDetails.setBackgroundResource(R.drawable.gradient_green);
                    holder.btnStudentDetails.setText("P");
                    student.setSelected(true);
                } else {
                    holder.btnStudentDetails.setBackgroundResource(R.drawable.gradient_red_attendance);
                    holder.btnStudentDetails.setText("A");
                    student.setSelected(false);
                }
            }
        });
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