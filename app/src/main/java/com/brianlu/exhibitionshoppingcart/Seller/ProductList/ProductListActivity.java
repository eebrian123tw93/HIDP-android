package com.brianlu.exhibitionshoppingcart.Seller.ProductList;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.brianlu.exhibitionshoppingcart.Buyer.ShoppingCart.ShoppingCartActivity;
import com.brianlu.exhibitionshoppingcart.Identity.IdentityChooseActivity;
import com.brianlu.exhibitionshoppingcart.R;
import com.brianlu.exhibitionshoppingcart.Seller.AddProduct.AddProductActivity;
import com.shashank.sony.fancytoastlib.FancyToast;

public class ProductListActivity extends AppCompatActivity implements ProductListView {

    private ProductListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        findViewById(R.id.add_product_button).setOnClickListener(view -> {
            Intent intent = new Intent(ProductListActivity.this, AddProductActivity.class);
            startActivity(intent);
        });

        presenter = new ProductListPresenter(this);

    }

    @Override
    public void onSetMessage(String message, int type) {
        FancyToast.makeText(this, message, FancyToast.LENGTH_SHORT, type, false).show();
    }
}
