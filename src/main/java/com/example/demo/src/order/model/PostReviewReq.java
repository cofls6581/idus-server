package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class PostReviewReq {
    private int orderProdIdx;
    private int userIdx;
    private int prodIdx;
    private float rating;
    private String reviewImage;
    private String reviewComment;
}
