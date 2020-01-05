package com.brianlu.exhibitionshoppingcart.HidpApi;

import android.util.Base64;

import com.brianlu.exhibitionshoppingcart.Model.CartItem;
import com.brianlu.exhibitionshoppingcart.Model.Product;
import com.brianlu.exhibitionshoppingcart.Model.ProductViewModel;
import com.brianlu.exhibitionshoppingcart.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

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
        retrofitArticleExcerptApi = urlRetrofitBuilder.buildretrofit("http://ec2-54-169-251-7.ap-southeast-1.compute.amazonaws.com:9900/", true);
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

    public Observable<Response<ResponseBody>> editCartItem(@NonNull User user, @NonNull CartItem cartItem, boolean isObserveOnIO) {
        String authKey = user.authKey();
        String productId = cartItem.getProductId();
        int amount = cartItem.getAmount();
        String query = "productId=" + productId + "&amount=" + amount;
        return hidpApi.editCartItem(authKey, productId, amount)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnNext(response -> System.out.println(response.isSuccessful()));
//                .filter(Response::isSuccessful);
    }

    public Observable<ProductViewModel> getProductInfo(@NonNull User user, @NonNull Product product, boolean isObserveOnIO) {
        String authKey = user.authKey();
        String productId = product.getProductId();
        return hidpApi.getProductInfo(authKey, productId)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .filter(Response::isSuccessful)
                .map(Response::body)
                .map(ResponseBody::string)
                .doOnNext(System.out::println)
                .map(s -> new GsonBuilder().create().fromJson(s, ProductViewModel.class));
    }

    public Observable<List<ProductViewModel>> getProductsBySellerId(@NonNull User user, boolean isObserveOnIO) {
        String authKey = user.authKey();
        return hidpApi.getProductsBySellerId(authKey, user.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .filter(Response::isSuccessful)
                .map(Response::body)
                .map(ResponseBody::string)
                .doOnNext(System.out::println)
                .map(string -> {
                    Type listType = new TypeToken<List<ProductViewModel>>() {
                    }.getType();
                    return new GsonBuilder().registerTypeHierarchyAdapter(byte[].class,
                            new ByteArrayToBase64TypeAdapter()).setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create().fromJson(string, listType);

                });
    }


    public Observable<List<CartItem>> getCartItems(@NonNull User user, boolean isObserveOnIO) {
        String authKey = user.authKey();
        return hidpApi.getCartItems(authKey)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .filter(Response::isSuccessful)
                .map(Response::body)
                .map(ResponseBody::string)
                .map(string -> {
                    Type listType = new TypeToken<List<CartItem>>() {
                    }.getType();
                    return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create().<List<CartItem>>fromJson(string, listType);
                });
    }

    public Observable<Boolean> uploadProduct(@NonNull User user, @NonNull ProductViewModel model, boolean isObserveOnIO) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        String json = gson.toJson(model);
        System.out.println(json);
        return hidpApi.uploadProduct(user.authKey(), json)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnNext(response -> System.out.println(response.isSuccessful()))
                .filter(Response::isSuccessful)
                .map(response -> true);
    }

    // 創建實例
    private static class SingletonHolder {
        private static final HidpApiService INSTANCE = new HidpApiService();
    }

    private static class ByteArrayToBase64TypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Base64.decode(json.getAsString(), Base64.NO_WRAP);
        }

        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Base64.encodeToString(src, Base64.NO_WRAP));
        }
    }
}

