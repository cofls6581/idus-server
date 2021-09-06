package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.src.order.model.*;
import com.example.demo.src.user.model.GetAddressRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.demo.config.BaseResponseStatus.POST_REQUESTS_EXISTS_STATUS;
import static com.example.demo.config.BaseResponseStatus.POST_REQUESTS_NOTHAVE_AUTHORITY;

@Repository
public class OrderDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    public int checkExistOrderUser(int idx){
        String checkExistUserQuery = "select exists(select userIdx from User where userIdx = ? )";
        int checkExistUserParams =idx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams);
    }
     public int checkExistOrderProd(int idx){
        String checkExistUserQuery = "select exists(select prodIdx from Product where prodIdx = ? )";
        int checkExistUserParams =idx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams);
    }
     public int checkExistOrderAuth(int idx){
        String checkExistUserQuery = "select exists(select authorIdx from Author where authorIdx = ? )";
        int checkExistUserParams =idx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams);
    }
     public int checkExistOrderProdSide(int idx){
        String checkExistUserQuery = "select exists(select prodSideIdx from ProductSide where prodSideIdx = ? )";
        int checkExistUserParams =idx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams);
    }

    public int createOrder(PostOrderReq postOrderReq){
        String createLikeQuery = "insert into `Order` (userIdx) VALUES (?)";
        Object[] createLikeParams = new Object[]{postOrderReq.getUserIdx()};
        this.jdbcTemplate.update(createLikeQuery, createLikeParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int createOrderProd(PostOrderReq postOrderReq,int i) throws InterruptedException {
        String createOrderQuery2= "insert into OrderProduct (orderIdx) \n" +
                "SELECT orderIdx \n" +
                "FROM `Order` \n" +
                "Order By createAt DESC limit 1 ;";
        TimeUnit.SECONDS.sleep(1);
        this.jdbcTemplate.update(createOrderQuery2);
        int [] prodPriceIdx=postOrderReq.getProdPrice();
        int [] prodCount=postOrderReq.getProdCount();
        int Params1 = prodPriceIdx[i];
        int Params2= prodCount[i];
        String createLikeQuery = "update OrderProduct set authorIdx=?,prodIdx=?,prodCount=?,userIdx=?,prodPrice=? order by createAt DESC limit 1";
        Object[] createLikeParams = new Object[]{postOrderReq.getAuthorIdx(),postOrderReq.getProdIdx(),Params2,postOrderReq.getUserIdx(),Params1};
        this.jdbcTemplate.update(createLikeQuery, createLikeParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }


       public int createOrderProdSide(PostOrderReq postOrderReq,int i) throws InterruptedException {
        int returnIdx; //생성된 주문옵션테이블행들의 인덱스 값들을 담을 정수형 배열
            String createOrderQuery2= "insert into OrderProductSide (orderProdIdx) \n" +
                    "SELECT orderProdIdx \n" +
                    "FROM OrderProduct \n" +
                    "Order By createAt DESC limit 1;";
            TimeUnit.SECONDS.sleep(1);
            this.jdbcTemplate.update(createOrderQuery2);
            int [] sideIdx=postOrderReq.getProdSideIdx(); //request로 받은 주문할 옵션인덱스들을 받는 배열
            int Params1 = sideIdx[i];
            String createOrderQuery1 = "update OrderProductSide set prodSideIdx=? Order By createAt DESC limit 1";
            this.jdbcTemplate.update(createOrderQuery1,Params1);
            String lastInsertIdQuery = "select last_insert_id()";
            returnIdx=this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
         return returnIdx;
    }


    public int checkExistReview(int opidx,int uidx){
        String checkExistUserQuery = "select exists(select prodReviewIdx from ProductReview where orderProdIdx = ? and userIdx= ? and status='Y')";
        int checkExistUserParams1 =opidx;
        int checkExistUserParams2 =uidx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams1,checkExistUserParams2);
    }

    public int checkAuthorityReview(int opidx,int uidx){
        String checkExistUserQuery = "select exists(select orderProdIdx from OrderProduct where orderProdIdx = ? and userIdx= ? and status='Y')";
        int checkExistUserParams1 =opidx;
        int checkExistUserParams2 =uidx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams1,checkExistUserParams2);
    }

    public int createReview(PostReviewReq postReviewReq){
        String createLikeQuery = "insert into ProductReview (orderProdIdx,userIdx,prodIdx,rating,reviewImage,reviewComment) VALUES (?,?,?,?,?,?)";
        Object[] createLikeParams = new Object[]{postReviewReq.getOrderProdIdx(),postReviewReq.getUserIdx(),postReviewReq.getProdIdx(),postReviewReq.getRating(),
                postReviewReq.getReviewImage(),postReviewReq.getReviewComment()};
        this.jdbcTemplate.update(createLikeQuery, createLikeParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }


        public int checkDeletedReview(int rIdx){
            String checkLikeQuery = "select exists(select prodReviewIdx from ProductReview where prodReviewIdx = ? and status='N' )";
            int checkLikeParams =rIdx;
            return this.jdbcTemplate.queryForObject(checkLikeQuery,
                    int.class,
                    checkLikeParams);
        }

    public int deleteReview(PatchReviewReq patchReviewReq){
        String deleteLikeQuery = "update ProductReview set status='N' where prodReviewIdx = ? ";
        Object[] deleteLikeParams = new Object[]{patchReviewReq.getProdReviewIdx()};
        return this.jdbcTemplate.update(deleteLikeQuery,deleteLikeParams);
    }


    public int checkExistReviewIdx(int ridx){
        String checkExistUserQuery = "select exists(select prodReviewIdx from ProductReview where orderProdIdx = ? and status='Y' )";
        int checkExistUserParams1 =ridx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams1);
    }

    public int modifyReview(PatchReviewInfoReq patchReviewInfoReq){
        String modifyUserNameQuery = "update ProductReview set rating = ?,reviewImage=?,reviewComment=? where prodReviewIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchReviewInfoReq.getRating(),patchReviewInfoReq.getReviewImage(),patchReviewInfoReq.getReviewComment(),patchReviewInfoReq.getProdReviewIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

    public int checkExistOrderProdIdx(int idx){
        String checkExistUserQuery = "select exists(select orderProdIdx from OrderProduct where orderProdIdx = ? )";
        int checkExistUserParams =idx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams);
    }

    public int checkExistOrder(int idx){
        String checkExistUserQuery = "select exists(select orderIdx from `Order` where orderIdx = ? )";
        int checkExistUserParams =idx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams);
    }

    public GetNowBuyBasketRes getNowBasket(int oIdx){
        String getQuery1 = "select o.orderIdx,\n" +
                "       prodIdx,\n" +
                "       authorName,\n" +
                "       prodImage,\n" +
                "        opp.prodName,\n" +
                "       opp. prodNum,\n" +
                "       (CASE when sum(prodPrice)>=minFreeCost then 0\n" +
                "           else deliveryCost\n" +
                "        END) as deliveryCost,\n" +
                "       minFreeCost\n" +
                "from `Order` o\n" +
                "left join (\n" +
                "    select op.orderIdx,\n" +
                "           a.authorIdx,\n" +
                "           a.authorName,\n" +
                "           prodName,\n" +
                "           prodNum,\n" +
                "           prodImage,\n" +
                "           op.prodIdx,\n" +
                "           minFreeCost,\n" +
                "           deliveryCost,\n" +
                "           prodPrice\n" +
                "    from OrderProduct op\n" +
                "    left join (\n" +
                "        select authorIdx,\n" +
                "               authorName\n" +
                "        from Author\n" +
                "        group by authorIdx\n" +
                "        )as a on a.authorIdx=op.authorIdx\n" +
                "    left join (\n" +
                "        select pp.prodIdx,\n" +
                "               prodName,\n" +
                "               prodNum,\n" +
                "               minFreeCost,\n" +
                "               prodImage,\n" +
                "               deliveryCost\n" +
                "        from Product pp\n" +
                "        left join (\n" +
                "            select prodIdx,\n" +
                "                   prodImage\n" +
                "            from ProductImage i\n" +
                "            where mainImage='Y'\n" +
                "            )as pi on pi.prodIdx=pp.prodIdx\n" +
                "        group by prodIdx\n" +
                "        )as p on p.prodIdx=op.prodIdx\n" +
                "    )as opp on opp.orderIdx=o.orderIdx\n" +
                "where o.orderIdx=?\n" +
                "group by prodIdx;\n";
        int getParams1 = oIdx;
        String getQuery2 = "select op.orderIdx,\n" +
                "       prodIdx,\n" +
                "       orderProdIdx,\n" +
                "       prodSideCate,\n" +
                "       prodSide,\n" +
                "       optionPrice\n" +
                "from `Order`o\n" +
                "left join (\n" +
                "    select prodIdx,\n" +
                "           orderIdx,\n" +
                "           ops.orderProdIdx,\n" +
                "           prodSideCate,\n" +
                "           prodSide,\n" +
                "           (prodPrice*prodCount) as optionPrice \n" +
                "    from OrderProduct opp\n" +
                "    left join (\n" +
                "        select orderProdIdx,\n" +
                "            prodSideCate,\n" +
                "            prodSide,\n" +
                "            k.prodSideIdx\n" +
                "        from OrderProductSide k\n" +
                "        left join(\n" +
                "            select prodSideIdx,\n" +
                "                   prodSideCate,\n" +
                "                   prodSide\n" +
                "            from ProductSide\n" +
                "            )as ps on ps.prodSideIdx=k.prodSideIdx\n" +
                "        )as ops on ops.orderProdIdx=opp.orderProdIdx\n" +
                "    )as op on op.orderIdx=o.orderIdx\n" +
                "where o.orderIdx=?;";
        int getParams2 = oIdx;

        GetNowBasketinfoRes getNowBasketinfoRes;
        List<GetNowBasketOptionRes> getNowBasketOptionRes;

        return new GetNowBuyBasketRes(
                getNowBasketinfoRes=this.jdbcTemplate.queryForObject(getQuery1,
                        (rs,rowNum)-> new GetNowBasketinfoRes (
                                rs.getInt("orderIdx"),
                                rs.getString("authorName"),
                                rs.getString("prodName"),
                                rs.getString("prodNum"),
                                rs.getString("prodImage"),
                                rs.getInt("deliveryCost"),
                                rs.getInt("minFreeCost")
                              ),getParams1),
                getNowBasketOptionRes=this.jdbcTemplate.query(getQuery2,
                                (rs,rowNum)-> new GetNowBasketOptionRes (
                                        rs.getInt("orderIdx"),
                                        rs.getInt("prodIdx"),
                                        rs.getInt("orderProdIdx"),
                                        rs.getString("prodSideCate"),
                                        rs.getString("prodSide"),
                                        rs.getInt("optionPrice")
                                     ),getParams2)
        );

    }

    public int checkExistRequestUser(int idx){
        String checkExistUserQuery = "select exists(select userIdx from `User` where userIdx= ? and status='Y')";
        int checkExistUserParams1 =idx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams1);
    }
    public int checkExistRequestProd(int idx){
        String checkExistUserQuery = "select exists(select prodIdx from Product where prodIdx = ?  and status='Y')";
        int checkExistUserParams1 =idx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams1);
    }
    public int checkExistRequest(int opidx){
        String checkExistUserQuery = "select exists(select requestIdx from Request where orderProdIdx = ? and status='Y')";
        int checkExistUserParams1 =opidx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams1);
    }
    public int checkAuthorityRequest(int opidx,int uidx,int pidx){
        String checkExistUserQuery = "select exists(select orderProdIdx from OrderProduct where prodIdx = ? and userIdx= ? and orderProdIdx=? and status='Y')";
        int checkExistUserParams1 =opidx;
        int checkExistUserParams2 =uidx;
        int checkExistUserParams3 =pidx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams3,checkExistUserParams2,checkExistUserParams1);
    }

    public int[] createRequest(PostRequestReq postRequestReq){
        int n=postRequestReq.getOrderProdIdx().length;
        int [] opIdx=postRequestReq.getOrderProdIdx();
        int [] resultIdx=new int[n];
        for(int i=0;i<n;i++) {
            String createLikeQuery = "insert into Request (orderProdIdx,userIdx,prodIdx,request) VALUES (?,?,?,?)";
            Object[] createLikeParams = new Object[]{opIdx[i],postRequestReq.getUserIdx(),postRequestReq.getProdIdx(),postRequestReq.getRequest()};
            this.jdbcTemplate.update(createLikeQuery, createLikeParams);
            String lastInsertIdQuery = "select last_insert_id()";
            resultIdx[i]=this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);;
            }
        return resultIdx;
    }

    public int checkExistRequestIdx(int ridx){
        String checkExistUserQuery = "select exists(select requestIdx from Request where requestIdx = ? and status='Y')";
        int checkExistUserParams1 =ridx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams1);
    }

    public int modifyRequest(PatchRequestReq patchRequestReq){
        String modifyUserNameQuery = "update Request set request=? where requestIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{ patchRequestReq.getRequest(), patchRequestReq.getRequestIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

    public int deleteRequest(PatchRequestDeleteReq patchRequestDeleteReq){
        String modifyUserNameQuery = "update Request set status='N' where requestIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{ patchRequestDeleteReq.getRequestIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

    public GetPaymentRes getPayment(int oIdx){
        int getParams1 = oIdx;
        String getQuery1 = "select userName,\n" +
                "       phoneNum,\n" +
                "       paymentName,\n" +
                "       paymentNum,\n" +
                "       totalProdPrice\n" +
                "from `Order` o\n" +
                "left join(\n" +
                "    select userIdx,\n" +
                "           userName,\n" +
                "           phoneNum\n" +
                "    from idausDB.`User`\n" +
                "    )as u on u.userIdx=o.userIdx\n" +
                "left join (\n" +
                "    select orderIdx,\n" +
                "           sum(prodPrice*prodCount) as totalProdPrice\n" +
                "    from OrderProduct opp\n" +
                "    group by orderIdx\n" +
                "    )as op on op.orderIdx=o.orderIdx\n" +
                "left join(\n" +
                "    select paymentName,\n" +
                "           paymentNum,\n" +
                "           userIdx,\n" +
                "           mainPayment\n" +
                "    from idausDB.Payment\n" +
                "    where mainPayment='Y'\n" +
                ")as p on p.userIdx=o.userIdx\n" +
                "where o.orderIdx=?;";
        String getQuery2 = "select a.deliveryIdx,\n" +
                "       deliveryName,\n" +
                "       deliveryPhoneNum,\n" +
                "       address\n" +
                "from `Order` o\n" +
                "left join(\n" +
                "    select userIdx,\n" +
                "           deliveryIdx,\n" +
                "           deliveryName,\n" +
                "           deliveryPhoneNum,\n" +
                "           address\n" +
                "    from idausDB.DeliveryAddress\n" +
                "    )as a on a.userIdx=o.userIdx\n" +
                "where orderIdx=?;";
        String getQuery3 = "select o.orderIdx,\n" +
                "       prodIdx,\n" +
                "       authorName,\n" +
                "       prodImage,\n" +
                "        opp.prodName,\n" +
                "       opp. prodNum,\n" +
                "        (CASE when sum(prodPrice*prodCount)>=minFreeCost then 0\n" +
                "           else deliveryCost\n" +
                "        END) as deliveryCost,\n" +
                "       minFreeCost\n" +
                "from `Order` o\n" +
                "left join (\n" +
                "    select op.orderIdx,\n" +
                "           a.authorIdx,\n" +
                "           a.authorName,\n" +
                "           prodName,\n" +
                "           prodNum,\n" +
                "           prodImage,\n" +
                "           op.prodIdx,\n" +
                "           minFreeCost,\n" +
                "           deliveryCost,\n" +
                "           prodPrice,\n" +
                "           prodCount\n" +
                "    from OrderProduct op\n" +
                "    left join (\n" +
                "        select authorIdx,\n" +
                "               authorName\n" +
                "        from Author\n" +
                "        group by authorIdx\n" +
                "        )as a on a.authorIdx=op.authorIdx\n" +
                "    left join (\n" +
                "        select pp.prodIdx,\n" +
                "               prodName,\n" +
                "               prodNum,\n" +
                "               minFreeCost,\n" +
                "               prodImage,\n" +
                "               deliveryCost\n" +
                "        from Product pp\n" +
                "        left join (\n" +
                "            select prodIdx,\n" +
                "                   prodImage\n" +
                "            from ProductImage i\n" +
                "            where mainImage='Y'\n" +
                "            )as pi on pi.prodIdx=pp.prodIdx\n" +
                "        group by prodIdx\n" +
                "        )as p on p.prodIdx=op.prodIdx\n" +
                "    )as opp on opp.orderIdx=o.orderIdx\n" +
                "where o.orderIdx=?\n" +
                "group by prodIdx;";
        String getQuery4 = "select op.orderIdx,\n" +
                "       prodIdx,\n" +
                "       orderProdIdx,\n" +
                "       prodSideCate,\n" +
                "       prodSide,\n" +
                "       optionPrice\n," +
                "       prodCount\n" +
                "from `Order`o\n" +
                "left join (\n" +
                "    select prodIdx,\n" +
                "           orderIdx,\n" +
                "           ops.orderProdIdx,\n" +
                "           prodSideCate,\n" +
                "           prodSide,\n" +
                "           (prodPrice*prodCount) as optionPrice,\n" +
                "           prodCount\n" +
                "    from OrderProduct opp\n" +
                "    left join (\n" +
                "        select orderProdIdx,\n" +
                "            prodSideCate,\n" +
                "            prodSide,\n" +
                "            k.prodSideIdx\n" +
                "        from OrderProductSide k\n" +
                "        left join(\n" +
                "            select prodSideIdx,\n" +
                "                   prodSideCate,\n" +
                "                   prodSide\n" +
                "            from ProductSide\n" +
                "            )as ps on ps.prodSideIdx=k.prodSideIdx\n" +
                "        )as ops on ops.orderProdIdx=opp.orderProdIdx\n" +
                "    )as op on op.orderIdx=o.orderIdx\n" +
                "where o.orderIdx=?;";
        String getQuery5 = "select orderIdx,\n" +
                "       sum(deliveryCost) as totalDeliveryCost\n" +
                "from(\n" +
                "select o.orderIdx,\n" +
                "        (CASE when sum(prodPrice*prodCount)>=minFreeCost then 0\n" +
                "           else deliveryCost\n" +
                "        END) as deliveryCost,\n" +
                "       minFreeCost\n" +
                "from `Order` o\n" +
                "left join (\n" +
                "    select orderIdx,\n" +
                "           op.prodIdx,\n" +
                "           minFreeCost,\n" +
                "           deliveryCost,\n" +
                "           prodPrice,\n" +
                "           prodCount\n" +
                "    from OrderProduct op\n" +
                "             left join (\n" +
                "        select pp.prodIdx,\n" +
                "               minFreeCost,\n" +
                "               deliveryCost\n" +
                "        from Product pp\n" +
                "    )as p on p.prodIdx=op.prodIdx\n" +
                ")as opp on opp.orderIdx=o.orderIdx\n" +
                "where o.orderIdx=?\n" +
                "group by prodIdx)as f;";

        GetPaymentInfoRes getPaymentInfoRes;
        List<GetPaymentDeliveryRes> getPaymentDeliveryRes;
        List<GetPaymentProdRes> getPaymentProdRes;
        List<GetPaymentOptionRes> getPaymentOptionRes;
        GetPaymentTotalDeliRes getPaymentTotalDeliRes;

        return new GetPaymentRes(
                getPaymentInfoRes=this.jdbcTemplate.queryForObject(getQuery1,
                        (rs,rowNum)-> new GetPaymentInfoRes (
                                rs.getString("userName"),
                                rs.getString("phoneNum"),
                                rs.getString("paymentName"),
                                rs.getString("paymentNum"),
                                rs.getInt("totalProdPrice")
                        ),getParams1),
                getPaymentDeliveryRes=this.jdbcTemplate.query(getQuery2,
                        (rs,rowNum)-> new GetPaymentDeliveryRes (
                                rs.getInt("deliveryIdx"),
                                rs.getString("deliveryName"),
                                rs.getString("deliveryPhoneNum"),
                                rs.getString("address")
                                ),getParams1),
                getPaymentProdRes=this.jdbcTemplate.query(getQuery3,
                        (rs,rowNum)-> new GetPaymentProdRes (
                                rs.getInt("orderIdx"),
                                rs.getInt("prodIdx"),
                                rs.getString("authorName"),
                                rs.getString("prodImage"),
                                rs.getString("prodName"),
                                rs.getString("prodNum"),
                                rs.getInt("deliveryCost"),
                                rs.getInt("minFreeCost")
                                ),getParams1),
                getPaymentOptionRes=this.jdbcTemplate.query(getQuery4,
                        (rs,rowNum)-> new GetPaymentOptionRes (
                                rs.getInt("orderIdx"),
                                rs.getInt("prodIdx"),
                                rs.getInt("orderProdIdx"),
                                rs.getString("prodSideCate"),
                                rs.getString("prodSide"),
                                rs.getInt("optionPrice"),
                                rs.getInt("prodCount")
                                ),getParams1),
                getPaymentTotalDeliRes=this.jdbcTemplate.queryForObject(getQuery5,
                        (rs,rowNum)-> new  GetPaymentTotalDeliRes (
                                rs.getInt("orderIdx"),
                                rs.getInt("totalDeliveryCost")
                                ),getParams1)
        );
    }

    public int checkExistDelivery(int idx){
        String checkExistUserQuery = "select exists(select deliveryIdx from DeliveryAddress where deliveryIdx = ? )";
        int checkExistUserParams =idx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams);
    }


    public int modifyOrder(PatchPaymentReq patchPaymentReq){
        String modifyUserNameQuery = "update `Order` set deliveryIdx=?,paymentType=?,totalPrice=?,`statement`=2 where orderIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{ patchPaymentReq.getDeliveryIdx(),patchPaymentReq.getPaymentType(),patchPaymentReq.getTotalPrice(),patchPaymentReq.getOrderIdx()};
        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

    public int createOrderProd(PostBasketReq postBasketReq,int i) throws InterruptedException {
        int [] prodPriceIdx=postBasketReq.getProdPrice();
        int [] prodCount=postBasketReq.getProdCount();
        int Params1 = prodPriceIdx[i];
        int Params2= prodCount[i];
        String createLikeQuery = "insert into OrderProduct (authorIdx,prodIdx,prodCount,userIdx,prodPrice) VALUES (?,?,?,?,?) ";
        TimeUnit.SECONDS.sleep(1);
        Object[] createLikeParams = new Object[]{postBasketReq.getAuthorIdx(),postBasketReq.getProdIdx(),Params2,postBasketReq.getUserIdx(),Params1};
        this.jdbcTemplate.update(createLikeQuery, createLikeParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int createBasket(PostBasketReq postBasketReq) throws InterruptedException {
        String createOrderQuery2= "insert into Basket (orderProdIdx) \n" +
                "SELECT orderProdIdx \n" +
                "FROM `OrderProduct` \n" +
                "Order By createAt DESC limit 1 ;";
        TimeUnit.SECONDS.sleep(1);
        this.jdbcTemplate.update(createOrderQuery2);
        String createLikeQuery = "update Basket set userIdx=? order by createAt DESC limit 1";
        Object[] createLikeParams = new Object[]{postBasketReq.getUserIdx()};
        this.jdbcTemplate.update(createLikeQuery, createLikeParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int createOrderProdSide(PostBasketReq postBasketReq,int i) throws InterruptedException {
        int returnIdx;
        String createOrderQuery2= "insert into OrderProductSide (orderProdIdx) \n" +
                "SELECT orderProdIdx \n" +
                "FROM OrderProduct \n" +
                "Order By createAt DESC limit 1;";
        TimeUnit.SECONDS.sleep(1);
        this.jdbcTemplate.update(createOrderQuery2);
        int [] sideIdx=postBasketReq.getProdSideIdx(); //request로 받은 주문할 옵션인덱스들을 받는 배열
        int Params1 = sideIdx[i];
        String createOrderQuery1 = "update OrderProductSide set prodSideIdx=? Order By createAt DESC limit 1";
        this.jdbcTemplate.update(createOrderQuery1,Params1);
        String lastInsertIdQuery = "select last_insert_id()";
        returnIdx=this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
        return returnIdx;
    }

    public int checkExistProdSide(int idx){
        String checkExistUserQuery = "select exists(select prodSideIdx from ProductSide where prodSideIdx=? and status='Y')";
        int checkExistUserParams =idx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams);
    }

    public GetBasketRes getBasket(int uIdx){
        String getQuery1 = "select prodIdx,\n" +
                "       authorName,\n" +
                "       prodName,\n" +
                "       prodNum,\n" +
                "       request,\n" +
                "       sum(prodPrice*prodCount) as totalProd,\n" +
                "       (CASE when  sum(prodPrice*prodCount)>=minFreeCost then 0\n" +
                "           else deliveryCost\n" +
                "        END) as deliveryCost,\n" +
                "       prodImage\n" +
                "from Basket b\n" +
                "left join(\n" +
                "    select orderProdIdx,\n" +
                "           k.authorIdx,\n" +
                "           authorName,\n" +
                "           prodName,\n" +
                "           prodNum,\n" +
                "           k.prodIdx,\n" +
                "           request,\n" +
                "           minFreeCost,\n" +
                "           deliveryCost,\n" +
                "           prodPrice,\n" +
                "           prodCount,\n" +
                "           prodImage\n" +
                "    from OrderProduct k\n" +
                "    left join(\n" +
                "        select authorIdx,\n" +
                "               authorName\n" +
                "        from Author\n" +
                "        )as a on a.authorIdx=k.authorIdx\n" +
                "    left join (\n" +
                "        select h.prodIdx,\n" +
                "               prodName,\n" +
                "               prodNum,\n" +
                "               minFreeCost,\n" +
                "               deliveryCost,\n" +
                "               prodImage\n" +
                "        from Product h\n" +
                "        left join(\n" +
                "            select prodIdx,\n" +
                "                   prodImage\n" +
                "            from ProductImage\n" +
                "            where mainImage='Y'\n" +
                "            )as pi on pi.prodIdx=h.prodIdx\n" +
                "        )as p on p.prodIdx=k.prodIdx\n" +
                "    )as op on op.orderProdIdx=b.orderProdIdx\n" +
                "where userIdx=? and status='Y'\n" +
                "group by prodIdx;";
        String getQuery2 = "select basketIdx,\n" +
                "       prodIdx,\n" +
                "       b.orderProdIdx,\n" +
                "       prodSideCate,\n" +
                "       prodSide,\n" +
                "       optionPrice,\n" +
                "       prodCount\n" +
                "from Basket b\n" +
                "left join (\n" +
                "    select prodIdx,\n" +
                "           orderIdx,\n" +
                "           ops.orderProdIdx,\n" +
                "           prodSideCate,\n" +
                "           prodSide,\n" +
                "           (prodPrice*prodCount) as optionPrice,\n" +
                "           prodCount\n" +
                "    from OrderProduct opp\n" +
                "    left join (\n" +
                "        select orderProdIdx,\n" +
                "            prodSideCate,\n" +
                "            prodSide,\n" +
                "            k.prodSideIdx\n" +
                "        from OrderProductSide k\n" +
                "        left join(\n" +
                "            select prodSideIdx,\n" +
                "                   prodSideCate,\n" +
                "                   prodSide\n" +
                "            from ProductSide\n" +
                "            )as ps on ps.prodSideIdx=k.prodSideIdx\n" +
                "        )as ops on ops.orderProdIdx=opp.orderProdIdx\n" +
                "    )as op on op.orderProdIdx=b.orderProdIdx\n" +
                "where b.userIdx=? and status='Y';";
        int getParams = uIdx;

        List<GetBasketInfoRes> getBasketInfoRes;
        List<GetBasketPriceRes> getBasketPriceRes;

        return new GetBasketRes(
                getBasketInfoRes=this.jdbcTemplate.query(getQuery1,
                        (rs,rowNum)-> new GetBasketInfoRes (
                                rs.getInt("prodIdx"),
                                rs.getString("authorName"),
                                rs.getString("prodName"),
                                rs.getString("prodNum"),
                                rs.getString("request"),
                                rs.getInt("totalProd"),
                                rs.getInt("deliveryCost"),
                                rs.getString("prodImage")
                        ),getParams),
                getBasketPriceRes=this.jdbcTemplate.query(getQuery2,
                        (rs,rowNum)-> new GetBasketPriceRes (
                                rs.getInt("basketIdx"),
                                rs.getInt("prodIdx"),
                                rs.getInt("orderProdIdx"),
                                rs.getString("prodSideCate"),
                                rs.getString("prodSide"),
                                rs.getInt("optionPrice"),
                                rs.getInt("prodCount")
                        ),getParams)
        );

    }

    public int checkExistBasket(int idx){
        String checkExistUserQuery = "select exists(select basketIdx from Basket where basketIdx = ? and status='Y' )";
        int checkExistUserParams =idx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams);
    }

    public int createOrderBasket(PostBasketOrderReq postBasketOrderReq,int userIdx){
        String createLikeQuery = "insert into `Order` (userIdx) VALUES (?)";
        Object[] createLikeParams = new Object[]{userIdx};
        this.jdbcTemplate.update(createLikeQuery, createLikeParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public void putOrderIdx(PostBasketOrderReq postBasketOrderReq,int i) throws InterruptedException {
        int [] opIdx=postBasketOrderReq.getOrderProdIdx();
        int Params1 = opIdx[i];
        String createOrderQuery1= "    UPDATE OrderProduct\n" +
                "    SET orderIdx = (SELECT A.orderIdx\n" +
                "    FROM (\n" +
                "                    SELECT orderIdx\n" +
                "                        FROM `Order`\n" +
                "                                Order By createAt DESC limit 1\n" +
                "            ) A\n" +
                "             )\n" +
                "    WHERE orderProdIdx = ?";
        this.jdbcTemplate.update(createOrderQuery1,Params1);
        String lastInsertIdQuery = "select last_insert_id()";
        this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public void deleteBasket(PostBasketOrderReq postBasketOrderReq,int i) throws InterruptedException {
        int [] bIdx=postBasketOrderReq.getBasketIdx();
        int Params1 = bIdx[i];
        String createOrderQuery1= " UPDATE Basket set status='N' where basketIdx=?";
        this.jdbcTemplate.update(createOrderQuery1,Params1);
        String lastInsertIdQuery = "select last_insert_id()";
        this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public List<GetUserOrderRes> getUserOrder(int uIdx){
        String getQuery1 = "select DATE_FORMAT(createAt,'%Y.%m.%d') AS DATE,\n" +
                "       totalPrice,\n" +
                "\t(CASE\n" +
                "\t\tWHEN statement=2\n" +
                "\t\tTHEN '결제 완료'\n" +
                "\t\tWHEN statement=3\n" +
                "\t\tTHEN '작가 발송 완료'\n" +
                "\t\tELSE '취소완료'\n" +
                "\tEND)as  statement,\n" +
                "       prodName,\n" +
                "       authorName,\n" +
                "       prodImage\n" +
                "from `Order` o\n" +
                "left join (\n" +
                "    select orderIdx,\n" +
                "           op.prodIdx,\n" +
                "           prodName,\n" +
                "           authorName,\n" +
                "           prodImage\n" +
                "    from OrderProduct op\n" +
                "    left join(\n" +
                "        select h.prodIdx,\n" +
                "               prodName,\n" +
                "               prodImage\n" +
                "        from Product h\n" +
                "        left join(\n" +
                "            select prodIdx,\n" +
                "                   prodImage\n" +
                "            from ProductImage\n" +
                "            where mainImage='Y'\n" +
                "            )as pi on pi.prodIdx=h.prodIdx\n" +
                "        )as p on p.prodIdx=op.prodIdx\n" +
                "    left join (\n" +
                "        select authorIdx,\n" +
                "               authorName\n" +
                "        from Author\n" +
                "        )as a on a.authorIdx=op.authorIdx\n" +
                "    group by orderIdx\n" +
                "    )as k on k.orderIdx=o.orderIdx\n" +
                "where userIdx=? and statement!=1";
        int getParams1 = uIdx;
        return this.jdbcTemplate.query(getQuery1,
                (rs,rowNum) -> new GetUserOrderRes(
                        rs.getString("DATE"),
                        rs.getInt("totalPrice"),
                        rs.getString("statement"),
                        rs.getString("prodName"),
                        rs.getString("authorName"),
                        rs.getString("prodImage")
                ),getParams1
        );
    }

    public int modifyProdN(PatchBasketProdNReq patchBasketProdNReq){
        String modifyUserNameQuery = "update OrderProduct set prodCount = ? where orderProdIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchBasketProdNReq.getProdCount(),patchBasketProdNReq.getOrderProdIdx()};
        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

    public int deleteBasketIdx(PatchBasketReq patchBasketReq){
        String modifyUserNameQuery = "update Basket set status='N' where basketIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{ patchBasketReq.getBasketIdx()};
        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

}


