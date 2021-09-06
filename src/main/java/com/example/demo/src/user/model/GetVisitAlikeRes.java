package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetVisitAlikeRes {
    private int prodIdx;
    private String prodName;
    private int prodReviewNum;
    private float prodRatingAvg;
    private String prodImage;
    private String reviewComment;
}
