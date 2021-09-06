package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
public class PostBasketRes {
    private int [] basketIdx;
    private int [] orderProdIdx;
    private int[] orderProdSideIdx;
}
