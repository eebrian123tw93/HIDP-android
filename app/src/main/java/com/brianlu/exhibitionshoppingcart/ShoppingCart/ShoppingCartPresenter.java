package com.brianlu.exhibitionshoppingcart.ShoppingCart;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.brianlu.exhibitionshoppingcart.Base.BasePresenter;
import com.brianlu.exhibitionshoppingcart.Base.BaseView;
import com.brianlu.exhibitionshoppingcart.HidpApi.HidpApiService;
import com.brianlu.exhibitionshoppingcart.model.Product;
import com.brianlu.exhibitionshoppingcart.model.User;
import com.shashank.sony.fancytoastlib.FancyToast;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
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

    ShoppingCartPresenter(ShoppingCartView view) {
        this.view = view;
        view.setScanButtonText("掃描商品");
        view.setCheckoutButtonText("總計 0");
    }

    @SuppressLint("CheckResult")
    void aaa(String content) {
        view.onSetMessage(content, FancyToast.INFO);
        Product product = new Product();
        product.setProductId(content);
        User user = new User();
        user.setUserId("test");
        user.setPassword("test");
        HidpApiService.getInstance().addItemToCart(user, product, false)
                .filter(Response::isSuccessful)
                .map(Response::body)
                .map(ResponseBody::string)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String string) {
                        if (string.equals("success") ) {
                            view.onSetMessage("商品加入成功", FancyToast.INFO);
                        } else {
                            Log.i("TAG", string);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onSetMessage(e.getMessage(), FancyToast.ERROR);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
