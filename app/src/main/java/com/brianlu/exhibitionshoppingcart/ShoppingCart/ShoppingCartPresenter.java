package com.brianlu.exhibitionshoppingcart.ShoppingCart;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.brianlu.exhibitionshoppingcart.Base.BasePresenter;
import com.brianlu.exhibitionshoppingcart.Base.BaseView;
import com.brianlu.exhibitionshoppingcart.HidpApi.HidpApiService;
import com.brianlu.exhibitionshoppingcart.model.CartItem;
import com.brianlu.exhibitionshoppingcart.model.User;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;

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
        view.setCheckoutButtonText("總計 0");
        adapter = new ShoppingCartRecyclerViewAdapter(context);
        view.onSetArticleListRecyclerAdapter(adapter);
    }

    void getCartItems() {
        User user = new User();
        user.setUserId("test");
        user.setPassword("test");
        HidpApiService.getInstance().getCartItems(user, false)
                .filter(Response::isSuccessful)
                .map(Response::body)
                .map(ResponseBody::string).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.i("TAG", s);
                Type listType = new TypeToken<List<CartItem>>() {
                }.getType();
                List<CartItem> articleList = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create().fromJson(s, listType);
                Log.i("TAG", articleList.size() + "");
                adapter.addArticles(articleList);

                double price = articleList.stream().map(CartItem::getProductPrice).map(Double::parseDouble).reduce(0.0, Double::sum );
                view.setCheckoutButtonText("總計 :"+price);
                view.onFinishRefreshOrLoad();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
