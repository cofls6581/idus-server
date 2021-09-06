package com.example.demo.src.userProduct.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDetailHotRes {
    private String prodCateName;
    private int prodIdx;
    private String prodImage;
    private String prodName;
}
