package com.brianlu.exhibitionshoppingcart.Buyer.CheckOut;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.brianlu.exhibitionshoppingcart.R;
import com.shashank.sony.fancytoastlib.FancyToast;

public class CheckOutActivity extends AppCompatActivity implements CheckOutView {

    private CheckOutPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        presenter = new CheckOutPresenter(this);
    }

    @Override
    public void onSetMessage(String message, int type) {
        FancyToast.makeText(this, message, FancyToast.LENGTH_SHORT, type, false).show();
    }
}
