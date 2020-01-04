package com.brianlu.exhibitionshoppingcart.Buyer.ShoppingCart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.brianlu.exhibitionshoppingcart.R;

public class ShoppingCartActivity extends AppCompatActivity {
    private Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        fragment = new ShoppingCartFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, fragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        fragment.onResume();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fragment.onActivityResult(requestCode, resultCode, intent);
    }
}
