package com.example.demo.src.userProduct.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetProductRes {
    List<GetDetailProdImgRes> getDetailProdImgRes;
    GetDetailRes getDetailRes;
    List<GetDetailInfoRes> getDetailInfoRes;
    List<GetDetailReviewRes> getDetailReviewRes;
    List<GetDetailReviewProdRes> getDetailReviewProdRes;
    List<GetDetailKeyWordRes> getDetailKeyWordRes;
    List<GetDetailCommentRes> getDetailCommentRes;
    List<GetDetailAuthorProdRes> getDetailAuthorProdRes;
    List<GetDetailCateProdRes> getDetailCateProdRes;
    List<GetDetailHotRes> getDetailHotRes;

}
