package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class ProductReview {
    private int prodReviewIdx;
    private Timestamp createAt;
    private Timestamp updateAt;
    private String status;
    private int orderIdx;
    private int userIdx;
    private int prodIdx;
    private float rating;
    private String reviewImage;
    private String reviewComment;
}
