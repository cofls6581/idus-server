package com.example.demo.src.userProduct.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Product {
    private int prodIdx;
    private Timestamp createAt;
    private Timestamp updateAt;
    private String status;
    private int prodCateIdx;
    private int authorIdx;
    private int saleIdx;
    private String prodName;
    private int prodPrice;
    private String safeFood;
    private int point;
    private int deliveryCost;
    private int minFreeCost;
    private int deliveryStart;
    private String prodNum;
    private String prodComment;
    private String makeDeliveryInfo;
    private String exchangeInfo;
    private int exchangeType;

}
