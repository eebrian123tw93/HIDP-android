package com.brianlu.exhibitionshoppingcart.ShoppingCart;

import com.brianlu.exhibitionshoppingcart.Base.BasePresenter;
import com.brianlu.exhibitionshoppingcart.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartRecyclerViewHolderPresenter extends BasePresenter {

    private List<CartItem> cartItems;

    public ShoppingCartRecyclerViewHolderPresenter() {
        cartItems = new ArrayList<>();
    }

    public void bindData(ShoppingCartRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ShoppingCartRecyclerViewHolderView) {
            ShoppingCartRecyclerViewHolderView viewHolderView = viewHolder;
            final CartItem cartItem = cartItems.get(position);

        }


    }

    public int getItemCount() {
        return cartItems.size();
    }

    public void addArticles(List<CartItem> cartItems) {
        cartItems.addAll(cartItems);

    }

    public void clear() {
        cartItems.clear();

    }


}