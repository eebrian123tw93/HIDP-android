package com.brianlu.exhibitionshoppingcart.ShoppingCart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brianlu.exhibitionshoppingcart.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Optional;


public class ShoppingCartFragment extends Fragment
        implements View.OnClickListener, ShoppingCartView {

    private RecyclerView recyclerView;
    private TwinklingRefreshLayout refreshLayout;
    private Button scanButton;

    private ShoppingCartPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        scanButton = view.findViewById(R.id.scan_button);
        scanButton.setOnClickListener(this);

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setAutoLoadMore(true);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
//                presenter.refresh();
//                presenter.getArticleList(orderBy, 0, 1000);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
//                presenter.getArticleList(orderBy, 1000);
            }
        });

        recyclerView = view.findViewById(R.id.cart_product_list_recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

//        recyclerView.setAdapter(adapter);

        presenter = new ShoppingCartPresenter(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scan_button:
                openQrcodeScanner();
                break;
        }
    }

    private void openQrcodeScanner() {
        IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
        scanIntegrator.setPrompt("掃描商品Qrcode");
        scanIntegrator.setOrientationLocked(true);
        scanIntegrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        Optional<IntentResult> scanningResult = Optional.of(IntentIntegrator.parseActivityResult(requestCode, resultCode, intent));
        scanningResult.ifPresent(result -> {
            String scanContent = result.getContents();
            presenter.aaa(scanContent);
            Log.i("ShoppingCartFragment", scanContent);
        });

    }

    @Override
    public void onFinishRefreshOrLoad() {
        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();
    }

    @Override
    public void onSetMessage(String message, int type) {
        FancyToast.makeText(getContext(), message, FancyToast.LENGTH_SHORT, type, false).show();
    }

    @Override
    public void setScanButtonText(String text) {
        scanButton.setText(text);
    }

    @Override
    public void onSetArticleListRecyclerAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

}
