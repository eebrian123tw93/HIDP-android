package com.brianlu.exhibitionshoppingcart.Seller.ProductList;

import androidx.recyclerview.widget.RecyclerView;

import com.brianlu.exhibitionshoppingcart.Base.BasePresenter;
import com.brianlu.exhibitionshoppingcart.Base.BaseView;
import com.brianlu.exhibitionshoppingcart.HidpApi.HidpApiService;
import com.brianlu.exhibitionshoppingcart.Model.ProductViewModel;
import com.brianlu.exhibitionshoppingcart.Seller.ProductList.RecyclerView.ProductListRecyclerViewAdapter;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

interface ProductListView extends BaseView {
    void onSetProductListRecyclerAdapter(RecyclerView.Adapter adapter);
}

public class ProductListPresenter extends BasePresenter {
    private ProductListView view;
    private ProductListRecyclerViewAdapter adapter;

    ProductListPresenter(ProductListView view) {
        this.view = view;
        adapter = new ProductListRecyclerViewAdapter(context);
        view.onSetProductListRecyclerAdapter(adapter);
    }

    void getProductList() {
        adapter.clear();
        HidpApiService.getInstance().getProductsBySellerId(user, false).subscribe(new Observer<List<ProductViewModel>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<ProductViewModel> productViewModels) {
                adapter.addProductList(productViewModels);
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
