package com.brianlu.exhibitionshoppingcart.Model;

import lombok.Data;

@Data
public class ProductViewModel {
    private String productSellerId;
    private String productName;
    private Double productPrice;
    private Integer productCount;
    private String productImageUrl;
    private String productId;
    private String productDesc;
    private byte [] productQrCode;

}

