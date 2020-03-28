package volunteer.upay.com.upay.adapters;

/**
 * Created by ashish on 25/3/18.
 */

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import volunteer.upay.com.upay.activities.VolunteerDetails;
import volunteer.upay.com.upay.models.Volunteer;
import volunteer.upay.com.upay.R;

/**
 * Created by ashish on 19/3/18.
 */

public class VolunteerAdapter extends RecyclerView.Adapter<VolunteerAdapter.MyViewHolder> {
    private List<Volunteer> volunteerList = new ArrayList<>();
    private List<Volunteer> filteredVolunteerList = new ArrayList<>();
    private Context context;

    public VolunteerAdapter(Context context, List<Volunteer> volunteerList) {
        this.context = context;
        this.volunteerList = volunteerList;
        this.filteredVolunteerList = new ArrayList<>(volunteerList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_volunteers, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    public void changeText(CharSequence text) {
        filteredVolunteerList.clear();
        if (TextUtils.isEmpty(text)) {
            filteredVolunteerList = new ArrayList<>(volunteerList);
        } else {
            for (Volunteer volunteer : volunteerList) {
                if (volunteer != null && volunteer.containText(text.toString())) {
                    filteredVolunteerList.add(volunteer);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvVolunteerName.setText(filteredVolunteerList.get(position).getName());
        holder.tvVolunteerDetails.setText("Zone: " + filteredVolunteerList.get(position).getZone_name() + " | Center: " + filteredVolunteerList.get(position).getCenter_name());
        String imgUrl = filteredVolunteerList.get(position).getPhotoUrl();
        imgUrl = imgUrl.replace("\\", "");
        if (!TextUtils.isEmpty(filteredVolunteerList.get(position).getPhotoUrl())) {
            Glide.with(context).load(imgUrl).into(holder.profileImage);
            //Log.d("True", "Yes");
        } else {
            Glide.with(context).load("http://upay.org.in/api/app_images/volunteer.png").into(holder.profileImage);
        }
    }

    @Override
    public int getItemCount() {
        return filteredVolunteerList.size();
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
            profileImage = itemView.findViewById(R.id.volunteer_profile_image);
            btnVolunteerDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, VolunteerDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Volunteer volunteer = filteredVolunteerList.get(getAdapterPosition());
                    intent.putExtra("id", volunteer.getId());
                    intent.putExtra("name", volunteer.getName());
                    intent.putExtra("uid", volunteer.getUpay_id());
                    intent.putExtra("zone", volunteer.getZone_name());
                    intent.putExtra("center", volunteer.getCenter_name());
                    intent.putExtra("mobile", volunteer.getPhone());
                    intent.putExtra("mail", volunteer.getEmail_id());
                    intent.putExtra("access", volunteer.getAdmin_access());
                    intent.putExtra("photo_url", volunteer.getPhotoUrl());
                    context.startActivity(intent);//TODO
                }
            });
        }
    }
}

