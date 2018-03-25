package volunteer.upay.com.upay.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import volunteer.upay.com.upay.R;

/**
 * Created by ashish on 10/3/18.
 */

public class AdapterZones extends RecyclerView.Adapter<AdapterZones.MyViewHolder> {
    Context context;
    String[] zones;
    public AdapterZones(Context context, String[] zones){
        this.context = context;
        this.zones = zones;
    }
    @NonNull
    @Override
    public AdapterZones.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.btn_zones,parent,false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterZones.MyViewHolder holder, int position) {
        holder.btnRecycler.setText(zones[position]);
    }

    @Override
    public int getItemCount() {
        return zones.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button btnRecycler;
        public MyViewHolder(View itemView) {
            super(itemView);
            btnRecycler = itemView.findViewById(R.id.btn_recycler);
            btnRecycler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Coming Soon..",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
