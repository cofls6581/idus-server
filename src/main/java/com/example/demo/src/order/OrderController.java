package com.example.demo.src.order;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.order.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("users/{userIdx}/orders")
public class OrderController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final OrderProvider orderProvider;
    @Autowired
    private final OrderService orderService;
    @Autowired
    private final JwtService jwtService;

    public OrderController(OrderProvider orderProvider, OrderService orderService, JwtService jwtService){
        this.orderProvider = orderProvider;
        this.orderService = orderService;
        this.jwtService = jwtService;
    }

    /**
     * 주문 생성 API
     * [POST] /users/:userIdx/orders
     * @return BaseResponse<PostOrderRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostOrderRes> createOrder(@PathVariable("userIdx") int idx,@RequestBody PostOrderReq postOrderReq) throws InterruptedException {
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if (idx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

        if(postOrderReq.getUserIdx() == 0){
            return new BaseResponse<>(POST_ORDERS_EMPTY_USERIDX);
        }
        if(postOrderReq.getProdIdx() == 0){
            return new BaseResponse<>(POST_ORDERS_EMPTY_PRODIDX);
        }
        if(postOrderReq.getAuthorIdx() == 0){
            return new BaseResponse<>(POST_ORDERS_EMPTY_AUTHORIDX);
        }
        if(postOrderReq.getProdCount() == null){
            return new BaseResponse<>(POST_ORDERS_EMPTY_PRODCOUNT);
        }
        if(postOrderReq.getProdPrice() == null){
            return new BaseResponse<>(POST_ORDERS_EMPTY_PRODPRICE);
        }
        if(postOrderReq.getProdSideIdx()==null){
            return new BaseResponse<>(POST_ORDERS_EMPTY_PRODSIDEIDX);
        }
        try{
            PostOrderRes postOrderRes = orderService.createOrder(postOrderReq);
            return new BaseResponse<>(postOrderRes);
        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 작품 리뷰 생성 API
     * [POST] /users/:userIdx/orders/reviews
     * @return BaseResponse<PostOrderRes>
     */
    // Body
    @ResponseBody
    @PostMapping("/reviews")
    public BaseResponse<PostReviewRes> createOrder(@PathVariable("userIdx") int idx,@RequestBody PostReviewReq postReviewReq) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if (idx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

        if(postReviewReq.getOrderProdIdx() == 0){
            return new BaseResponse<>(POST_REIVEWS_EMPTY_ORDERPRODIDX);
        }
        if(postReviewReq.getUserIdx() == 0){
            return new BaseResponse<>(POST_REIVEWS_EMPTY_USERIDX);
        }
        if(postReviewReq.getProdIdx() == 0){
            return new BaseResponse<>(POST_REIVEWS_EMPTY_PRODIDX);
        }
        if(postReviewReq.getRating() == 0){
            return new BaseResponse<>(POST_REIVEWS_EMPTY_RATING);
        }
        if(postReviewReq.getReviewComment() == null){
            return new BaseResponse<>(POST_REIVEWS_EMPTY_REVIEWCOMMENT);
        }

        try{
            PostReviewRes postReviewRes = orderService.createReview(postReviewReq);
            return new BaseResponse<>(postReviewRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 작품리뷰 삭제 API
     * [PATCH] /users/:userIdx/orders/reviews/status
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/reviews/status")
    public BaseResponse<String> deleteReview(@PathVariable("userIdx") int userIdx, @RequestBody ProductReview productReview){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
        try {
            PatchReviewReq patchReviewReq = new PatchReviewReq(productReview.getProdReviewIdx());
            if(patchReviewReq.getProdReviewIdx() == 0){
                return new BaseResponse<>(DELETE_REIVEWS_EMPTY_IDX);
            }
            orderService.deleteReview(patchReviewReq);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 작품리뷰 수정 API
     * [PATCH] /users/:userIdx/orders/reviews
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/reviews")
    public BaseResponse<String> modifyReview(@PathVariable("userIdx") int userIdx, @RequestBody ProductReview productReview){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
        try {
            PatchReviewInfoReq patchReviewInfoReq = new PatchReviewInfoReq(productReview.getProdReviewIdx(), productReview.getRating(),
                    productReview.getReviewImage(), productReview.getReviewComment());
            if(patchReviewInfoReq.getReviewComment() == null){
                return new BaseResponse<>(PATCH_REIVEWS_EMPTY_REVIEWCOMMENT);
            }

            if(patchReviewInfoReq.getRating() == 0){
                return new BaseResponse<>(PATCH_REIVEWS_EMPTY_RATING);
            }
            orderService.modifyReview(patchReviewInfoReq);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }



    /**
     * 즉시구매 장바구니 조회 API
     * [GET] /users/:userIdx/orders/:orderIdx/baskets
     * @return BaseResponse<GetNowBuyBasketRes>
     */
    @ResponseBody
    @GetMapping("/{orderIdx}/baskets")
    public BaseResponse<GetNowBuyBasketRes> getNowBasket(@PathVariable("userIdx") int userIdx,@PathVariable("orderIdx") int orderIdx) {
         try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
        try{
            GetNowBuyBasketRes getNowBuyBasketRes = orderProvider.getNowBasket(orderIdx);
            return new BaseResponse<>(getNowBuyBasketRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 요청사항 생성 API
     * [POST] /users/:userIdx/orders/requests
     * @return BaseResponse<PostRequestRes>
     */
    // Body
    @ResponseBody
    @PostMapping("/requests")
    public BaseResponse<PostRequestRes> createOrder(@PathVariable("userIdx") int idx,@RequestBody PostRequestReq postRequestReq) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if (idx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

        if(postRequestReq.getOrderProdIdx() == null){
            return new BaseResponse<>(POST_REQUESTS_EMPTY_ORDERPRODIDX);
        }
        if(postRequestReq.getRequest() == null){
            return new BaseResponse<>(POST_REQUESTS_EMPTY_REQUEST);
        }
        if(postRequestReq.getProdIdx() == 0){
            return new BaseResponse<>(POST_REQUESTS_EMPTY_PRODIDX);
        }
        if(postRequestReq.getUserIdx() == 0){
            return new BaseResponse<>(POST_REQUESTS_EMPTY_USERIDX);
        }

        try{
            PostRequestRes postRequestRes = orderService.createRequest(postRequestReq);
            return new BaseResponse<>(postRequestRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 요청사항 수정 API
     * [PATCH] /users/:userIdx/orders/requests
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/requests")
    public BaseResponse<String> modifyRequest(@PathVariable("userIdx") int userIdx, @RequestBody Request request){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
        try {
            PatchRequestReq patchRequestReq = new PatchRequestReq(request.getRequestIdx(),request.getRequest());
            if(patchRequestReq.getRequest() == null){
                return new BaseResponse<>(PATCH_REQUESTS_EMPTY_REQUESTS);
            }
            if(patchRequestReq.getRequestIdx() == 0){
                return new BaseResponse<>(PATCH_REQUESTS_EMPTY_REQUESTIDX);
            }
            orderService.modifyRequest(patchRequestReq);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 요청사항 삭제 API
     * [PATCH] /users/:userIdx/orders/requests/status
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/requests/status")
    public BaseResponse<String> deleteRequest(@PathVariable("userIdx") int userIdx, @RequestBody Request request){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
        try {
            PatchRequestDeleteReq patchRequestDeleteReq = new PatchRequestDeleteReq(request.getRequestIdx(),request.getStatus());
            if(patchRequestDeleteReq.getRequestIdx() == 0){
                return new BaseResponse<>(DELETE_REQUESTS_EMPTY_REQUESTIDX);
            }
            orderService.deleteRequest(patchRequestDeleteReq);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 주문서 조회 API
     * [GET] /users/:userIdx/orders/:orderIdx/payments
     * @return BaseResponse<GetNowBuyBasketRes>
     */
    @ResponseBody
    @GetMapping("/{orderIdx}/payments")
    public BaseResponse<GetPaymentRes> getPayment(@PathVariable("userIdx") int userIdx,@PathVariable("orderIdx") int orderIdx) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
        try{
            GetPaymentRes getPaymentRes = orderProvider.getPayment(orderIdx);
            return new BaseResponse<>(getPaymentRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 주문 정보수정(결제페이지) API
     * [PATCH] /users/:userIdx/orders/payments
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/payments")
    public BaseResponse<String> modifyOrder(@PathVariable("userIdx") int userIdx, @RequestBody Order order){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
        try {
            PatchPaymentReq patchPaymentReq = new PatchPaymentReq(order.getOrderIdx(),order.getTotalPrice(),order.getDeliveryIdx(),order.getPaymentType());
            if(patchPaymentReq.getOrderIdx() == 0){
                return new BaseResponse<>(PATCH_ORDERS_EMPTY_ORDERIDX);
            }
            if(patchPaymentReq.getTotalPrice() == 0){
                return new BaseResponse<>(PATCH_ORDERS_EMPTY_PRICE);
            }
            if(patchPaymentReq.getDeliveryIdx() == 0){
                return new BaseResponse<>(PATCH_ORDERS_EMPTY_DELIVERYIDX);
            }
            if(patchPaymentReq.getPaymentType() == 0){
                return new BaseResponse<>(PATCH_ORDERS_EMPTY_PAYMENTTYPE);
            }
            orderService.modifyOrder(patchPaymentReq);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 장바구니 작품 생성 API
     * [POST] /users/:userIdx/orders/baskets
     * @return BaseResponse<PostRequestRes>
     */
    // Body
    @ResponseBody
    @PostMapping("/baskets")
    public BaseResponse<PostBasketRes> createBasket(@PathVariable("userIdx") int idx,@RequestBody PostBasketReq postBasketReq) throws InterruptedException {
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if (idx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

        if(postBasketReq.getUserIdx() == 0){
            return new BaseResponse<>(POST_BASKETS_EMPTY_USERIDX);
        }
        if(postBasketReq.getProdIdx() == 0){
            return new BaseResponse<>(POST_BASKETS_EMPTY_PRODIDX);
        }
        if(postBasketReq.getAuthorIdx() == 0){
            return new BaseResponse<>(POST_BASKETS_EMPTY_AUTHORIDX);
        }
        if(postBasketReq.getProdCount() == null){
            return new BaseResponse<>(POST_BASKETS_EMPTY_PRODCOUNT);
        }
        if(postBasketReq.getProdPrice() == null){
            return new BaseResponse<>(POST_BASKETS_EMPTY_PRODPRICE);
        }
        if(postBasketReq.getProdSideIdx() == null){
            return new BaseResponse<>(POST_BASKETS_EMPTY_PRODSIDEIDX);
        }

        try{
            PostBasketRes postBasketRes = orderService.createBasket(postBasketReq);
            return new BaseResponse<>(postBasketRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 장바구니 API
     * [GET] /users/:userIdx/orders/baskets
     * @return BaseResponse<GetBasketRes>
     */
    @ResponseBody
    @GetMapping("baskets")
    public BaseResponse<GetBasketRes> getBasket(@PathVariable("userIdx") int userIdx) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
        try{
            GetBasketRes getBasketRes = orderProvider.getBasket(userIdx);
            return new BaseResponse<>(getBasketRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 장바구니 주문 생성 API
     * [POST] /users/:userIdx/orders/baskets/payments
     * @return BaseResponse<PostOrderRes>
     */
    // Body
    @ResponseBody
    @PostMapping("/baskets/payments")
    public BaseResponse<PostBasketOrderRes> createBasketOrder(@PathVariable("userIdx") int idx,@RequestBody PostBasketOrderReq postBasketOrderReq) throws InterruptedException {
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if (idx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

        if(postBasketOrderReq.getBasketIdx() == null){
            return new BaseResponse<>(POST_BASKETORDERS_EMPTY_BASKETIDX);
        }
        if(postBasketOrderReq.getOrderProdIdx()==null){
            return new BaseResponse<>(POST_BASKETORDERS_EMPTY_ORDERPRODIDX);
        }
        try{
            PostBasketOrderRes postBasketOrderRes = orderService.createBasketOrder(postBasketOrderReq,idx);
            return new BaseResponse<>(postBasketOrderRes);
        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저 주문 배송 조회 API
     * [GET] /users/:userIdx/orders
     * @return BaseResponse<GetUserOrderRes>
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse <List<GetUserOrderRes>> getUserOrder(@PathVariable("userIdx") int userIdx) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
        try{
            List<GetUserOrderRes> getUserOrderRes = orderProvider.getUserOrder(userIdx);
            return new BaseResponse<>(getUserOrderRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 장바구니 수량 수정 API
     * [PATCH] /users/:userIdx/orders/baskets
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/baskets")
    public BaseResponse<String> modifyProdN(@PathVariable("userIdx") int userIdx,@RequestBody OrderProduct orderProduct){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
        try {
            PatchBasketProdNReq patchBasketProdNReq = new PatchBasketProdNReq(orderProduct.getOrderProdIdx(),orderProduct.getProdCount());
            if(patchBasketProdNReq.getOrderProdIdx() == 0){
                return new BaseResponse<>(PATCH_BASKETS_EMPTY_ORDERPRODIDX);
            }
            if(patchBasketProdNReq.getProdCount() == 0){
                return new BaseResponse<>(PATCH_BASKETS_EMPTY_PRODN);
            }
            orderService.modifyProdN(patchBasketProdNReq);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 장바구니 삭제 API
     * [PATCH] /users/:userIdx/orders/baskets/status
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/baskets/status")
    public BaseResponse<String> deleteBasket(@PathVariable("userIdx") int userIdx, @RequestBody Basket basket){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
        try {
            PatchBasketReq patchBasketReq = new PatchBasketReq(basket.getBasketIdx());
            if(patchBasketReq.getBasketIdx() == 0){
                return new BaseResponse<>(DELETE_BASKET_EMPTY_IDX);
            }
            orderService.deleteBasket(patchBasketReq);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
