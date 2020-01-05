package com.brianlu.exhibitionshoppingcart.Model;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class CartItem implements Serializable {
    String productId;
    Date dateTimeAdded;
    int amount;
    BehaviorRelay<Double> priceTotal = BehaviorRelay.createDefault(0.0);
}
