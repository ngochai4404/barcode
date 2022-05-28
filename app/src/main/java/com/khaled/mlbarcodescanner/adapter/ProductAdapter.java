package com.khaled.mlbarcodescanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.khaled.mlbarcodescanner.R;
import com.khaled.mlbarcodescanner.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by haipn on 28/05/2022.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public ProductAdapter(Context context, List<Product> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product p = mData.get(position);
        holder.tvId.setText(p.getId());
        holder.tvName.setText(p.getName());
        holder.tvPrice.setText(p.getPrice()+ " BNB");
        holder.tvAmount.setText(p.getAmount().toString());
        Picasso.get().load(p.getImage())
                .into(holder.img);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvId;
        TextView tvName;
        TextView tvAmount;
        TextView tvPrice;
        ImageView img;

        ViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.id_products);
            tvName = itemView.findViewById(R.id.name_products);
            tvAmount = itemView.findViewById(R.id.code_products);
            tvPrice = itemView.findViewById(R.id.date_products);
            img = itemView.findViewById(R.id.image_products);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}