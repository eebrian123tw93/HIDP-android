package com.brianlu.exhibitionshoppingcart.Seller.ProductList.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brianlu.exhibitionshoppingcart.Model.ProductViewModel;
import com.brianlu.exhibitionshoppingcart.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class ProductListRecyclerViewAdapter extends RecyclerView.Adapter<ProductListRecyclerViewAdapter.ViewHolder> {

    private Context context;

    private ProductListRecyclerViewHolderPresenter viewHolderPresenter;

    public ProductListRecyclerViewAdapter(Context context) {
        this.context = context;
        this.viewHolderPresenter = new ProductListRecyclerViewHolderPresenter();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return viewHolderPresenter.getItemCount();
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        viewHolderPresenter.bindData(holder, position);
    }

    public void addProductList(List<ProductViewModel> models) {
        viewHolderPresenter.addArticles(models);
        notifyDataSetChanged();
    }

    public void clear() {
        viewHolderPresenter.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ProductListRecyclerViewHolderView {

        private ImageView productImageView;
        private ImageView qrcodeImageView;
        private TextView nameTextView;
        private TextView priceTextView;
        private TextView countTextView;

        ViewHolder(View v) {
            super(v);
            productImageView = v.findViewById(R.id.product_imageView);
            nameTextView = v.findViewById(R.id.product_name_textView);
            priceTextView = v.findViewById(R.id.price_textView);
            countTextView = v.findViewById(R.id.count_textView);
            qrcodeImageView = v.findViewById(R.id.product_qrcode);
        }

        @Override
        public void onSetProductItemImageView(String fileName) {
            System.out.println("file"+fileName);
            Glide.with(itemView)
                    .load(fileName)
                    .centerCrop()
                    .error(R.mipmap.box)
                    .into(productImageView);
        }

        @Override
        public void onSetProductQrcodeImageView(byte[] imageByteArray) {
            Glide.with(itemView)
                    .load(imageByteArray)
                    .error(R.mipmap.qr_code)
                    .into(qrcodeImageView);
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
    }


}
