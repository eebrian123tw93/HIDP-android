package com.brianlu.exhibitionshoppingcart.ProductDetail;

import android.util.Log;

import com.brianlu.exhibitionshoppingcart.Base.BasePresenter;
import com.brianlu.exhibitionshoppingcart.Base.BaseView;
import com.brianlu.exhibitionshoppingcart.HidpApi.HidpApiService;
import com.brianlu.exhibitionshoppingcart.model.Product;
import com.brianlu.exhibitionshoppingcart.model.ProductViewModel;
import com.brianlu.exhibitionshoppingcart.model.User;
import com.google.gson.GsonBuilder;
import com.shashank.sony.fancytoastlib.FancyToast;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;

interface ProductDetailView extends BaseView {
    void onShowProductDetail(ProductViewModel model);

    void onItemAddSuccess();
}

class ProductDetailPresenter extends BasePresenter {
    private ProductDetailView view;
    private Product product;

    ProductDetailPresenter(ProductDetailView view) {
        this.view = view;
    }

    void getProductInfo(String productId) {
        view.onSetMessage(productId, FancyToast.INFO);
        product = new Product();
        product.setProductId(productId);
        User user = new User();
        user.setUserId("test");
        user.setPassword("test");
        HidpApiService.getInstance().getProductInfo(user, product, false)
                .filter(Response::isSuccessful)
                .map(Response::body)
                .map(ResponseBody::string)
                .map(s -> {
                    ProductViewModel detail = new GsonBuilder().create().fromJson(s, ProductViewModel.class);
                    return detail;
                })
                .subscribe(new Observer<ProductViewModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ProductViewModel detail) {
                        view.onShowProductDetail(detail);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.onSetMessage(e.getMessage(), FancyToast.ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    void addItemToCart() {
        String productId = product.getProductId();
        view.onSetMessage(productId, FancyToast.INFO);
        Product product = new Product();
        product.setProductId(productId);
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
                        if (string.equals("success")) {
                            view.onSetMessage("商品加入成功", FancyToast.SUCCESS);
                            view.onItemAddSuccess();
                        } else if (string.equals("no products in stock")) {
                            Log.i("TAG", string);
                            view.onSetMessage("商品庫存不足", FancyToast.INFO);
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
