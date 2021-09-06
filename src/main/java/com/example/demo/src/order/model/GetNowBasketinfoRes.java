package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class GetNowBasketinfoRes {
    private int orderIdx;
    private String authorName;
    private String prodName;
    private String prodNum;
    private String prodImage;
    private int deliveryCost;
    private int minFreeCost;
}
