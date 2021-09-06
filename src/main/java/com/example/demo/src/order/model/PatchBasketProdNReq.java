package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class PatchBasketProdNReq {
    private int orderProdIdx;
    private int prodCount;
}
