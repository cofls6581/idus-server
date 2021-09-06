package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class PatchPaymentReq {
    private int orderIdx;
    private int totalPrice;
    private int deliveryIdx;
    private int paymentType;
}
