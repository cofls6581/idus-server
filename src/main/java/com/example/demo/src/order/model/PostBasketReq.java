package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
public class PostBasketReq {
    private int userIdx;
    private int prodIdx;
    private int authorIdx;
    private int [] prodCount;
    private int [] prodPrice;
    private int [] prodSideIdx;
}
