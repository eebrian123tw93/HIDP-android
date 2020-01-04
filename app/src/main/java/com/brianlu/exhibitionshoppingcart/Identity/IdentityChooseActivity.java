package com.brianlu.exhibitionshoppingcart.Identity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.brianlu.exhibitionshoppingcart.R;
import com.brianlu.exhibitionshoppingcart.Buyer.ShoppingCart.ShoppingCartActivity;
import com.brianlu.exhibitionshoppingcart.Seller.ProductList.ProductListActivity;

public class IdentityChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_choose);

        findViewById(R.id.buyer_button).setOnClickListener(view -> {
            Intent intent = new Intent(IdentityChooseActivity.this, ShoppingCartActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.seller_button).setOnClickListener(view -> {
            Intent intent = new Intent(IdentityChooseActivity.this, ProductListActivity.class);
            startActivity(intent);
        });
    }
}
