package com.example.demo.src.userProduct.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDetailCommentRes {
    private int prodCommentIdx;
    private String userName;
    private String productComment;
    private String authorName;
    private String productReply;

}
