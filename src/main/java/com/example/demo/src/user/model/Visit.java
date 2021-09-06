package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Visit {
    private int visitIdx;
    private Timestamp createAt;
    private Timestamp updateAt;
    private String status;
    private int prodIdx;
    private int userIdx;

}
