package com.brianlu.exhibitionshoppingcart.Buyer.ShoppingCart;

import com.brianlu.exhibitionshoppingcart.Base.BasePresenter;
import com.brianlu.exhibitionshoppingcart.HidpApi.HidpApiService;
import com.brianlu.exhibitionshoppingcart.Model.CartItem;
import com.brianlu.exhibitionshoppingcart.Model.Product;
import com.brianlu.exhibitionshoppingcart.Model.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ShoppingCartRecyclerViewHolderPresenter extends BasePresenter {

    private List<CartItem> cartItems;

    public ShoppingCartRecyclerViewHolderPresenter() {
        cartItems = new ArrayList<>();
    }

    public void bindData(ShoppingCartRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ShoppingCartRecyclerViewHolderView) {
            ShoppingCartRecyclerViewHolderView viewHolderView = viewHolder;
            final CartItem cartItem = cartItems.get(position);
            Product product = new Product();
            product.setProductId(cartItem.getProductId());
            HidpApiService.getInstance().getProductInfo(user,product, false ).subscribe(new Observer<ProductViewModel>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(ProductViewModel productViewModel) {
                    viewHolderView.onSetProductItemPrice(productViewModel.getProductPrice()+"");
                    viewHolderView.onSetProductItemImageView(productViewModel.getProductImageUrl());
                    viewHolderView.onSetProductItemName(productViewModel.getProductName());
                    cartItem.getPriceTotal().accept(cartItem.getAmount()* productViewModel.getProductPrice());
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();

                }

                @Override
                public void onComplete() {

                }
            });
//            Optional<Product> productOptional = Optional.ofNullable(cartItem.getProduct());
//            productOptional.ifPresent(product -> {
//
//                viewHolderView.onSetProductItemPrice(product.getProductPrice());
//                viewHolderView.onSetProductItemImageView(product.getProductImageUrl());
//                viewHolderView.onSetProductItemName(product.getProductName());
//            });
            viewHolderView.onSetProductItemCount(cartItem.getAmount() + "");


        }


    }

    public int getItemCount() {
        return cartItems.size();
    }

    public void addArticles(List<CartItem> cartItems) {
        this.cartItems.addAll(cartItems);
    }

    public void clear() {
        cartItems.clear();

    }


}