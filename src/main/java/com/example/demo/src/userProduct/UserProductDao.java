package com.example.demo.src.userProduct;

import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.src.userProduct.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserProductDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

     public GetProductRes getProduct(int userIdx, int prodIdx){
        String getProductQuery1 = "select i.prodImage from ProductImage i where prodIdx=? and status='Y';";
        int getProductParams1 = prodIdx;
        String getProductQuery2="select authorName,\n" +
                "       prodName,\n" +
                "       salePercent,\n" +
                "       convert(ifnull(((100-salePercent)*0.01*prodPrice),0),signed integer) as saleCost,\n" +
                "       prodPrice,\n" +
                "       point,\n" +
                "       prodRatingAvg,\n" +
                "       prodReviewNum,\n" +
                "       orderNum,\n" +
                "       latestLikeIdx,\n" +
                "       likeStatus,\n" +
                "       totalLike,\n" +
                "       deliveryCost,\n" +
                "       deliveryStart,\n" +
                "       prodNum,\n" +
                "       prodComment,\n" +
                "       makeDeliveryInfo,\n" +
                "        CASE\n" +
                "           WHEN(exchangeType=1) THEN '작가문의'\n" +
                "           WHEN(exchangeType=2) THEN '가능'\n" +
                "           WHEN(exchangeType=3) THEN '불가'\n" +
                "       ELSE  '조건확인'\n" +
                "       END as exchangeType,\n" +
                "       exchangeInfo,\n" +
                "       totalLikeNum,\n" +
                "       totalFollowNum,\n" +
                "       totalSupportNum,\n" +
                "       authorComment\n" +
                "from Product p\n" +
                "    left join(\n" +
                "        select\n" +
                "               count(prodReviewIdx) as prodReviewNum,\n" +
                "               sum(rating)/count(prodReviewIdx) as prodRatingAvg,\n" +
                "               prodIdx\n" +
                "        from ProductReview\n" +
                "        group by prodIdx\n" +
                "    )as r on r.prodIdx=p.prodIdx\n" +
                "   left join (\n" +
                "       select authorName,\n" +
                "              authorIdx,\n" +
                "              authorComment\n" +
                "       from Author\n" +
                "       )as a on a.authorIdx=p.authorIdx\n" +
                "   left join(\n" +
                "       select ooo.orderIdx,\n" +
                "              count(ooo.orderIdx) as orderNum,\n" +
                "              prodIdx\n" +
                "       from `Order` ooo\n" +
                "       left join (\n" +
                "           select prodIdx,\n" +
                "                  orderIdx\n" +
                "           from OrderProduct\n" +
                "           )as op on op.orderIdx=ooo.orderIdx\n" +
                "       group by prodIdx\n" +
                "    )as o on o.prodIdx=p.prodIdx\n" +
                "   left join(\n" +
                "       select salePercent,\n" +
                "              prodIdx\n" +
                "       from Sale\n" +
                "    )as s on s.prodIdx=p.prodIdx\n" +
                "    left join(\n" +
                "        select count(prodLikeIdx) as totalLike,\n" +
                "               prodIdx\n" +
                "        from ProductLike\n" +
                "        group by prodIdx\n" +
                "    )as l on l.prodIdx=p.prodIdx\n" +
                "    left join (\n" +
                "        select followIdx,\n" +
                "               authorIdx,\n" +
                "               count(followIdx) as totalFollowNum\n" +
                "        from Follow\n" +
                "        group by authorIdx\n" +
                "    )as f on f.authorIdx=p.authorIdx\n" +
                "    left join (\n" +
                "        select supportIdx,\n" +
                "                count(supportIdx) as totalSupportNum,\n" +
                "               authorIdx\n" +
                "        from Support\n" +
                "        group by authorIdx\n" +
                "    )as su on su.authorIdx=p.authorIdx\n" +
                "    left join (\n" +
                "        select sum(n) as totalLikeNum,\n" +
                "               authorIdx\n" +
                "        from Product pa\n" +
                "            left join(\n" +
                "                select count(prodLikeIdx) as n,\n" +
                "                       prodIdx\n" +
                "                from ProductLike\n" +
                "                group by prodIdx\n" +
                "                )as pl on pl.prodIdx=pa.prodIdx\n" +
                "        group by authorIdx\n" +
                "    )as k on k.authorIdx=p.authorIdx\n" +
                "    left join(\n" +
                "    select userIdx,\n" +
                "           prodLikeIdx as latestLikeIdx,\n" +
                "           status as likeStatus\n" +
                "    from ProductLike\n" +
                "    ORDER BY createAt DESC limit 1\n" +
                "    )as ul on ul.userIdx= userIdx=?\n" +
                "where p.prodIdx=? and status='Y' ;";

        int getProductParams2=userIdx;
        int getProductParams3=prodIdx;
        String getProductQuery3="select i.infoName, info from ProductInfo i where prodIdx=?;";
        int getProductParams4=prodIdx;
        String getProductQuery4="select prodReviewIdx,userName,date_format(createAt,'%Y년 %m월 %d일') as dates, rating, reviewImage, reviewComment " +
                "from ProductReview r join( select userIdx,userName from User)as u on u.userIdx=r.userIdx where r.prodIdx=?;";
        int getProductParams5=prodIdx;
        String getProductQuery5="select r.prodReviewIdx, prodSideCate, prodSide from ProductReview r " +
                "left join( select oo.orderIdx, prodSideCate, prodSide,orderProdIdx from `Order` oo " +
                "left join ( select p.orderIdx, p. orderProdIdx, prodSideCate, prodSide from OrderProduct p " +
                "left join( select opss.orderProdIdx, opss.prodSideIdx, prodSideCate, prodSide from OrderProductSide opss " +
                "left join ( select ps.prodSideIdx, prodSideCate, prodSide from ProductSide ps)as pss on pss.prodSideIdx=opss.prodSideIdx" +
                ")as op on op.orderProdIdx=p.orderProdIdx)as op on op.orderIdx=oo.orderIdx)as o on o.orderProdIdx=r.orderProdIdx " +
                "where r.prodIdx=? ;";
        int getProductParams6=prodIdx;
        String getProductQuery6="select keyWord from ProductKeyWord k where prodIdx=?;";
        int getProductParams7=prodIdx;
        String getProductQuery7="select pc.prodCommentIdx, userName, productComment, authorName, productReply from ProductComment pc " +
                "left join( select prodCommentIdx, productReply, createAt from ProductReply)as pr on pr.prodCommentIdx=pc.prodCommentIdx " +
                "join( select userIdx, userName from User)as u on u.userIdx=pc.userIdx " +
                "join( select prodIdx, ppp.authorIdx, authorName from Product ppp " +
                "join( select authorIdx, authorName from Author )as a on a.authorIdx=ppp.authorIdx)as p on p.prodIdx=pc.prodIdx " +
                "where pc.prodIdx=? ORDER BY pr.createAt;";
        int getProductParams8=prodIdx;
        String getProductQuery8="select idx, aa.prodImage, aa.prodName from Product p " +
                "join( select a.authorIdx, prodName, prodImage, idx from Author a " +
                "left join( select authorIdx, prodName, prodImage, idx from Product bpl " +
                "join( select prodImage, prodIdx as idx from ProductImage where mainImage='Y')as pi on idx=bpl.prodIdx " +
                "where prodIdx !=?)as pp on pp.authorIdx=a.authorIdx)as aa on aa.authorIdx=p.authorIdx where prodIdx=?;";
           int getProductParams9=prodIdx;
           int getProductParams10=prodIdx;

           String getProductQuery9="select pp.prodIdx, prodImage, pp.prodName from Product p " +
                   "left join( select prodCateIdx, bpi.prodIdx, prodImage,prodName from Product bpi " +
                   "left join( select prodImage, prodIdx from ProductImage where mainImage='Y')as pi on pi.prodIdx=bpi.prodIdx where bpi.prodIdx !=?" +
                   ")as pp on p.prodCateIdx=pp.prodCateIdx where p.prodIdx=?;";
           int getProductParams11=prodIdx;
           int getProductParams12=prodIdx;
           String getProductQuery10="select prodCateName, pp.prodIdx, prodImage, pp.prodName from Product p " +
                   "left join( select prodCateIdx, bpi.prodIdx, prodImage, prodName, likeNum from Product bpi " +
                   "left join( select prodImage, prodIdx from ProductImage where mainImage='Y')as pi on pi.prodIdx=bpi.prodIdx " +
                   "left join( select prodIdx, count(prodLikeIdx) as likeNum from ProductLike group by prodIdx)as pl on pl.prodIdx=bpi.prodIdx where bpi.prodIdx !=?" +
                   ")as pp on p.prodCateIdx=pp.prodCateIdx left join( select prodCateIdx, prodCateName from ProductCategory)as pc on pc.prodCateIdx=p.prodCateIdx " +
                   "where p.prodIdx=? ORDER BY likeNum DESC;";
           int getProductParams13=prodIdx;
           int getProductParams14=prodIdx;

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

        return new GetProductRes(
                getDetailProdImgRes=this.jdbcTemplate.query(getProductQuery1,
                        (rs,rowNum) -> new GetDetailProdImgRes(
                                rs.getString("prodImage")
                        ),getProductParams1
                ),
                getDetailRes=this.jdbcTemplate.queryForObject(getProductQuery2,
                        (rs,rowNum) -> new GetDetailRes(
                                rs.getString("authorName"),
                                rs.getString("prodName"),
                                rs.getInt("salePercent"),
                                rs.getInt("saleCost"),
                                rs.getInt("prodPrice"),
                                rs.getInt("point"),
                                rs.getFloat("prodRatingAvg"),
                                rs.getInt("prodReviewNum"),
                                rs.getInt("orderNum"),
                                rs.getInt("latestLikeIdx"),
                                rs.getString("likeStatus"),
                                rs.getInt("totalLike"),
                                rs.getInt("deliveryCost"),
                                rs.getInt("deliveryStart"),
                                rs.getString("prodNum"),
                                rs.getString("prodComment"),
                                rs.getString("makeDeliveryInfo"),
                                rs.getString("exchangeType"),
                                rs.getString("exchangeInfo"),
                                rs.getInt("totalLikeNum"),
                                rs.getInt("totalFollowNum"),
                                rs.getInt("totalSupportNum"),
                                rs.getString("authorComment")
                        ),getProductParams2,getProductParams3
                ),
                getDetailInfoRes=this.jdbcTemplate.query(getProductQuery3,
                        (rs,rowNum) -> new GetDetailInfoRes(
                                rs.getString("infoName"),
                                rs.getString("info")
                        ),getProductParams4
                ),
                getDetailReviewRes=this.jdbcTemplate.query(getProductQuery4,
                        (rs,rowNum) -> new GetDetailReviewRes(
                                rs.getInt("prodReviewIdx"),
                                rs.getString("userName"),
                                rs.getString("dates"),
                                rs.getFloat("rating"),
                                rs.getString("reviewImage"),
                                rs.getString("reviewComment")
                        ),getProductParams5
                ),
                getDetailReviewProdRes=this.jdbcTemplate.query(getProductQuery5,
                        (rs,rowNum) -> new GetDetailReviewProdRes(
                                rs.getInt("prodReviewIdx"),
                                rs.getString("prodSideCate"),
                                rs.getString("prodSide")
                        ),getProductParams6
                ),
                getDetailKeyWordRes=this.jdbcTemplate.query(getProductQuery6,
                        (rs,rowNum) -> new GetDetailKeyWordRes(
                                rs.getString("keyWord")
                        ),getProductParams7
                ),
                getDetailCommentRes=this.jdbcTemplate.query(getProductQuery7,
                        (rs,rowNum) -> new GetDetailCommentRes(
                                rs.getInt("prodCommentIdx"),
                                rs.getString("userName"),
                                rs.getString("productComment"),
                                rs.getString("authorName"),
                                rs.getString("productReply")
                        ),getProductParams8
                ),
                getDetailAuthorProdRes=this.jdbcTemplate.query(getProductQuery8,
                        (rs,rowNum) -> new GetDetailAuthorProdRes(
                                rs.getInt("idx"),
                                rs.getString("prodImage"),
                                rs.getString("prodName")
                        ),getProductParams9,getProductParams10
                ),
                getDetailCateProdRes=this.jdbcTemplate.query(getProductQuery9,
                        (rs,rowNum) -> new GetDetailCateProdRes(
                                rs.getInt("prodIdx"),
                                rs.getString("prodImage"),
                                rs.getString("prodName")
                        ),getProductParams11,getProductParams12
                ),
                getDetailHotRes=this.jdbcTemplate.query(getProductQuery10,
                        (rs,rowNum) -> new GetDetailHotRes(
                                rs.getString("prodCateName"),
                                rs.getInt("prodIdx"),
                                rs.getString("prodImage"),
                                rs.getString("prodName")
                        ),getProductParams13,getProductParams14)
        );
    }

    public int checkExistUser(int idx){
        String checkExistUserQuery = "select exists(select userIdx from User where userIdx = ? and status='Y')";
        int checkExistUserParams =idx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams);

    }
    public int checkExistProduct(int idx){
        String checkExistProductQuery = "select exists(select prodIdx from Product where prodIdx = ? and status='Y')";
        int checkExistProductParams =idx;
        return this.jdbcTemplate.queryForObject(checkExistProductQuery,
                int.class,
                checkExistProductParams);

    }

    public int createProdComment(PostProdCommentReq postProdCommentReq){
        String createProdCommentQuery = "insert into ProductComment (userIdx,prodIdx,productComment) VALUES (?,?,?)";
        Object[] createProdCommentParams = new Object[]{postProdCommentReq.getUserIdx(), postProdCommentReq.getProdIdx(), postProdCommentReq.getProductComment()};
        this.jdbcTemplate.update(createProdCommentQuery, createProdCommentParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }
    public int checkExistComment(int idx){
        String checkExistCommentQuery = "select exists(select prodCommentIdx from ProductComment where prodCommentIdx=? and status='Y')";
        int checkExistCommentParams =idx;
        return this.jdbcTemplate.queryForObject(checkExistCommentQuery,
                int.class,
                checkExistCommentParams);

    }
    public int checkCanDelete(int userIdx, int prodIdx){
        String checkCanDeleteQuery = "select exists(select userIdx, prodCommentIdx from ProductComment where userIdx=? and prodCommentIdx=?  and status='Y')";
        int checkCanDeleteParams1 =userIdx;
        int checkCanDeleteParams2 =prodIdx;
        return this.jdbcTemplate.queryForObject(checkCanDeleteQuery,
                int.class,
                checkCanDeleteParams1,checkCanDeleteParams2);

    }

    public int deleteComment(PatchProdCommentReq patchProdCommentReq){
        String deleteProdCommentQuery = "update ProductComment set status='N' where prodCommentIdx = ? ";
        Object[] deleteProdCommentParams = new Object[]{patchProdCommentReq.getProdCommentIdx()};
        return this.jdbcTemplate.update(deleteProdCommentQuery,deleteProdCommentParams);
    }

    public int checkExistOpProd(int idx){
        String checkExistCommentQuery = "select exists(select prodIdx from Product where prodIdx=? and status='Y')";
        int checkExistCommentParams =idx;
        return this.jdbcTemplate.queryForObject(checkExistCommentQuery,
                int.class,
                checkExistCommentParams);

    }

    public List<GetProdOptionRes> getProdOption(int idx){
        String getUsersByEmailQuery = "select prodSideIdx,\n" +
                "       sideCateNum,\n" +
                "       prodSideCate,\n" +
                "       prodSide,\n" +
                "       prodSidePrice\n" +
                "from ProductSide\n" +
                "where prodIdx=?";

        int getUsersByEmailParams = idx;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetProdOptionRes(
                        rs.getInt("prodSideIdx"),
                        rs.getInt("sideCateNum"),
                        rs.getString("prodSideCate"),
                        rs.getString("prodSide"),
                        rs.getInt("prodSidePrice")),
                getUsersByEmailParams);
    }

}
