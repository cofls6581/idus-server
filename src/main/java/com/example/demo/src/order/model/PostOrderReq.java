package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class PostOrderReq {
    private int userIdx;
    private int prodIdx;
    private int authorIdx;
    private int [] prodCount;
    private int [] prodPrice;
    private int [] prodSideIdx;
}
