package volunteer.upay.com.upay.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import volunteer.upay.com.upay.models.CategoryModel;
import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.manager.NavigationManager;

/**
 * Created by ashish on 11/3/18.
 */

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.MyViewHolder> {
    private Context context;
    private List<CategoryModel> categories;

    public AdapterCategories(Context context, List<CategoryModel> categoryModelList) {
        this.context = context;
        this.categories = categoryModelList;
    }

    @NonNull
    @Override
    public AdapterCategories.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_categories, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCategories.MyViewHolder holder, int position) {
        CategoryModel categoryModel = categories.get(position);
        holder.tvCategories.setText(categoryModel.getCategory());
        Glide.with(context).load(categoryModel.getIconUrl()).into(holder.imgCategories);
        Glide.with(context)
                .load(categoryModel.getBackgroundUrl())
                .placeholder(R.drawable.upay)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.background);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardCategories;
        TextView tvCategories;
        ImageView imgCategories;
        ImageView background;

        MyViewHolder(View itemView) {
            super(itemView);
            cardCategories = itemView.findViewById(R.id.card_categories);
            cardCategories.setOnClickListener(this);
            tvCategories = itemView.findViewById(R.id.tv_categories);
            imgCategories = itemView.findViewById(R.id.image_categories);
            background = itemView.findViewById(R.id.background);
        }

        @Override
        public void onClick(View v) {
            NavigationManager.openCategory(v.getContext(), categories.get(getAdapterPosition()));
        }
    }
}
