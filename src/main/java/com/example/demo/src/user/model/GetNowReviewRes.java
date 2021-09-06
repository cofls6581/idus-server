package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetNowReviewRes {
    private Timestamp createAt;
    private int prodIdx;
    private String prodName;
    private int prodReviewNum;
    private float prodRatingAvg;
    private String prodImage;
    private String  reviewComment;

}
