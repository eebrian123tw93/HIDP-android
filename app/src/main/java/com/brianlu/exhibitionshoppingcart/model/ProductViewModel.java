package com.brianlu.exhibitionshoppingcart.model;

import lombok.Data;

@Data
public class ProductViewModel {
    private String productSellerId;
    private String productName;
    private Double productPrice;
    private Integer productCount;
    private String productImageUrl;
}

