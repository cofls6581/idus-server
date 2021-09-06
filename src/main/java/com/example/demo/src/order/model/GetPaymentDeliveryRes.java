package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class GetPaymentDeliveryRes {
    private int deliveryIdx;
    private String deliveryName;
    private String deliveryPhoneNum;
    private String address;
}
