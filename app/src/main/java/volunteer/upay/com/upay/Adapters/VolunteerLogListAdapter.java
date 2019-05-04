package volunteer.upay.com.upay.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import volunteer.upay.com.upay.Models.VolunteerLogModel;
import volunteer.upay.com.upay.R;

public class VolunteerLogListAdapter extends RecyclerView.Adapter<VolunteerLogListAdapter.MyViewHolder> {
    private List<VolunteerLogModel> mList;

    public VolunteerLogListAdapter() {
        mList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.volunteer_log_item, parent, false);
        return new MyViewHolder(view);
    }

    public void swapItems(List<VolunteerLogModel> modelList) {
        mList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VolunteerLogModel model = mList.get(position);
        holder.date.setText(model.getTimestmp());
        holder.subject.setText(model.getSubject());

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView subject;

        public MyViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            subject = itemView.findViewById(R.id.subject);
        }
    }
}
