package com.brianlu.exhibitionshoppingcart.Buyer.ProductDetail;

import com.brianlu.exhibitionshoppingcart.Base.BasePresenter;
import com.brianlu.exhibitionshoppingcart.Base.BaseView;
import com.brianlu.exhibitionshoppingcart.HidpApi.HidpApiService;
import com.brianlu.exhibitionshoppingcart.Model.CartItem;
import com.brianlu.exhibitionshoppingcart.Model.Product;
import com.brianlu.exhibitionshoppingcart.Model.ProductViewModel;
import com.brianlu.exhibitionshoppingcart.Model.User;
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
    private boolean isScan;

    ProductDetailPresenter(ProductDetailView view) {
        this.view = view;
    }

    void getProductInfo(String productId) {

        product = new Product();
        product.setProductId(productId);
        User user = new User();
        user.setUserId("test");
        user.setPassword("test");
        HidpApiService.getInstance()
                .getProductInfo(user, product, false)
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

    void addItemToCart(int amount) {
        String productId = product.getProductId();
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setAmount(amount);
        HidpApiService.getInstance().editCartItem(user, cartItem, false)
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ResponseBody> response) {
                        System.out.println(response.code());
                        if (response.code() == 200) {
                            if (isScan) {
                                view.onSetMessage("商品加入成功", FancyToast.SUCCESS);
                            } else {
                                view.onSetMessage("商品更新成功", FancyToast.SUCCESS);
                            }

                            view.onItemAddSuccess();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.onSetMessage("error", FancyToast.ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    void setScan(boolean scan) {
        isScan = scan;
    }
}
