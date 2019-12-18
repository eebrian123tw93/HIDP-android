package com.brianlu.exhibitionshoppingcart.ShoppingCart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.brianlu.exhibitionshoppingcart.R;
import com.brianlu.exhibitionshoppingcart.model.CartItem;
import com.bumptech.glide.Glide;

import java.util.List;

public class ShoppingCartRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingCartRecyclerViewAdapter.ViewHolder> {

    private Context context;

    private ShoppingCartRecyclerViewHolderPresenter viewHolderPresenter;

    public ShoppingCartRecyclerViewAdapter(Context context) {
        this.context = context;
        this.viewHolderPresenter = new ShoppingCartRecyclerViewHolderPresenter();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return viewHolderPresenter.getItemCount();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        viewHolderPresenter.bindData(holder, position);
    }

    public void addArticles(List<CartItem> articles) {
        viewHolderPresenter.addArticles(articles);
        notifyDataSetChanged();
    }

    public void clear() {
        viewHolderPresenter.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ShoppingCartRecyclerViewHolderView {

        private ImageView productImageView;

        public ViewHolder(View v) {
            super(v);
            productImageView = v.findViewById(R.id.product_imageView);
        }

        @Override
        public void onSetProductItemImageView(String fileName) {

        }
    }


}
