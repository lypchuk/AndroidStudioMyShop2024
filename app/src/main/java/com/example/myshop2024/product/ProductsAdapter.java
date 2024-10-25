package com.example.myshop2024.product;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myshop2024.R;
import com.example.myshop2024.categoty.CategoryCardViewHolder;
import com.example.myshop2024.constants.Urls;
import com.example.myshop2024.dto.CategoryItemDTO;
import com.example.myshop2024.dto.ProductItemDTO;

import java.util.List;

//class for create list categories
public class ProductsAdapter extends RecyclerView.Adapter<ProductCardViewHolder> {
    private List<ProductItemDTO> items;



    public ProductsAdapter(List<ProductItemDTO> items) {

        this.items = items;
    }

    //use parent component category_view_item for CategoryCardViewHolder
    @NonNull
    @Override
    public ProductCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.product_view_item, parent, false);
        return new ProductCardViewHolder(view);
    }

    //method build list, use ... automatically ... ?
    @Override
    public void onBindViewHolder(@NonNull ProductCardViewHolder holder, int position) {
        if(items!=null && position<items.size()) {
            ProductItemDTO item = items.get(position);

            holder.getProductName().setText(item.getName());
            holder.setId(item.getId());
            String ImageUrl = item.getImagePath()[0];

            ImageUrl = ImageUrl.replace("\\","/");
            //String url = Urls.BASE+"/"+ImageUrl;
///////////////////////////////////////////////////////////////////////////////////////////////////
            String url = Urls.BASE+ImageUrl;

            Log.v("image url:" , url);

            //String url = Urls.BASE+"/images/"+item.getImagePath();

            Glide.with(holder.itemView.getContext())
                    .load(url)
                    //.apply(new RequestOptions().override(400))
                    .into(holder.getIvProductImage());

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
