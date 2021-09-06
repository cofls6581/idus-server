package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetNewRes {
    private int prodIdx;
    private String prodName;
    private String prodImage;
    private String authorName;
    private Timestamp createAt;
}
