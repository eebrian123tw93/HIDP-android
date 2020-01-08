package com.brianlu.exhibitionshoppingcart.Buyer.ShoppingCart;

import android.annotation.SuppressLint;

import androidx.recyclerview.widget.RecyclerView;

import com.brianlu.exhibitionshoppingcart.Base.BasePresenter;
import com.brianlu.exhibitionshoppingcart.Base.BaseView;
import com.brianlu.exhibitionshoppingcart.HidpApi.HidpApiService;
import com.brianlu.exhibitionshoppingcart.Model.CartItem;
import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

interface ShoppingCartView extends BaseView {
    void setScanButtonText(String text);

    void setCheckoutButtonText(String text);

    void onFinishRefreshOrLoad();

    void onSetArticleListRecyclerAdapter(RecyclerView.Adapter adapter);
}

class ShoppingCartPresenter extends BasePresenter {
    private ShoppingCartView view;
    private ShoppingCartRecyclerViewAdapter adapter;

    ShoppingCartPresenter(ShoppingCartView view) {
        this.view = view;
        view.setScanButtonText("掃描商品");
        view.setCheckoutButtonText("總計 0元");
        adapter = new ShoppingCartRecyclerViewAdapter(context);
        view.onSetArticleListRecyclerAdapter(adapter);
    }

    void getCartItems() {
        adapter.clear();
        HidpApiService.getInstance().getCartItems(user, false)
                .map(cartItems -> cartItems.stream().filter(cartItem -> cartItem.getAmount() != 0).collect(Collectors.toList()))
                .subscribe(new Observer<List<CartItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @SuppressLint("CheckResult")
                    @Override
                    public void onNext(List<CartItem> cartItems) {
                        adapter.addCartItems(cartItems);
                        BehaviorRelay<Double> total = BehaviorRelay.createDefault(0.0);
                        List<BehaviorRelay<Double>> prices = cartItems.stream().map(CartItem::getPriceTotal).collect(Collectors.toList());
                        prices.forEach(price -> {
                            price.subscribe(aDouble -> {
                                total.accept(total.getValue() + aDouble);
                            });
                        });
                        total.subscribe(aDouble -> {
                            view.setCheckoutButtonText("總計" + ((int) (double) aDouble)+" 元");
                        });


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}


