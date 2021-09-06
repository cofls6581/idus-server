package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.order.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class OrderService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderDao orderDao;
    private final OrderProvider orderProvider;
    private final JwtService jwtService;

    @Autowired
    public OrderService(OrderDao orderDao, OrderProvider orderProvider, JwtService jwtService) {
        this.orderDao = orderDao;
        this.orderProvider = orderProvider;
        this.jwtService = jwtService;
    }
    /*
    @Transactional
    public PostOrderRes createOrder(PostOrderReq postOrderReq) throws BaseException {
        if(orderProvider.checkExistOrderUser(postOrderReq.getUserIdx())==0){
            throw new BaseException(POST_ORDERS_NOTEXISTS_USER);
        }
        if(orderProvider.checkExistOrderProd(postOrderReq.getProdIdx()) ==0){
            throw new BaseException(POST_ORDERS_NOTEXISTS_PROD);
        }
        if(orderProvider.checkExistOrderAuth(postOrderReq.getAuthorIdx()) ==0){
            throw new BaseException(POST_ORDERS_NOTEXISTS_AUTHOR);
        }
        int orderProdIdx;
        orderProdIdx=orderDao.createOrderProd(postOrderReq);
        int [] orderProdSideIdx=orderDao.createOrderProdSide(postOrderReq);
        return new PostOrderRes(orderProdIdx,orderProdSideIdx);
    }*/

    //수정중
    /*@Transactional
    public PostOrderRes createOrder(PostOrderReq postOrderReq) throws BaseException {
        if(orderProvider.checkExistOrderUser(postOrderReq.getUserIdx())==0){
            throw new BaseException(POST_ORDERS_NOTEXISTS_USER);
        }
        if(orderProvider.checkExistOrderProd(postOrderReq.getProdIdx()) ==0){
            throw new BaseException(POST_ORDERS_NOTEXISTS_PROD);
        }
        if(orderProvider.checkExistOrderAuth(postOrderReq.getAuthorIdx()) ==0){
            throw new BaseException(POST_ORDERS_NOTEXISTS_AUTHOR);
        }
        int orderProdIdx;
        orderProdIdx=orderDao.createOrderProd(postOrderReq);
        int n=postOrderReq.getProdSideN();
        int [] orderProdSideIdx= new int[n];
        int i=0;
        while(i<n){
            orderProdSideIdx[i]=orderDao.createOrderProdSide(postOrderReq,i);
            i++;
        }
        return new PostOrderRes(orderProdIdx,orderProdSideIdx);
    }*/
  /*  @Transactional
    public PostOrderRes createOrder(PostOrderReq postOrderReq) throws BaseException, InterruptedException {
        if(orderProvider.checkExistOrderUser(postOrderReq.getUserIdx())==0){
            throw new BaseException(POST_ORDERS_NOTEXISTS_USER);
        }
        if(orderProvider.checkExistOrderProd(postOrderReq.getProdIdx()) ==0){
            throw new BaseException(POST_ORDERS_NOTEXISTS_PROD);
        }
        if(orderProvider.checkExistOrderAuth(postOrderReq.getAuthorIdx()) ==0){
            throw new BaseException(POST_ORDERS_NOTEXISTS_AUTHOR);
        }
        int orderIdx;
        orderIdx=orderDao.createOrder(postOrderReq);
        int n=postOrderReq.getProdPrice().length;
        int [] orderProdIdx=new int[n];
        int m=postOrderReq.getProdSideIdx().length;
        int [] orderProdSideIdx= new int[m];
        int k=m/n;
        int i=0;
        for(;i<n;i++) {
            orderProdIdx[i]=orderDao.createOrderProd(postOrderReq,i);
        }
        int j=0;
        for(;j<m;j++){
            orderProdSideIdx[j] = orderDao.createOrderProdSide(postOrderReq,j,m,k);
        }
        return new PostOrderRes(orderIdx,orderProdIdx,orderProdSideIdx);
    }*/



     @Transactional
     public PostOrderRes createOrder(PostOrderReq postOrderReq) throws BaseException, InterruptedException {
        if(orderProvider.checkExistOrderUser(postOrderReq.getUserIdx())==0){
            throw new BaseException(POST_ORDERS_NOTEXISTS_USER);
        }
        if(orderProvider.checkExistOrderProd(postOrderReq.getProdIdx()) ==0){
            throw new BaseException(POST_ORDERS_NOTEXISTS_PROD);
        }
        if(orderProvider.checkExistOrderAuth(postOrderReq.getAuthorIdx()) ==0){
            throw new BaseException(POST_ORDERS_NOTEXISTS_AUTHOR);
        }
        int orderIdx;
        orderIdx=orderDao.createOrder(postOrderReq);
        int n=postOrderReq.getProdPrice().length;
        int [] orderProdIdx=new int[n];
        int m=postOrderReq.getProdSideIdx().length;
        int [] orderProdSideIdx= new int[m];
        int [] ps = new int [m];
        ps=postOrderReq.getProdSideIdx();
        int k=m/n;
            for(int i=0;i<m;i++) {
                if(orderProvider.checkExistProdSide(ps[i]) ==0){
                    throw new BaseException(POST_ORDERS_NOTEXISTS_PRODSIDE);
                }
            }

        for(int i=0;i<n;i++) {
            orderProdIdx[i]=orderDao.createOrderProd(postOrderReq,i);
            for (int j=k*i; j< (i*k)+k; j++) {
                orderProdSideIdx[j] = orderDao.createOrderProdSide(postOrderReq,j);
            }
        }
        return new PostOrderRes(orderIdx,orderProdIdx,orderProdSideIdx);
    }



    public PostReviewRes createReview(PostReviewReq postReviewReq) throws BaseException {
        if(orderProvider.checkExistReviewUser(postReviewReq.getUserIdx())==0){
            throw new BaseException(POST_REVIEWS_NOTEXISTS_USER);
        }
        if(orderProvider.checkExistReviewProd(postReviewReq.getProdIdx())==0){
            throw new BaseException(POST_REVIEWS_NOTEXISTS_PROD);
        }
        if(orderProvider.checkExistReview(postReviewReq.getOrderProdIdx(),postReviewReq.getUserIdx()) ==1){
            throw new BaseException(POST_REVIEWS_EXISTS_STATUS);
        }
        if(orderProvider.checkAuthorityReview(postReviewReq.getOrderProdIdx(),postReviewReq.getUserIdx()) ==0){
            throw new BaseException(POST_REVIEWS_NOTHAVE_AUTHORITY);
        }

        try{
            int prodReviewIdx = orderDao.createReview(postReviewReq);
            return new PostReviewRes(prodReviewIdx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteReview(PatchReviewReq patchReviewReq) throws BaseException {
        if(orderProvider.checkExistReviewIdx(patchReviewReq.getProdReviewIdx())==0){
            throw new BaseException(PATCH_REVIEWS_NOTEXISTS_PROD);
        }
        if(orderProvider.checkDeletedReview(patchReviewReq.getProdReviewIdx()) ==1){
            throw new BaseException(REVIEWS_DUPLICATE_DELETED);
        }
        try{
            int result = orderDao.deleteReview(patchReviewReq);
            if(result == 0){
                throw new BaseException(DELETE_FAIL_LIKE);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyReview(PatchReviewInfoReq patchReviewInfoReq) throws BaseException {
        if(orderProvider.checkExistReviewIdx(patchReviewInfoReq.getProdReviewIdx())==0){
            throw new BaseException(PATCH_REVIEWINFOS_NOTEXISTS_PROD);
        }
        try{
            int result = orderDao.modifyReview(patchReviewInfoReq);
            if(result == 0){
                throw new BaseException(DELETE_FAIL_LIKE);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public PostRequestRes createRequest(PostRequestReq postRequestReq) throws BaseException {
        if(orderProvider.checkExistRequestUser(postRequestReq.getUserIdx())==0){
            throw new BaseException(POST_REQUESTS_NOTEXISTS_USER);
        }
        if(orderProvider.checkExistRequestProd(postRequestReq.getProdIdx())==0){
            throw new BaseException(POST_REQUESTS_NOTEXISTS_PROD);
        }
        int n=postRequestReq.getOrderProdIdx().length;
        int [] opIdx=postRequestReq.getOrderProdIdx();
        for(int i=0;i<n;i++) {
            if (orderProvider.checkExistRequest(opIdx[i]) == 1) {
                throw new BaseException(POST_REQUESTS_EXISTS_STATUS);
            }
            if (orderProvider.checkAuthorityRequest(opIdx[i], postRequestReq.getUserIdx(),postRequestReq.getProdIdx()) == 0) {
                throw new BaseException(POST_REQUESTS_NOTHAVE_AUTHORITY);
            }
        }

        try{
            int [] requestIdx  = orderDao.createRequest(postRequestReq);
            return new PostRequestRes(requestIdx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public void modifyRequest(PatchRequestReq patchRequestReq) throws BaseException {
        if(orderProvider.checkExistRequestIdx(patchRequestReq.getRequestIdx())==0){
            throw new BaseException(PATCH_REQUEST_NOTEXISTS_REQUESTIDX);
        }
        try{
            int result = orderDao.modifyRequest(patchRequestReq);
            if(result == 0){
                throw new BaseException(PATCH_FAIL_REQUEST);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public void deleteRequest(PatchRequestDeleteReq patchRequestDeleteReq) throws BaseException {
        if(orderProvider.checkExistRequestIdx(patchRequestDeleteReq.getRequestIdx())==0){
            throw new BaseException(DELETE_REQUEST_NOTEXISTS_REQUESTIDX);
        }
        try{
            int result = orderDao.deleteRequest(patchRequestDeleteReq);
            if(result == 0){
                throw new BaseException(DELETE_FAIL_REQUEST);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyOrder(PatchPaymentReq patchPaymentReq) throws BaseException {
        if(orderProvider.checkExistOrder(patchPaymentReq.getOrderIdx())==0){
            throw new BaseException(PATCH_ORDERS_NOTEXISTS_ORDERIDX);
        }
        if(orderProvider.checkExistDelivery(patchPaymentReq.getDeliveryIdx())==0){
            throw new BaseException(PATCH_ORDERS_NOTEXISTS_DELIVERYIDX);
        }
        try{
            int result = orderDao.modifyOrder(patchPaymentReq);
            if(result == 0){
                throw new BaseException(PATCH_FAIL_REQUEST);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public PostBasketRes createBasket(PostBasketReq postBasketReq) throws BaseException, InterruptedException {
        if(orderProvider.checkExistOrderUser(postBasketReq.getUserIdx())==0){
            throw new BaseException(POST_BASKETS_NOTEXISTS_USER);
        }
        if(orderProvider.checkExistOrderProd(postBasketReq.getProdIdx()) ==0){
            throw new BaseException(POST_BASKETS_NOTEXISTS_PROD);
        }
        if(orderProvider.checkExistOrderAuth(postBasketReq.getAuthorIdx()) ==0){
            throw new BaseException(POST_BASKETS_NOTEXISTS_AUTHOR);
        }

        int n=postBasketReq.getProdPrice().length;
        int [] orderProdIdx=new int[n];
        int [] basketIdx=new int[n];
        int m=postBasketReq.getProdSideIdx().length;
        int [] orderProdSideIdx= new int[m];
        int [] ps = new int [m];
        ps=postBasketReq.getProdSideIdx();
        int k=m/n;
        for(int i=0;i<m;i++) {
                if(orderProvider.checkExistProdSide(ps[i]) ==0){
                    throw new BaseException(POST_BASKETS_NOTEXISTS_PRODSIDEIDX);
                }
        }
        for(int i=0;i<n;i++) {
            orderProdIdx[i]=orderDao.createOrderProd(postBasketReq,i);
            basketIdx[i]=orderDao.createBasket(postBasketReq);
            for (int j=k*i; j< (i*k)+k; j++) {
                orderProdSideIdx[j] = orderDao.createOrderProdSide(postBasketReq,j);
            }
        }
        return new PostBasketRes(basketIdx,orderProdIdx,orderProdSideIdx);
    }

    @Transactional
    public PostBasketOrderRes createBasketOrder(PostBasketOrderReq postBasketOrderReq,int userIdx) throws BaseException, InterruptedException {
        int orderIdx;
        orderIdx=orderDao.createOrderBasket(postBasketOrderReq,userIdx);
        int n=postBasketOrderReq.getBasketIdx().length;
        int [] ps = new int [n];
        ps=postBasketOrderReq.getOrderProdIdx();
        int [] b=new int[n];
        b=postBasketOrderReq.getBasketIdx();
        for(int i=0;i<n;i++) {
            if(orderProvider.checkExistOrderProdIdx(ps[i]) ==0){
                throw new BaseException(POST_ORDERS_NOTEXISTS_ORDERPRODIDX);
            }
            if(orderProvider.checkExistBasket(b[i]) ==0){
                throw new BaseException(POST_ORDERS_NOTEXISTS_BASKETIDX);
            }
        }
        for(int i=0;i<n;i++) {
            orderDao.putOrderIdx(postBasketOrderReq,i);
            orderDao.deleteBasket(postBasketOrderReq,i);
        }
        return new PostBasketOrderRes(orderIdx);
    }

    public void modifyProdN(PatchBasketProdNReq patchBasketProdNReq) throws BaseException {
        if(orderProvider.checkExistOrderProdIdx(patchBasketProdNReq.getOrderProdIdx())==0){
            throw new BaseException(PATCH_BASKETS_NOTEXISTS_ORDERPRODIDX);
        }
        try{
            int result = orderDao.modifyProdN(patchBasketProdNReq);
            if(result == 0){
                throw new BaseException(PATCH_FAIL_PRODN);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteBasket(PatchBasketReq patchBasketReq) throws BaseException {
        if(orderProvider.checkExistBasket(patchBasketReq.getBasketIdx())==0){
            throw new BaseException(DELETE_BASKETS_NOTEXISTS_BASKETIDX);
        }
        try{
            int result = orderDao.deleteBasketIdx(patchBasketReq);
            if(result == 0){
                throw new BaseException(DELETE_FAIL_BASKET);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
