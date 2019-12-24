package com.brianlu.exhibitionshoppingcart.model;

import lombok.Data;

@Data
public class Product {
    String productId;
    String productImageUrl;
    String productName;
    String productPrice;
    int productCount;
}
