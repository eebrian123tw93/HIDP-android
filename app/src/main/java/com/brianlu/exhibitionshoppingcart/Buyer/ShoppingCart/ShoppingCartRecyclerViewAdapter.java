package com.brianlu.exhibitionshoppingcart.Buyer.ShoppingCart;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.brianlu.exhibitionshoppingcart.Model.CartItem;
import com.brianlu.exhibitionshoppingcart.R;
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
        Log.i("TAG", viewHolderPresenter.getItemCount() + "fdsfasdf");
        return viewHolderPresenter.getItemCount();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        viewHolderPresenter.bindData(holder, position);
    }

    public void addCartItems(List<CartItem> articles) {
        viewHolderPresenter.addArticles(articles);
        notifyDataSetChanged();
    }

    public void clear() {
        viewHolderPresenter.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ShoppingCartRecyclerViewHolderView {

        private ImageView productImageView;
        private TextView nameTextView;
        private TextView priceTextView;
        private TextView countTextView;
        private CardView cardView;

        ViewHolder(View v) {
            super(v);
            productImageView = v.findViewById(R.id.product_imageView);
            nameTextView = v.findViewById(R.id.product_name_textView);
            priceTextView = v.findViewById(R.id.price_textView);
            countTextView = v.findViewById(R.id.count_textView);
            cardView = v.findViewById(R.id.card_view);
        }

        @Override
        public void onSetProductItemImageView(String fileName) {
            System.out.println("file" + fileName);
            Glide.with(itemView)
                    .load(fileName)
                    .centerCrop()
                    .error(R.mipmap.box)
                    .into(productImageView);
        }

        @Override
        public void onSetProductItemName(String name) {
            nameTextView.setText(name);
        }

        @Override
        public void onSetProductItemPrice(String price) {
            priceTextView.setText(price);
        }

        @Override
        public void onSetProductItemCount(String count) {
            countTextView.setText(count);
        }

        @Override
        public void onSetCardViewClickListener(View.OnClickListener listener) {
            cardView.setOnClickListener(listener);
        }
    }


}
