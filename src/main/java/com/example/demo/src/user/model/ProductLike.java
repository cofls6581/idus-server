package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductLike {
    private int prodLikeIdx;
    private int prodIdx;
    private int userIdx;
    private String status;
}
