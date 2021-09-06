package com.example.demo.src.userProduct.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PatchProdCommentReq {
    private  int prodCommentIdx;
    private int userIdx;
}
