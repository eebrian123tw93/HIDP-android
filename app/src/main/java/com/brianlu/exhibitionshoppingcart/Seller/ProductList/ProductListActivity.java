package com.brianlu.exhibitionshoppingcart.Seller.ProductList;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brianlu.exhibitionshoppingcart.R;
import com.brianlu.exhibitionshoppingcart.Seller.AddProduct.AddProductActivity;
import com.shashank.sony.fancytoastlib.FancyToast;

public class ProductListActivity extends AppCompatActivity implements ProductListView {

    private ProductListPresenter presenter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        findViewById(R.id.add_product_button).setOnClickListener(view -> {
            Intent intent = new Intent(ProductListActivity.this, AddProductActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.product_list_recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        presenter = new ProductListPresenter(this);

    }

    @Override
    public void onSetMessage(String message, int type) {
        FancyToast.makeText(this, message, FancyToast.LENGTH_SHORT, type, false).show();
    }

    @Override
    public void onSetProductListRecyclerAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getProductList();
    }
}
