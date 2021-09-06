package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDisProdRes {
    private int prodIdx;
    private int salePercent;
    private int saleCost;
    private int  prodPrice;
    private String prodName;
    private int prodReviewNum;
    private float  prodRatingAvg;
    private String prodImage;
    private String reviewComment;
}
