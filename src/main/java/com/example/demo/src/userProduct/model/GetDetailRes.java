package com.example.demo.src.userProduct.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDetailRes {
    private String authorName;
    private String prodName;
    private int salePercent;
    private int saleCost;
    private int prodPrice;
    private int point;
    private float prodRatingAvg;
    private int prodReviewNum;
    private int orderNum;
    private int latestLikeIdx;
    private String likeStatus;
    private int totalLike;
    private int  deliveryCost;
    private int deliveryStart;
    private String prodNum;
    private String prodComment;
    private String makeDeliveryInfo;
    private String exchangeType;
    private String exchangeInfo;
    private int totalLikeNum;
    private int totalFollowNum;
    private int totalSupportNum;
    private String authorComment;
}
