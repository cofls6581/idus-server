package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class GetUserOrderRes {
    private String DATE;
    private int totalPrice;
    private String statement;
    private String prodName;
    private String authorName;
    private String ProdImage;
}
