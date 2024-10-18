package com.example.myshop2024.categoty;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myshop2024.R;
import com.example.myshop2024.constants.Urls;
import com.example.myshop2024.dto.CategoryItemDTO;

import java.util.List;

import okhttp3.logging.HttpLoggingInterceptor;

//class for create list categories
public class CategoriesAdapter extends RecyclerView.Adapter<CategoryCardViewHolder> {
    private List<CategoryItemDTO> items;


    public CategoriesAdapter(List<CategoryItemDTO> items) {
        this.items = items;
    }

    //use parent component category_view_item for CategoryCardViewHolder
    @NonNull
    @Override
    public CategoryCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.category_view_item, parent, false);
        return new CategoryCardViewHolder(view);
    }

    //method build list, use ... automatically ... ?
    @Override
    public void onBindViewHolder(@NonNull CategoryCardViewHolder holder, int position) {
        if(items!=null && position<items.size()) {
            CategoryItemDTO item = items.get(position);
            holder.getCategoryName().setText(item.getName());

            //set Id category for delete and edit
            holder.setId(item.getId());
            //get image from server
            String ImageUrl = item.getImagePath();
            ImageUrl = ImageUrl.replace("\\","/");
            String url = Urls.BASE+"/"+ImageUrl;

            Log.v("image url:" , url);

            //String url = Urls.BASE+"/images/"+item.getImagePath();

            Glide.with(holder.itemView.getContext())
                    .load(url)
                    //.apply(new RequestOptions().override(400))
                    .into(holder.getIvCategoryImage());

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}