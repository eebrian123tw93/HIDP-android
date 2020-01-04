package com.brianlu.exhibitionshoppingcart.Seller.ProductList;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.brianlu.exhibitionshoppingcart.R;
import com.shashank.sony.fancytoastlib.FancyToast;

public class ProductListActivity extends AppCompatActivity implements ProductListView {

    private ProductListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        presenter = new ProductListPresenter(this);
    }

    @Override
    public void onSetMessage(String message, int type) {
        FancyToast.makeText(this, message, FancyToast.LENGTH_SHORT, type, false).show();
    }
}
