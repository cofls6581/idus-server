package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetBasketPriceRes {
    private int basketIdx;
    private int prodIdx;
    private int orderProdIdx;
    private String prodSideCate;
    private String prodSide;
    private int optionPrice;
    private int prodCount;
}
