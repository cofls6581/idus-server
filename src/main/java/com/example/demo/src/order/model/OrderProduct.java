package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class OrderProduct {
    private int orderProdIdx;
    private Timestamp createAt;
    private Timestamp updateAt;
    private String status;
    private int orderIdx;
    private int authorIdx;
    private int prodIdx;
    private int prodCount;
    private int userIdx;
    private String request;
}
