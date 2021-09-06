package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetBasketInfoRes {
    private int prodIdx;
    private String authorName;
    private String prodName;
    private String prodNum;
    private String request;
    private int totalProd;
    private int deliveryCost;
    private String prodImage;
}
