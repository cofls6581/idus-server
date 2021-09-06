package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Request {
    private int requestIdx;
    private Timestamp createAt;
    private Timestamp updateAt;
    private String status;
    private int orderProdIdx;
    private int userIdx;
    private int prodIdx;
    private String request;
}
