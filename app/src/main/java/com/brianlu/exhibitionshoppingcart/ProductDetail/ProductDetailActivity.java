package com.brianlu.exhibitionshoppingcart.ProductDetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.brianlu.exhibitionshoppingcart.R;
import com.brianlu.exhibitionshoppingcart.model.ProductViewModel;
import com.bumptech.glide.Glide;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Optional;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailView, View.OnClickListener {

    private ProductDetailPresenter present;

    private ImageView productImageView;
    private Button addToCartButton;
    private TextView nameTextView;
    private TextView priceTextView;
    private TextView countTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        addToCartButton = findViewById(R.id.add_to_cart_button);
        nameTextView = findViewById(R.id.product_name_textView);
        priceTextView = findViewById(R.id.product_price_textView);
        countTextView = findViewById(R.id.product_count_textView);


        present = new ProductDetailPresenter(this);
        addToCartButton.setOnClickListener(this);
        Intent intent = getIntent();
        Optional<String> productIdOptional = Optional.ofNullable(intent.getStringExtra("productId"));
        productIdOptional.ifPresent(productId -> {
            present.getProductInfo(productId);
        });

    }

    @Override
    public void onItemAddSuccess() {
        finish();
    }

    @Override
    public void onShowProductDetail(ProductViewModel model) {
        Glide.with(getApplicationContext())
                .load(model.getProductImageUrl())
                .into(productImageView);

        nameTextView.setText(model.getProductName());
        priceTextView.setText(model.getProductPrice().toString());
        countTextView.setText(model.getProductCount().toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_to_cart_button:
                present.addItemToCart();
                break;
        }
    }

    @Override
    public void onSetMessage(String message, int type) {
        FancyToast.makeText(this, message, FancyToast.LENGTH_SHORT, type, false).show();
    }
}
