package com.brianlu.exhibitionshoppingcart.Buyer.CheckOut;

import com.brianlu.exhibitionshoppingcart.Base.BasePresenter;
import com.brianlu.exhibitionshoppingcart.Base.BaseView;

interface CheckOutView extends BaseView {

}

public class CheckOutPresenter extends BasePresenter {

    private CheckOutView view;

    CheckOutPresenter(CheckOutView view) {
        this.view = view;
    }
}
