package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
public class Basket {
    private int basketIdx;
    private Timestamp createAt;
    private Timestamp updateAt;
    private String status;
    private int userIdx;
    private int orderProdIdx;
}
