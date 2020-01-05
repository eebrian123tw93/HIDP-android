package com.brianlu.exhibitionshoppingcart.Buyer.ShoppingCart;


import android.view.View;

public interface ShoppingCartRecyclerViewHolderView {

    void onSetProductItemImageView(String fileName);

    void onSetProductItemName(String name);

    void onSetProductItemPrice(String price);

    void onSetProductItemCount(String count);

    void onSetCardViewClickListener(View.OnClickListener listener);
}