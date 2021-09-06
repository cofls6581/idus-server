package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class PostOrderRes {
    private int orderIdx;
    private int [] orderProdIdx;
    private int[] orderProdSideIdx;
}
