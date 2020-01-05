package com.brianlu.exhibitionshoppingcart.Seller.ProductList.RecyclerView;

import com.brianlu.exhibitionshoppingcart.Base.BasePresenter;
import com.brianlu.exhibitionshoppingcart.Model.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

class ProductListRecyclerViewHolderPresenter extends BasePresenter {

    private List<ProductViewModel> productViewModels;

    ProductListRecyclerViewHolderPresenter() {
        productViewModels = new ArrayList<>();
    }

    void bindData(ProductListRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        if (viewHolder != null) {
            ProductListRecyclerViewHolderView viewHolderView = viewHolder;
            final ProductViewModel productViewModel = productViewModels.get(position);
            viewHolderView.onSetProductItemCount("剩餘 "+productViewModel.getProductCount() + "個");
            viewHolderView.onSetProductItemPrice(productViewModel.getProductPrice() + "元");
            viewHolderView.onSetProductItemImageView(productViewModel.getProductImageUrl());
            viewHolderView.onSetProductItemName(productViewModel.getProductName());
            viewHolder.onSetProductQrcodeImageView(productViewModel.getProductQrCode());
        }
    }

    int getItemCount() {
        return productViewModels.size();
    }

    void addArticles(List<ProductViewModel> productViewModels) {
        this.productViewModels.addAll(productViewModels);
    }

    void clear() {
        productViewModels.clear();
    }

}