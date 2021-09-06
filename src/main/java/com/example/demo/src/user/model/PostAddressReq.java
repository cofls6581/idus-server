package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostAddressReq {
    private int userIdx;
    private String deliveryName;
    private String deliveryPhoneNum;
    private String address;
}
