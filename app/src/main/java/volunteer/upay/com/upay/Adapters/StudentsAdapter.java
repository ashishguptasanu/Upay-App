package volunteer.upay.com.upay.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


import volunteer.upay.com.upay.Activities.StudentDetails;
import volunteer.upay.com.upay.Models.Student;
import volunteer.upay.com.upay.R;

/**
 * Created by ashish on 19/3/18.
 */

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.MyViewHolder> {
    List<Student> studentList = new ArrayList<>();
    Context context;

    public  StudentsAdapter(Context context, List<Student> studentList){
        this.context = context;
        this.studentList = studentList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_student,parent,false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvStudentName.setText(studentList.get(position).getStudentName());
        holder.tvStudentDetails.setText("Class: "+studentList.get(position).getClss() + " | Age: "+ studentList.get(position).getAge());
        String imgUrl = studentList.get(position).getPhotoUrl();
        imgUrl = imgUrl.replace("\\", "");
        if(!TextUtils.isEmpty(studentList.get(position).getPhotoUrl())){
            Picasso.with(context).load(imgUrl).into(holder.profileImage);
            //Log.d("True", "Yes");
        }else{
            Picasso.with(context).load("http://upay.org.in/api/images_api/student_icon.png").into(holder.profileImage);
        }
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
            btnStudentDetails = itemView.findViewById(R.id.btn_student_details);
            profileImage =  itemView.findViewById(R.id.profile_image);
            btnStudentDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, StudentDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("center_name", studentList.get(getAdapterPosition()).getCenterName());
                    intent.putExtra("zone_name", studentList.get(getAdapterPosition()).getZoneName());
                    intent.putExtra("name", studentList.get(getAdapterPosition()).getStudentName());
                    intent.putExtra("parent_name", studentList.get(getAdapterPosition()).getParentName());
                    intent.putExtra("age", studentList.get(getAdapterPosition()).getAge());
                    intent.putExtra("class", studentList.get(getAdapterPosition()).getClss());
                    intent.putExtra("school", studentList.get(getAdapterPosition()).getSchool());
                    intent.putExtra("photo_url", studentList.get(getAdapterPosition()).getPhotoUrl());
                    intent.putExtra("comments", studentList.get(getAdapterPosition()).getComments());
                    context.startActivity(intent);
                }
            });
        }
    }
}
