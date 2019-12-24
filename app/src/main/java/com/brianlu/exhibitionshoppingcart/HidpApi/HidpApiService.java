package com.brianlu.exhibitionshoppingcart.HidpApi;

import com.brianlu.exhibitionshoppingcart.model.Product;
import com.brianlu.exhibitionshoppingcart.model.User;
import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HidpApiService {
    private HidpApi hidpApi;
    private Retrofit retrofitArticleExcerptApi;

    private HidpApiService() {
        URLRetrofitBuilder urlRetrofitBuilder = new URLRetrofitBuilder();
        retrofitArticleExcerptApi = urlRetrofitBuilder.buildretrofit("http://ec2-3-1-217-210.ap-southeast-1.compute.amazonaws.com:9900/", true);
        hidpApi = retrofitArticleExcerptApi.create(HidpApi.class);
    }

    // 獲取實例
    public static HidpApiService getInstance() {
        return HidpApiService.SingletonHolder.INSTANCE;
    }

    public void register(@NonNull Observer observer,
                         @NonNull User user, boolean isObserveOnIO) {

        Gson gson = new Gson();
        String json = gson.toJson(user);
        hidpApi
                .register(json)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }



    public void login(@NonNull Observer observer, @NonNull User user, boolean isObserveOnIO) {
        String authKey = user.authKey();
        hidpApi
                .login(authKey)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);

    }

    public void deleteUser(@NonNull Observer observer, @NonNull User user, boolean isObserveOnIO) {
        String authKey = user.authKey();
        hidpApi
                .deleteUser(authKey)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);

    }

    public void forgotPassword(@NonNull Observer observer, @NonNull String email, boolean isObserveOnIO) {
        hidpApi.forgotPassword(email)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public Observable<Response<ResponseBody>> addItemToCart(@NonNull User user, @NonNull Product product, boolean isObserveOnIO) {
        String authKey = user.authKey();
        String productId = product.getProductId();
        return hidpApi.addItemToCart(authKey, productId)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    // 創建實例
    private static class SingletonHolder {
        private static final HidpApiService INSTANCE = new HidpApiService();
    }
}

