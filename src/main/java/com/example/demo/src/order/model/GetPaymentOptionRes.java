package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class GetPaymentOptionRes {
    private int orderIdx;
    private int prodIdx;
    private int orderProdIdx;
    private String prodSideCate;
    private String prodSide;
    private int optionPrice;
    private int prodCount;
}
