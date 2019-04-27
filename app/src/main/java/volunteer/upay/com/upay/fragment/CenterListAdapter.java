package volunteer.upay.com.upay.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import volunteer.upay.com.upay.Models.Centers;
import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.fragment.CenterListFragment.OnCenterSelectListener;

import java.util.ArrayList;
import java.util.List;


public class CenterListAdapter extends RecyclerView.Adapter<CenterListAdapter.ViewHolder> {

    private final OnCenterSelectListener mListener;
    private List<Centers> mValues;

    public CenterListAdapter(List<Centers> items, OnCenterSelectListener listener) {
        mValues = items;
        if (mValues == null) {
            mValues = new ArrayList<>();
        }
        mListener = listener;
    }

    public void setItems(@NonNull List<Centers> centerList) {
        mValues = centerList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_centeritem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getCenter_name());
        holder.mContentView.setText(mValues.get(position).getCenter_address());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onCenterSelect(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public Centers mItem;

        public ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

    }
}
