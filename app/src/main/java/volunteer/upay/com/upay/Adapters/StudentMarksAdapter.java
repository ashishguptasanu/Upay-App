package volunteer.upay.com.upay.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
import ss.com.bannerslider.views.indicators.CircleIndicator;
import volunteer.upay.com.upay.Models.Marks;
import volunteer.upay.com.upay.R;

public class StudentMarksAdapter extends RecyclerView.Adapter<StudentMarksAdapter.MyViewHolder> {
    private Context context;
    private List<Marks> marksList;

    public StudentMarksAdapter(Context context, List<Marks> marksList) {
        this.context = context;
        this.marksList = marksList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_student_marks, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvTestName.setText(marksList.get(position).getTestName());
        holder.tvTestDate.setText(marksList.get(position).getSybmittedDate());
        holder.tvTestMarks.setText(" (" + marksList.get(position).getMarksObtained() + "/" + marksList.get(position).getTotalMarks() + ")");
        try {
            float marksObtained = Float.parseFloat(marksList.get(position).getMarksObtained());
            float totalMarks = Float.parseFloat(marksList.get(position).getTotalMarks());
            float percentage = (marksObtained * 100) / totalMarks;
            holder.percentage.setProgress((int) percentage, 100);
        } catch (NumberFormatException e) {
            holder.percentage.setProgress(0, 100);
        }
    }

    @Override
    public int getItemCount() {
        return marksList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTestName, tvTestMarks, tvTestDate;
        CircularProgressIndicator percentage;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTestName = itemView.findViewById(R.id.tv_test_name);
            tvTestMarks = itemView.findViewById(R.id.tv_marks);
            tvTestDate = itemView.findViewById(R.id.tv_test_date);
            percentage = itemView.findViewById(R.id.circular_progress_marks);
        }
    }
}
