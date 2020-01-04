package com.brianlu.exhibitionshoppingcart.Seller.AddProduct;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.brianlu.exhibitionshoppingcart.R;
import com.shashank.sony.fancytoastlib.FancyToast;

public class AddProductActivity extends AppCompatActivity implements AddProductView {

    private AddProductPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        presenter = new AddProductPresenter(this);
    }

    @Override
    public void onSetMessage(String message, int type) {
        FancyToast.makeText(this, message, FancyToast.LENGTH_SHORT, type, false).show();
    }
}
