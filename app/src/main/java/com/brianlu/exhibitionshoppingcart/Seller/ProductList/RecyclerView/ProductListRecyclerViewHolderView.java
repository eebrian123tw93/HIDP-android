package com.brianlu.exhibitionshoppingcart.Seller.ProductList.RecyclerView;


public interface ProductListRecyclerViewHolderView {

    void onSetProductItemImageView(String fileName);

    void onSetProductQrcodeImageView(byte [] bytes);

    void onSetProductItemName(String name);

    void onSetProductItemPrice(String price);

    void onSetProductItemCount(String count);
}