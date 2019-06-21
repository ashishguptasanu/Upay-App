package volunteer.upay.com.upay.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import volunteer.upay.com.upay.Activities.MyCenterActivity;
import volunteer.upay.com.upay.Models.Centers;
import volunteer.upay.com.upay.R;

/**
 * Created by ashish on 11/3/18.
 */

public class AdapterCenters extends RecyclerView.Adapter<AdapterCenters.MyViewHolder> {
    private Context context;
    private List<Centers> mCentersList;
    private List<Centers> mFilteredCentersList;
    private SharedPreferences sharedPreferences;

    public AdapterCenters(Context context) {
        this.context = context;
        this.mCentersList = new ArrayList<>();
        mFilteredCentersList = new ArrayList<>();
    }

    public void onFilterApplied(@NonNull String query) {
        mFilteredCentersList.clear();
        if (TextUtils.isEmpty(query)) {
            mFilteredCentersList = new ArrayList<>(mCentersList);
            notifyDataSetChanged();
        } else {
            for (Centers center : mCentersList) {
                if (center.getCenter_name().toLowerCase().contains(query.toLowerCase()) ||
                        center.getZone_name().toLowerCase().contains(query.toLowerCase())) {
                    mFilteredCentersList.add(center);
                }
            }
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_centers, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvCenterName.setText(mFilteredCentersList.get(position).getCenter_name());
        holder.tvZoneName.setText(mFilteredCentersList.get(position).getZone_name() + " | " + mCentersList.get(position).getCenterType());
    }

    @Override
    public int getItemCount() {
        return mFilteredCentersList.size();
    }

    public void swapCenters(List<Centers> centerList) {
        mCentersList = centerList;
        mFilteredCentersList = new ArrayList<>(mCentersList);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardCenters;
        ImageView imgMoveToCenter;
        TextView tvCenterName, tvZoneName;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardCenters = itemView.findViewById(R.id.card_centers);
            imgMoveToCenter = itemView.findViewById(R.id.img_move_to_center);
            tvCenterName = itemView.findViewById(R.id.tv_center_name);
            tvZoneName = itemView.findViewById(R.id.tv_zone_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, MyCenterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            int centerId = Integer.parseInt(mFilteredCentersList.get(getAdapterPosition()).getCenter_id());
            sharedPreferences.edit().putInt("center_id", centerId).apply();
            intent.putExtra("center_id", String.valueOf(centerId));
            intent.putExtra("center_name", mFilteredCentersList.get(getAdapterPosition()).getCenter_name());
            intent.putExtra("latitude", mFilteredCentersList.get(getAdapterPosition()).getLatitude());
            intent.putExtra("longitude", mFilteredCentersList.get(getAdapterPosition()).getLongitude());
            sharedPreferences.edit().putInt("zone_id", Integer.parseInt(mFilteredCentersList.get(getAdapterPosition()).getZone_id())).apply();
            sharedPreferences.edit().putString("center_name", mFilteredCentersList.get(getAdapterPosition()).getCenter_name()).apply();
            sharedPreferences.edit().putString("zone_name", mFilteredCentersList.get(getAdapterPosition()).getZone_name()).apply();
            sharedPreferences.edit().putString("latitude", mFilteredCentersList.get(getAdapterPosition()).getLongitude()).apply();
            sharedPreferences.edit().putString("longitude", mFilteredCentersList.get(getAdapterPosition()).getLongitude()).apply();
            context.startActivity(intent);
        }
    }
}
