package com.brianlu.exhibitionshoppingcart.ShoppingCart;

import android.content.Intent;
import android.view.View;

import com.brianlu.exhibitionshoppingcart.Base.BasePresenter;
import com.brianlu.exhibitionshoppingcart.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartRecyclerViewHolderPresenter extends BasePresenter {

    private List<CartItem> articles;

    public ShoppingCartRecyclerViewHolderPresenter() {
        articles = new ArrayList<>();
    }

    public void bindData(ShoppingCartRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ShoppingCartRecyclerViewHolderView) {
            ShoppingCartRecyclerViewHolderView viewHolderView = viewHolder;
            final CartItem article = articles.get(position);

        }


    }

    public int getItemCount() {
        return articles.size();
    }

    public void addArticles(List<CartItem> cartItems) {
        this.articles.addAll(cartItems);

    }

    public void clear() {
        this.articles.clear();

    }


}