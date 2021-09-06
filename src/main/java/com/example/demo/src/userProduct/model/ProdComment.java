package com.example.demo.src.userProduct.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProdComment {
    private int prodCommentIdx;
    private Timestamp createAt;
    private Timestamp updateAt;
    private String status;
    private int prodIdx;
    private int userIdx;
    private String productComment;
}
