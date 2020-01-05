package com.brianlu.exhibitionshoppingcart.Seller.AddProduct;

import com.akiniyalocts.imgur_api.ImgurClient;
import com.akiniyalocts.imgur_api.model.Image;
import com.akiniyalocts.imgur_api.model.ImgurResponse;
import com.brianlu.exhibitionshoppingcart.Base.BasePresenter;
import com.brianlu.exhibitionshoppingcart.Base.BaseView;
import com.brianlu.exhibitionshoppingcart.HidpApi.HidpApiService;
import com.brianlu.exhibitionshoppingcart.Model.ProductViewModel;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.mime.TypedFile;


interface AddProductView extends BaseView {
    void onSetUploadButtonClickable(boolean enable);

    void onUploadSuccess();
}

public class AddProductPresenter extends BasePresenter {
    private AddProductView view;
    private ProductViewModel model;
    private File file;

    AddProductPresenter(AddProductView view) {
        this.view = view;
        model = new ProductViewModel();
        setUploadButtonClickable();
    }

    void uploadProduct() {
        view.onSetUploadButtonClickable(false);
        ImgurClient.getInstance()
                .uploadImage(
                        new TypedFile("image/*", file),
                        file.getAbsolutePath(),
                        file.getAbsolutePath(),
                        new Callback<ImgurResponse<Image>>() {
                            @Override
                            public void success(ImgurResponse<Image> imageImgurResponse, retrofit.client.Response response) {
                                if (imageImgurResponse.success) {
                                    String link = imageImgurResponse.data.getLink();
                                    model.setProductImageUrl(link);
                                    view.onSetMessage(link, FancyToast.SUCCESS);
                                    HidpApiService.getInstance().uploadProduct(user, model, false).subscribe(new Observer<Boolean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(Boolean aBoolean) {
                                            view.onUploadSuccess();
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

                            @Override
                            public void failure(RetrofitError error) {
                                error.printStackTrace();
                            }
                        }
                );
    }

    void setName(String name) {
        model.setProductName(name);
        setUploadButtonClickable();
    }

    void setContent(String content) {
        model.setProductDesc(content);
        setUploadButtonClickable();
    }

    void setPrice(double price) {
        model.setProductPrice(price);
        setUploadButtonClickable();
    }

    void setCount(int count) {
        model.setProductCount(count);
        setUploadButtonClickable();
    }

    void setImageFile(File file) {
        this.file = file;
        setUploadButtonClickable();
    }

    private void setUploadButtonClickable() {
        boolean enable = file != null && model.getProductName() != null && model.getProductDesc() != null && model.getProductCount() != null && model.getProductPrice() != null && !model.getProductName().isEmpty() && !model.getProductDesc().isEmpty() && model.getProductCount() != 0;
        view.onSetUploadButtonClickable(enable);
    }


}
