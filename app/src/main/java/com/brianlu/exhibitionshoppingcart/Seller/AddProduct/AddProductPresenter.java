package com.brianlu.exhibitionshoppingcart.Seller.AddProduct;

import com.brianlu.exhibitionshoppingcart.Base.BasePresenter;
import com.brianlu.exhibitionshoppingcart.Base.BaseView;


interface AddProductView extends BaseView {

}

public class AddProductPresenter extends BasePresenter {
    private AddProductView view;

    AddProductPresenter(AddProductView view) {
        this.view = view;
    }
}
