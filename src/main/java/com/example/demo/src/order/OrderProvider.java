package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.order.model.*;
import com.example.demo.src.userProduct.model.GetProductRes;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class OrderProvider {
    private final OrderDao orderDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OrderProvider(OrderDao orderDao, JwtService jwtService) {
        this.orderDao = orderDao;
        this.jwtService = jwtService;
    }


    public int checkExistOrderUser(int idx) throws BaseException {
        try {
            return orderDao.checkExistOrderUser(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
     public int checkExistOrderProd(int idx) throws BaseException {
        try {
            return orderDao.checkExistOrderProd(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
     public int checkExistOrderAuth(int idx) throws BaseException {
        try {
            return orderDao.checkExistOrderAuth(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /* public int checkExistOrderProdSide(int idx) throws BaseException {
        try {
            return orderDao.checkExistOrderProdSide(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }*/

    public int checkExistReviewUser(int idx) throws BaseException {
        try {
            return orderDao.checkExistOrderUser(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkExistReviewProd(int idx) throws BaseException {
        try {
            return orderDao.checkExistOrderProd(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkExistReview(int opidx, int uidx) throws BaseException {
        try {
            return orderDao.checkExistReview(opidx,uidx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkAuthorityReview(int opidx, int uidx) throws BaseException {
        try {
            return orderDao.checkAuthorityReview(opidx,uidx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkDeletedReview(int rIdx) throws BaseException{
        try{
            return orderDao.checkDeletedReview(rIdx);
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkExistReviewIdx(int ridx) throws BaseException {
        try {
            return orderDao.checkExistReviewIdx(ridx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkExistOrderProdIdx(int idx) throws BaseException {
        try {
            return orderDao.checkExistOrderProdIdx(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkExistOrder(int idx) throws BaseException {
        try {
            return orderDao.checkExistOrder(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetNowBuyBasketRes getNowBasket(int orderIdx) throws BaseException {
        if(checkExistOrder(orderIdx)==0){
            throw new BaseException(GET_NOWBASKETS_NOTEXISTS_ORDER);
        }
        try {
            GetNowBuyBasketRes getNowBuyBasketRes = orderDao.getNowBasket(orderIdx);
            return getNowBuyBasketRes;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkExistRequestUser(int idx) throws BaseException {
        try {
            return orderDao.checkExistRequestUser(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkExistRequestProd(int idx) throws BaseException {
        try {
            return orderDao.checkExistRequestProd(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkExistRequest(int opidx) throws BaseException {
        try {
            return orderDao.checkExistRequest(opidx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkAuthorityRequest(int opidx,int uidx,int pIdx) throws BaseException {
        try {
            return orderDao.checkAuthorityRequest(opidx,uidx,pIdx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public int checkExistRequestIdx(int ridx) throws BaseException {
        try {
            return orderDao.checkExistRequestIdx(ridx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetPaymentRes getPayment(int orderIdx) throws BaseException {
        if(checkExistOrder(orderIdx)==0){
            throw new BaseException(GET_PAYMENTS_NOTEXISTS_ORDER);
        }
        try {
            GetPaymentRes getPaymentRes = orderDao.getPayment(orderIdx);
            return getPaymentRes;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkExistDelivery(int idx) throws BaseException {
        try {
            return orderDao.checkExistDelivery(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkExistProdSide(int idx) throws BaseException {
        try {
            return orderDao.checkExistProdSide(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetBasketRes getBasket(int userIdx) throws BaseException {
        if(checkExistOrderUser(userIdx)==0){
            throw new BaseException(GET_BASKETS_NOTEXISTS_USER);
        }
        try {
            GetBasketRes getBasketRes = orderDao.getBasket(userIdx);
            return getBasketRes;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkExistBasket(int idx) throws BaseException {
        try {
            return orderDao.checkExistBasket(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserOrderRes> getUserOrder(int userIdx) throws BaseException {
        if(checkExistOrderUser(userIdx)==0){
            throw new BaseException(GET_USERORDERS_NOTEXISTS_USER);
        }
        try {
            List<GetUserOrderRes> getUserOrderRes = orderDao.getUserOrder(userIdx);
            return getUserOrderRes;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
