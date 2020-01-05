package com.brianlu.exhibitionshoppingcart.Buyer.ProductDetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.brianlu.exhibitionshoppingcart.Model.ProductViewModel;
import com.brianlu.exhibitionshoppingcart.R;
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
    private TextView contentTextView;
    private SeekBar seekBar;

    private boolean isScan;
    private int amount;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productImageView = findViewById(R.id.product_imageView);
        addToCartButton = findViewById(R.id.add_to_cart_button);
        nameTextView = findViewById(R.id.product_name_textView);
        priceTextView = findViewById(R.id.product_price_textView);
        countTextView = findViewById(R.id.product_count_textView);
        contentTextView = findViewById(R.id.product_content_textView);
        seekBar = findViewById(R.id.seekBar);

        findViewById(R.id.minus_imageView).setOnClickListener(view -> {
            int value = seekBar.getProgress();
            if (seekBar.getMin() < value) {
                value--;
            }
            seekBar.setProgress(value);
            countTextView.setText(value + "個");
        });

        findViewById(R.id.plus_imageView).setOnClickListener(view -> {
            int value = seekBar.getProgress();

            if (seekBar.getMax() > value) {
                value++;
            }

            seekBar.setProgress(value);
            countTextView.setText(value + "個");
        });


        present = new ProductDetailPresenter(this);
        addToCartButton.setOnClickListener(this);
        final Intent intent = getIntent();
        isScan = intent.getBooleanExtra("isScan", false);
        amount = intent.getIntExtra("amount", 0);

        if (!isScan) {
            addToCartButton.setText("更新");
        }

        Optional<String> productIdOptional = Optional.ofNullable(intent.getStringExtra("productId"));
        productIdOptional.ifPresent(productId -> {
            Log.i("TAG", productId);
            present.getProductInfo(productId);
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                countTextView.setText(i + "個");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public void onItemAddSuccess() {
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public void onShowProductDetail(ProductViewModel model) {
        Glide.with(getApplicationContext())
                .load(model.getProductImageUrl())
                .centerCrop()
                .error(R.mipmap.box)
                .into(productImageView);

//        Log.i("TAG", model.getProductImageUrl());
        contentTextView.setText(model.getProductDesc());
        nameTextView.setText(model.getProductName());
        priceTextView.setText(model.getProductPrice().toString() + "元");
        countTextView.setText(0 + "個");
        seekBar.setMin(0);
        seekBar.setMax(model.getProductCount() + amount);
        seekBar.setProgress(amount);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_to_cart_button:
                int progress = seekBar.getProgress();
                if (progress != 0 || !isScan) {
                    present.addItemToCart(progress);
                }
                break;
        }
    }

    @Override
    public void onSetMessage(String message, int type) {
        FancyToast.makeText(this, message, FancyToast.LENGTH_SHORT, type, false).show();
    }
}
