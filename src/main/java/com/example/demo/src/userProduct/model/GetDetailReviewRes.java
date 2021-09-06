package com.example.demo.src.userProduct.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDetailReviewRes {
    private int prodReviewIdx;
    private String userName;
    private String dates;
    private float  rating;
    private String reviewImage;
    private String reviewComment;
}
