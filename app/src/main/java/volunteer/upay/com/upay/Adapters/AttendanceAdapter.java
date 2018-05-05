package volunteer.upay.com.upay.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import volunteer.upay.com.upay.Activities.StudentDetails;
import volunteer.upay.com.upay.Models.Student;
import volunteer.upay.com.upay.R;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder> {
    List<Student> studentList = new ArrayList<>();
    Context context;
    FloatingActionButton fabSubmitAttendance;

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
                for(int i=0; i<studentList.size(); i++){
                    if(studentList.get(i).isSelected()){
                        Log.d("isSelected:", String.valueOf(i));
                    }
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