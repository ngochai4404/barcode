package com.khaled.mlbarcodescanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.khaled.mlbarcodescanner.R;
import com.khaled.mlbarcodescanner.model.Order;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by haipn on 28/05/2022.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<Order> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public OrderAdapter(Context context, List<Order> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_oder, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order p = mData.get(position);
        holder.tvId.setText(p.getId());
        holder.tvName.setText(p.getName());
        holder.tvPrice.setText(p.getTx());
        holder.tvAmount.setText(p.getDate());
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
            tvId = itemView.findViewById(R.id.id_order);
            tvName = itemView.findViewById(R.id.name_order);
            tvAmount = itemView.findViewById(R.id.date_order);
            tvPrice = itemView.findViewById(R.id.tx);
            img = itemView.findViewById(R.id.image_order);
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