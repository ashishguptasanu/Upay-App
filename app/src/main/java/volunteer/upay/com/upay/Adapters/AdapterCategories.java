package volunteer.upay.com.upay.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import volunteer.upay.com.upay.R;

/**
 * Created by ashish on 11/3/18.
 */

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.MyViewHolder> {
    Context context;
    String[] categories;
    String[] background;

    public AdapterCategories(Context context, String[] categories, String[] background){
        this.context = context;
        this.categories = categories;
        this.background = background;
    }
    @NonNull
    @Override
    public AdapterCategories.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_categories,parent,false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCategories.MyViewHolder holder, int position) {
        //holder.cardCategories.setCardBackgroundColor(background[position]);
        holder.tvCategories.setText(categories[position]);
        Picasso.with(context).load(background[position]).into(holder.imgCategories);
    }

    @Override
    public int getItemCount() {
        return categories.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardCategories;
        TextView tvCategories;
        ImageView imgCategories;
        public MyViewHolder(View itemView) {
            super(itemView);
            cardCategories = itemView.findViewById(R.id.card_categories);
            tvCategories = itemView.findViewById(R.id.tv_categories);
            imgCategories = itemView.findViewById(R.id.image_categories);
        }
    }
}
