package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class DeliveryAddress {
    private int deliveryIdx;
    private Timestamp createAt;
    private Timestamp updateAt;
    private String status;
    private int userIdx;
    private String deliveryName;
    private String deliveryPhoneNum;
    private String address;
}
