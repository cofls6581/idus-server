package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetHomeRes {
    List<GetBannerRes> getBannerRes;
    List<GetVisitAlikeRes> getVisitAlikeRes;
    List<GetTodayProdRes> getTodayProdRes;
    List<GetDisProdRes> getDisProdRes;
    List<GetRecOrderRes> getRecOrderRes;

    List<GetNowOrderRes> getNowOrderRes;
    List<GetNowReviewRes> getNowReviewRes;

    List<GetNewRes> getNewRes;
}
