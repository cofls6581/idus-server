package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Order {
        private int orderIdx;
        private Timestamp createAt;
        private Timestamp updateAt;
        private String status;
        private int userIdx;
        private int totalPrice;
        private int deliveryIdx;
        private int paymentType;
        private int statement;
}
