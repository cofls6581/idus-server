package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetNowOrderRes {
    private int prodIdx;
    private Timestamp createAt;
    private String authorName;
    private String prodName;
    private int prodReviewNum;
    private float prodRatingAvg;
    private String prodImage;
    private String reviewComment;

}
