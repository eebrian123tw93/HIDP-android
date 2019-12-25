package com.brianlu.exhibitionshoppingcart.ShoppingCart;


public interface ShoppingCartRecyclerViewHolderView {

    void onSetProductItemImageView(String fileName);

    void onSetProductItemName(String name);

    void onSetProductItemPrice(String price);

    void onSetProductItemCount(String count);
}