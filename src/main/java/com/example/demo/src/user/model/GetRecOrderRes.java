package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetRecOrderRes {
    private int prodIdx;
    private Timestamp createAt;
    private String ProdName;
    private int prodReviewNum;
    private float prodRatingAvg;
    private String prodImage;
    private String reviewComment;
}
