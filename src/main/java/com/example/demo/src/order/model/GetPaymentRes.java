package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class GetPaymentRes {
    GetPaymentInfoRes getPaymentInfoRes;
    List<GetPaymentDeliveryRes> getPaymentDeliveryRes;
    List<GetPaymentProdRes> getPaymentProdRes;
    List<GetPaymentOptionRes> getPaymentOptionRes;
    GetPaymentTotalDeliRes getPaymentTotalDeliRes;

}
