package volunteer.upay.com.upay.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;
import volunteer.upay.com.upay.models.Centers;
import volunteer.upay.com.upay.models.Volunteer;
import volunteer.upay.com.upay.models.VolunteerLogModel;
import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.util.AccessLevel;
import volunteer.upay.com.upay.widgets.CircularDateView;

import static volunteer.upay.com.upay.util.FetchUtils.getCenterNameFromId;
import static volunteer.upay.com.upay.util.FetchUtils.getVolunteerName;

public class VolunteerLogListAdapter extends RecyclerView.Adapter<VolunteerLogListAdapter.MyViewHolder> {
    private List<VolunteerLogModel> mList;
    private AccessLevel mAccessLevel;

    public VolunteerLogListAdapter(AccessLevel accessLevel) {
        mList = new ArrayList<>();
        this.mAccessLevel = accessLevel;
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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        VolunteerLogModel model = mList.get(position);
        holder.date.setTimestamp(Long.parseLong(model.getTimestmp()));

        String noOfStudents = model.getNo_of_students();
        if (TextUtils.isEmpty(noOfStudents)) {
            holder.work_done.setText(model.getWork_done());
        } else {
            holder.work_done.setText(noOfStudents + " Students (" + model.getWork_done() + ")");
        }

        holder.time.setText(model.getClass_taught() + " (" + model.getIn_time() + " - " + model.getOut_time() + ")");
        if (mAccessLevel != AccessLevel.USER) {
            getVolunteerName(holder.itemView.getContext(), model.getVolunteer_id()).onSuccess(new Continuation<Volunteer, Object>() {
                @Override
                public Object then(Task<Volunteer> task) throws Exception {
                    holder.subject.setText(task.getResult().getName());
                    return null;
                }
            });
            holder.center_name.setText(model.getSubject());
        } else {
            getCenterNameFromId(holder.itemView.getContext(), model.getCenter_id()).onSuccess(new Continuation<Centers, Object>() {
                @Override
                public Object then(Task<Centers> task) throws Exception {
                    holder.center_name.setText(task.getResult().getCenter_name());
                    return null;
                }
            });
            holder.subject.setText(model.getSubject());
        }

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircularDateView date;
        private TextView subject, work_done, time, center_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            subject = itemView.findViewById(R.id.subject);
            center_name = itemView.findViewById(R.id.center_name);
            time = itemView.findViewById(R.id.time);
            work_done = itemView.findViewById(R.id.work_done);
        }
    }
}