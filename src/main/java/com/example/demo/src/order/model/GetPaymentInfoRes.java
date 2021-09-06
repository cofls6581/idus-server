package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class GetPaymentInfoRes {
    private String userName;
    private String phoneNum;
    private String paymentName;
    private String paymentNum;
    private int totalProdPrice;
}
