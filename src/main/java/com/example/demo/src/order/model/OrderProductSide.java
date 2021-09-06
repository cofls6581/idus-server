package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class OrderProductSide {
    private int orderProdSideIdx;
    private Timestamp createAt;
    private Timestamp updateAt;
    private String status;
    private int orderProdIdx;
    private int prodSideIdx;
    private int prodSideCount;
}
