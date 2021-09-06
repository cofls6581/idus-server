package com.example.demo.src.userProduct.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProdOptionRes {
    private int prodSideIdx;
    private int sideCateNum;
    private String prodSideCate;
    private String prodSide;
    private int prodSidePrice;
}
