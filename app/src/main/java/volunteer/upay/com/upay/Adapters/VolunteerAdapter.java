package volunteer.upay.com.upay.Adapters;

/**
 * Created by ashish on 25/3/18.
 */

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import volunteer.upay.com.upay.Activities.VolunteerDetails;
import volunteer.upay.com.upay.Models.Student;
import volunteer.upay.com.upay.Models.Volunteer;
import volunteer.upay.com.upay.R;

import android.content.Context;
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


import volunteer.upay.com.upay.Models.Student;
import volunteer.upay.com.upay.R;

/**
 * Created by ashish on 19/3/18.
 */

public class VolunteerAdapter extends RecyclerView.Adapter<VolunteerAdapter.MyViewHolder> {
    List<Volunteer> volunteerList = new ArrayList<>();
    Context context;

    public  VolunteerAdapter(Context context, List<Volunteer> volunteerList){
        this.context = context;
        this.volunteerList = volunteerList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_volunteers,parent,false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvVolunteerName.setText(volunteerList.get(position).getName());
        holder.tvVolunteerDetails.setText("Zone: "+volunteerList.get(position).getZone_name() + " | Center: "+ volunteerList.get(position).getCenter_name());
        String imgUrl = volunteerList.get(position).getPhotoUrl();
        imgUrl = imgUrl.replace("\\", "");
        if(!TextUtils.isEmpty(volunteerList.get(position).getPhotoUrl())){
            Picasso.with(context).load(imgUrl).into(holder.profileImage);
            //Log.d("True", "Yes");
        }else{
            Picasso.with(context).load("http://upay.org.in/api/app_images/volunteer.png").into(holder.profileImage);
        }
    }

    @Override
    public int getItemCount() {
        return volunteerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvVolunteerName, tvVolunteerDetails;
        Button btnVolunteerDetails;
        CircularImageView profileImage;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvVolunteerName = itemView.findViewById(R.id.tv_volunteer_name);
            tvVolunteerDetails = itemView.findViewById(R.id.tv_volunteer_details);
            btnVolunteerDetails = itemView.findViewById(R.id.btn_volunteer_details);
            profileImage =  itemView.findViewById(R.id.volunteer_profile_image);
            btnVolunteerDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, VolunteerDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("name", volunteerList.get(getAdapterPosition()).getName());
                    intent.putExtra("uid", volunteerList.get(getAdapterPosition()).getUpay_id());
                    intent.putExtra("zone", volunteerList.get(getAdapterPosition()).getZone_name());
                    intent.putExtra("center", volunteerList.get(getAdapterPosition()).getCenter_name());
                    intent.putExtra("mobile", volunteerList.get(getAdapterPosition()).getPhone());
                    intent.putExtra("mail", volunteerList.get(getAdapterPosition()).getEmail_id());
                    intent.putExtra("access", volunteerList.get(getAdapterPosition()).getAdmin_access());
                    intent.putExtra("photo_url", volunteerList.get(getAdapterPosition()).getPhotoUrl());
                    context.startActivity(intent);//TODO
                }
            });
        }
    }
}

