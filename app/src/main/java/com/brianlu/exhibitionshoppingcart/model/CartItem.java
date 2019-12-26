package com.brianlu.exhibitionshoppingcart.model;

import lombok.Data;

@Data
public class CartItem {
    String productId;
    String productImageUrl;
    String productName;
    String productPrice;
    int productCount;
}
