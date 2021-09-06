package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetAddressRes {
    private int deliveryIdx;
    private int userIdx;
    private String deliveryName;
    private String deliveryPhoneNum;
    private String address;
}
