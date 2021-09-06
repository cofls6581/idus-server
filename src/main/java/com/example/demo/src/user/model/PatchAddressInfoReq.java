package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class PatchAddressInfoReq {
    private int deliveryIdx;
    private String deliveryName;
    private String deliveryPhoneNum;
    private String address;
}
