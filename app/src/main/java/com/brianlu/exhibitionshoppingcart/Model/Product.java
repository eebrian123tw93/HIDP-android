package com.brianlu.exhibitionshoppingcart.Model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Product implements Serializable {
    String productId;
    String productImageUrl;
    String productName;
    String productPrice;
    int productCount;
}
