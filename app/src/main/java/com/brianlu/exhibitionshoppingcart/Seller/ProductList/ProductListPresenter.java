package com.brianlu.exhibitionshoppingcart.Seller.ProductList;

import com.brianlu.exhibitionshoppingcart.Base.BasePresenter;
import com.brianlu.exhibitionshoppingcart.Base.BaseView;

interface ProductListView extends BaseView {

}

public class ProductListPresenter extends BasePresenter {
    private ProductListView view;

    ProductListPresenter(ProductListView view) {
        this.view = view;
    }
}
