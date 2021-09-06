package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class GetPaymentProdRes {
    private int orderIdx;
    private int prodIdx;
    private String authorName;
    private String prodImage;
    private String prodName;
    private String prodNum;
    private int deliveryCost;
    private int minFreeCost;
}
