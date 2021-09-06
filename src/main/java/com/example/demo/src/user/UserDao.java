package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

   /* public List<GetUserRes> getUsers(){
        String getUsersQuery = "select * from UserInfo";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userName"),
                        rs.getString("ID"),
                        rs.getString("Email"),
                        rs.getString("password"))
                );
    }*/

  /*  public List<GetUserRes> getUsersByEmail(String email){
        String getUsersByEmailQuery = "select * from UserInfo where email =?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userName"),
                        rs.getString("ID"),
                        rs.getString("Email"),
                        rs.getString("password")),
                getUsersByEmailParams);
    }*/

   /* public GetUserRes getUser(int userIdx){
        String getUserQuery = "select * from UserInfo where userIdx = ?";
        int getUserParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userName"),
                        rs.getString("ID"),
                        rs.getString("Email"),
                        rs.getString("password")),
                getUserParams);
    }*/
    

   public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into User (email,userName,password,phoneNum) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getEmail(), postUserReq.getUserName(), postUserReq.getPassword(), postUserReq.getPhoneNum()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }
    public int checkPhoneNum(String num){
        String checkPhoneNumQuery = "select exists(select phoneNum from User where phoneNum = ?)";
        String checkPhoneNumParams = num;
        return this.jdbcTemplate.queryForObject(checkPhoneNumQuery,
                int.class,
                checkPhoneNumParams);

    }

  /*  public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update UserInfo set userName = ? where userIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }*/

    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select userIdx,email,userName,password,phoneNum,streaming from User where email = ?";
        String getPwdParams = postLoginReq.getEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("userIdx"),
                        rs.getString("email"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("phoneNum"),
                        rs.getString("streaming")
                ),
                getPwdParams
                );
    }

    public int checkAliveUser(String mail){
        String checkExistUserQuery = "select exists(select email from User where email = ? and status='N')";
        String checkExistUserParams =mail;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams);

    }
    public int checkExistUser(String mail){
        String checkExistUserQuery = "select exists(select email from User where email = ? )";
        String checkExistUserParams =mail;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams);

    }
    public int checkExistUser(int idx){
        String checkExistUserQuery = "select exists(select deliveryIdx from DeliveryAddress where userIdx = ? )";
        int checkExistUserParams =idx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams);

    }
    public int checkExistLikeUser(int idx){
        String checkExistUserQuery = "select exists(select userIdx from  User where userIdx = ? and status='Y')";
        int checkExistUserParams =idx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams);
    }
    public int checkExistVisitUser(int idx){
        String checkExistUserQuery = "select exists(select userIdx from  Visit where userIdx = ? and status='N')";
        int checkExistUserParams =idx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams);
    }

    public int checkLike(int pIdx, int uIdx){
        String checkLikeQuery = "select exists(select prodLikeIdx from ProductLike where prodIdx = ? and userIdx=? and status='Y' )";
        int checkLikeParams1 =pIdx;
        int checkLikeParams2 = uIdx;
        return this.jdbcTemplate.queryForObject(checkLikeQuery,
                int.class,
                checkLikeParams1,checkLikeParams2);
    }
    public int checkDelivery(int dIdx){
        String checkLikeQuery = "select exists(select deliveryIdx from DeliveryAddress where deliveryIdx = ? and status='Y' )";
        int checkLikeParams1 =dIdx;
        return this.jdbcTemplate.queryForObject(checkLikeQuery,
                int.class,
                checkLikeParams1);
    }
    public int createLike(PostLikeReq postLikeReq){
        String createLikeQuery = "insert into ProductLike (prodIdx,userIdx) VALUES (?,?)";
        Object[] createLikeParams = new Object[]{postLikeReq.getProdIdx(),postLikeReq.getUserIdx()};
        this.jdbcTemplate.update(createLikeQuery, createLikeParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int deleteLike(PatchLikeReq patchLikeReq){
        String deleteLikeQuery = "update ProductLike set status='N' where prodLikeIdx = ? ";
        Object[] deleteLikeParams = new Object[]{patchLikeReq.getProdLikeIdx()};
        return this.jdbcTemplate.update(deleteLikeQuery,deleteLikeParams);
    }
    public int deleteAddress(PatchAddressReq patchAddressReq){
        String deleteLikeQuery = "update DeliveryAddress set status='N' where deliveryIdx = ? ";
        Object[] deleteLikeParams = new Object[]{patchAddressReq.getDeliveryIdx()};
        return this.jdbcTemplate.update(deleteLikeQuery,deleteLikeParams);
    }

    public int checkDeletedLike(int plIdx){
        String checkLikeQuery = "select exists(select prodLikeIdx from ProductLike where prodLikeIdx = ? and status='N'  )";
        int checkLikeParams =plIdx;
        return this.jdbcTemplate.queryForObject(checkLikeQuery,
                int.class,
                checkLikeParams);
    }

    public int checkVisit( int uIdx){
        String checkVisitQuery = "select exists(select visitIdx from Visit where userIdx=? and status='Y' )";
        int checkVisitParams2 = uIdx;
        return this.jdbcTemplate.queryForObject(checkVisitQuery,
                int.class, checkVisitParams2);
    }

    public int createVisit(PostVisitReq postVisitReq){
        String createLikeQuery = "insert into Visit (prodIdx,userIdx) VALUES (?,?)";
        Object[] createLikeParams = new Object[]{postVisitReq.getProdIdx(),postVisitReq.getUserIdx()};
        this.jdbcTemplate.update(createLikeQuery, createLikeParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int checkExistVisitProd(int idx){
        String checkVisitQuery = "select exists(select prodIdx from Product where prodIdx = ? and status='Y' )";
        int checkVisitParams1 =idx;
        return this.jdbcTemplate.queryForObject(checkVisitQuery,
                int.class,
                checkVisitParams1);
    }
    public int patchVisit(PatchVisitReq patchVisitReq){
        String deleteLikeQuery = "update Visit set prodIdx=? where userIdx = ? ";
        Object[] deleteLikeParams = new Object[]{patchVisitReq.getProdIdx(),patchVisitReq.getUserIdx()};
        return this.jdbcTemplate.update(deleteLikeQuery,deleteLikeParams);
    }


      public GetHomeRes getToday(int uIdx){
        String getUserQuery1 = "select bannerIdx, \n" +
                "       bannerImage\n" +
                "from Banner\n" +
                "ORDER BY createAt DESC limit 7;";
        String getUserQuery2="select h.prodIdx,\n" +
                "       prodName,\n" +
                "       prodReviewNum,\n" +
                "       round(prodRatingAvg, 1) as prodRatingAvg,\n" +
                "       prodImage,\n" +
                "       reviewComment\n" +
                "from Visit v\n" +
                "    left join (\n" +
                "        select prodCateIdx,\n" +
                "               prodIdx\n" +
                "        from Product\n" +
                "    )as p on p.prodIdx=v.prodIdx\n" +
                "   left join ( \n" +
                "    select k.prodCateIdx,\n" +
                "           k.prodName,\n" +
                "           k.prodIdx,\n" +
                "           prodReviewNum,\n" +
                "           prodRatingAvg,\n" +
                "           prodImage,\n" +
                "           reviewComment\n" +
                "    from Product k\n" +
                "        left join(\n" +
                "            select count(prodReviewIdx) as prodReviewNum,\n" +
                "                   sum(rating) / count(prodReviewIdx) as prodRatingAvg,\n" +
                "                   prodIdx\n" +
                "            from ProductReview\n" +
                "            group by prodIdx) as r on r.prodIdx = k.prodIdx\n" +
                "        left join( \n" +
                "            select reviewComment,\n" +
                "                   prodIdx\n" +
                "            from ProductReview\n" +
                "            group by prodIdx) as rr on rr.prodIdx = k.prodIdx\n" +
                "        left join(\n" +
                "            select prodImage,\n" +
                "                   prodIdx\n" +
                "            from ProductImage\n" +
                "            where mainImage = 'Y'\n" +
                "            ) as pi on pi.prodIdx = k.prodIdx\n" +
                "    )as h on h.prodCateIdx=p.prodCateIdx and h.prodIdx !=p.prodIdx \n" +
                "where userIdx=? limit 6;";
        int getUserParams1 = uIdx;
        String getUserQuery3="select h.prodIdx,\n" +
                  "       prodName,\n" +
                  "       prodReviewNum,\n" +
                  "       round(prodRatingAvg, 1) as prodRatingAvg,\n" +
                  "       prodImage, \n" +
                  "       reviewComment\n" +
                  "from Visit v\n" +
                  "    left join (\n" +
                  "        select prodCateIdx,\n" +
                  "               prodIdx\n" +
                  "        from Product\n" +
                  "    )as p on p.prodIdx=v.prodIdx\n" +
                  "   left join (\n" +
                  "    select k.prodCateIdx,\n" +
                  "           k.prodName,\n" +
                  "           k.prodIdx,\n" +
                  "           prodReviewNum,\n" +
                  "           prodRatingAvg,\n" +
                  "           prodImage,\n" +
                  "           reviewComment \n" +
                  "    from Product k\n" +
                  "        left join(\n" +
                  "            select count(prodReviewIdx) as prodReviewNum,\n" +
                  "                   sum(rating) / count(prodReviewIdx) as prodRatingAvg,\n" +
                  "                   prodIdx\n" +
                  "            from ProductReview\n" +
                  "            group by prodIdx) as r on r.prodIdx = k.prodIdx\n" +
                  "        left join(\n" +
                  "            select reviewComment,\n" +
                  "                   prodIdx\n" +
                  "            from ProductReview\n" +
                  "            group by prodIdx) as rr on rr.prodIdx = k.prodIdx\n" +
                  "        left join(\n" +
                  "            select prodImage,\n" +
                  "                   prodIdx \n" +
                  "            from ProductImage\n" +
                  "            where mainImage = 'Y'\n" +
                  "            ) as pi on pi.prodIdx = k.prodIdx\n" +
                  "    )as h on h.prodCateIdx !=p.prodCateIdx\n" +
                  "where userIdx=? limit 6;";
          int getUserParams2 = uIdx;
          String getUserQuery4="select  kk.prodIdx,\n" +
                  "       salePercent,\n" +
                  "       convert(ifnull(((100-salePercent)*0.01*prodPrice),0),signed integer) as saleCost, \n" +
                  "       prodPrice,\n" +
                  "       prodName,\n" +
                  "       prodReviewNum,\n" +
                  "       round(prodRatingAvg, 1) as prodRatingAvg,\n" +
                  "       prodImage,\n" +
                  "       reviewComment\n" +
                  "from Sale s\n" +
                  "    left join (\n" +
                  "    select k.prodCateIdx,\n" +
                  "           k.prodName,\n" +
                  "           k.prodIdx,\n" +
                  "           prodReviewNum,\n" +
                  "           prodRatingAvg,\n" +
                  "           prodImage,\n" +
                  "           reviewComment, \n" +
                  "           prodPrice\n" +
                  "    from Product k\n" +
                  "        left join(\n" +
                  "        select count(prodReviewIdx)               as prodReviewNum,\n" +
                  "               sum(rating) / count(prodReviewIdx) as prodRatingAvg,\n" +
                  "               prodIdx\n" +
                  "        from ProductReview\n" +
                  "        group by prodIdx) as r on r.prodIdx = k.prodIdx\n" +
                  "        left join(\n" +
                  "        select reviewComment,\n" +
                  "               prodIdx\n" +
                  "        from ProductReview\n" +
                  "       ) as rr on rr.prodIdx = k.prodIdx\n" +
                  "        left join(\n" +
                  "        select prodImage,\n" +
                  "               prodIdx\n" +
                  "        from ProductImage\n" +
                  "        where mainImage = 'Y'\n" +
                  "    ) as pi on pi.prodIdx = k.prodIdx\n" +
                  "    group by prodIdx\n" +
                  ")as kk on kk.prodIdx=s.prodIdx\n" +
                  "where status='Y';";
          String getUserQuery5="select g.*\n" +
                  "from (\n" +
                  "      select prodIdx,\n" +
                  "             createAt,\n" +
                  "             prodName,\n" +
                  "             prodReviewNum,\n" +
                  "             round(prodRatingAvg, 1) as prodRatingAvg,\n" +
                  "             prodImage,\n" +
                  "             reviewComment\n" +
                  "      from `Order` o\n" +
                  "      left join (select orderIdx,\n" +
                  "                        opp.prodIdx,\n" +
                  "                        prodCateIdx,\n" +
                  "                            prodName,\n" +
                  "                            prodReviewNum,\n" +
                  "                            prodRatingAvg,\n" +
                  "                            prodImage,\n" +
                  "                            reviewComment\n" +
                  "                 from OrderProduct opp\n" +
                  "                          left join(\n" +
                  "                     select k.prodCateIdx,\n" +
                  "                            k.prodName,\n" +
                  "                            k.prodIdx,\n" +
                  "                            prodReviewNum,\n" +
                  "                            prodRatingAvg,\n" +
                  "                            prodImage,\n" +
                  "                            reviewComment\n" +
                  "                     from Product k\n" +
                  "                              left join(\n" +
                  "                         select count(prodReviewIdx)               as prodReviewNum,\n" +
                  "                                sum(rating) / count(prodReviewIdx) as prodRatingAvg,\n" +
                  "                                prodIdx\n" +
                  "                         from ProductReview\n" +
                  "                         group by prodIdx) as r on r.prodIdx = k.prodIdx\n" +
                  "                              left join(\n" +
                  "                         select reviewComment,\n" +
                  "                                prodIdx\n" +
                  "                         from ProductReview) as rr on rr.prodIdx = k.prodIdx\n" +
                  "                              left join(\n" +
                  "                         select prodImage,\n" +
                  "                                prodIdx\n" +
                  "                         from ProductImage\n" +
                  "                         where mainImage = 'Y'\n" +
                  "                     ) as pi on pi.prodIdx = k.prodIdx\n" +
                  "                 ) as kk on kk.prodIdx = opp.prodIdx\n" +
                  "      )as op on op.orderIdx=o.orderIdx\n" +
                  "      order by createAt DESC\n" +
                  "    LIMIT 18446744073709551615\n" +
                  ")as g\n" +
                  "group by prodIdx limit 6;";

          List<GetBannerRes> getBannerRes;
          List<GetVisitAlikeRes> getVisitAlikeRes;
          List<GetTodayProdRes> getTodayProdRes;
          List<GetDisProdRes> getDisProdRes;
          List<GetRecOrderRes> getRecOrderRes;
          List<GetNowOrderRes> getNowOrderRes;
          List<GetNowReviewRes> getNowReviewRes;
          List<GetNewRes> getNewRes;
        return new GetHomeRes(
                getBannerRes=this.jdbcTemplate.query(getUserQuery1,
                        (rs,rowNum)-> new GetBannerRes(
                                rs.getInt("bannerIdx"),
                                rs.getString("bannerImage")
              )),
                getVisitAlikeRes=this.jdbcTemplate.query(getUserQuery2,
                  (rs,rowNum)-> new GetVisitAlikeRes(
                          rs.getInt("prodIdx"),
                          rs.getString("prodName"),
                          rs.getInt("prodReviewNum"),
                          rs.getFloat("prodRatingAvg"),
                          rs.getString("prodImage"),
                          rs.getString("reviewComment")
                  ),getUserParams1),
                getTodayProdRes=this.jdbcTemplate.query(getUserQuery3,
                  (rs,rowNum)-> new GetTodayProdRes(
                          rs.getInt("prodIdx"),
                          rs.getString("prodName"),
                          rs.getInt("prodReviewNum"),
                          rs.getFloat("prodRatingAvg"),
                          rs.getString("prodImage"),
                          rs.getString("reviewComment")

                  ),getUserParams2),
                getDisProdRes=this.jdbcTemplate.query(getUserQuery4,
                        (rs,rowNum)-> new GetDisProdRes (
                                rs.getInt("prodIdx"),
                                rs.getInt("salePercent"),
                                rs.getInt("saleCost"),
                                rs.getInt("prodPrice"),
                                rs.getString("prodName"),
                                rs.getInt("prodReviewNum"),
                                rs.getFloat("prodRatingAvg"),
                                rs.getString("prodImage"),
                                rs.getString("reviewComment")

                  )),
                getRecOrderRes=this.jdbcTemplate.query(getUserQuery5,
                        (rs,rowNum)-> new GetRecOrderRes(
                                rs.getInt("prodIdx"),
                                rs.getTimestamp("createAt"),
                                rs.getString("prodName"),
                                rs.getInt("prodReviewNum"),
                                rs.getFloat("prodRatingAvg"),
                                rs.getString("prodImage"),
                                rs.getString("reviewComment"))
                 ),
                getNowOrderRes=null,getNowReviewRes=null,getNewRes=null
        );
    }

    public GetHomeRes getNowOrder(){
        String getUserQuery1 = "select g.* \n" +
                "from (\n" +
                "      select prodIdx,\n" +
                "             createAt,\n" +
                "             authorName,\n" +
                "             prodName,\n" +
                "             prodReviewNum,\n" +
                "             round(prodRatingAvg, 1) as prodRatingAvg,\n" +
                "             prodImage,\n" +
                "             reviewComment\n" +
                "      from `Order` o\n" +
                "          left join (select orderIdx,\n" +
                "                        opp.prodIdx,\n" +
                "                        prodCateIdx,\n" +
                "                            prodName,\n" +
                "                            prodReviewNum,\n" +
                "                            prodRatingAvg,\n" +
                "                            prodImage,\n" +
                "                            reviewComment,\n" +
                "                            authorName\n" +
                "                 from OrderProduct opp\n" +
                "      left join(\n" +
                "              select k.prodCateIdx,\n" +
                "           k.prodName,\n" +
                "           k.prodIdx,\n" +
                "           prodReviewNum,\n" +
                "           prodRatingAvg,\n" +
                "           prodImage,\n" +
                "           reviewComment,\n" +
                "        authorName\n" +
                "    from Product k\n" +
                "        left join(\n" +
                "        select count(prodReviewIdx)               as prodReviewNum,\n" +
                "               sum(rating) / count(prodReviewIdx) as prodRatingAvg, \n" +
                "               prodIdx\n" +
                "        from ProductReview\n" +
                "        group by prodIdx) as r on r.prodIdx = k.prodIdx\n" +
                "        left join(\n" +
                "        select reviewComment,\n" +
                "               prodIdx\n" +
                "        from ProductReview ) as rr on rr.prodIdx = k.prodIdx\n" +
                "        left join(\n" +
                "        select prodImage,\n" +
                "               prodIdx\n" +
                "        from ProductImage\n" +
                "        where mainImage = 'Y'\n" +
                "    ) as pi on pi.prodIdx = k.prodIdx\n" +
                "    left join (\n" +
                "        select authorName,\n" +
                "               authorIdx\n" +
                "        from Author\n" +
                "        )as a on a.authorIdx= k.authorIdx\n" +
                "    ) as kk on kk.prodIdx = opp.prodIdx\n" +
                "      )as op on op.orderIdx=o.orderIdx\n" +
                "      order by createAt DESC\n" +
                "    LIMIT 18446744073709551615\n" +
                ")as g\n" +
                "group by prodIdx;";

        List<GetBannerRes> getBannerRes;
        List<GetVisitAlikeRes> getVisitAlikeRes;
        List<GetTodayProdRes> getTodayProdRes;
        List<GetDisProdRes> getDisProdRes;
        List<GetRecOrderRes> getRecOrderRes;
        List<GetNowOrderRes> getNowOrderRes;
        List<GetNowReviewRes> getNowReviewRes;
        List<GetNewRes> getNewRes;
        return new GetHomeRes(
                getBannerRes=null,getVisitAlikeRes=null,getTodayProdRes=null,
                getDisProdRes=null, getRecOrderRes=null,
                getNowOrderRes=this.jdbcTemplate.query(getUserQuery1,
                        (rs,rowNum)-> new GetNowOrderRes (
                                rs.getInt("prodIdx"),
                                rs.getTimestamp("createAt"),
                                rs.getString("authorName"),
                                rs.getString("prodName"),
                                rs.getInt("prodReviewNum"),
                                rs.getFloat("prodRatingAvg"),
                                rs.getString("prodImage"),
                                rs.getString("reviewComment")
                        )),
                getNowReviewRes=null,getNewRes=null);
    }
    public GetHomeRes getNowReview(){
        String getUserQuery2 = "select g.* \n" +
                "from (\n" +
                "      select r.createAt,\n" +
                "             r.prodIdx,\n" +
                "             prodName,\n" +
                "             prodReviewNum,\n" +
                "             round(prodRatingAvg, 1) as prodRatingAvg,\n" +
                "             prodImage,\n" +
                "             reviewComment \n" +
                "      from ProductReview r\n" +
                "      left join(\n" +
                "              select k.prodCateIdx,\n" +
                "                     k.prodName,\n" +
                "                     k.prodIdx,\n" +
                "                     prodReviewNum,\n" +
                "                     prodRatingAvg,\n" +
                "                     prodImage\n" +
                "              from Product k\n" +
                "                left join(\n" +
                "                    select count(prodReviewIdx)               as prodReviewNum,\n" +
                "                            sum(rating) / count(prodReviewIdx) as prodRatingAvg,\n" +
                "                            prodIdx\n" +
                "                    from ProductReview \n" +
                "                    group by prodIdx) as r on r.prodIdx = k.prodIdx\n" +
                "                left join(\n" +
                "                    select prodImage,\n" +
                "                           prodIdx\n" +
                "                    from ProductImage\n" +
                "                     where mainImage = 'Y') as pi on pi.prodIdx = k.prodIdx\n" +
                "      )as kk on kk.prodIdx= r.prodIdx\n" +
                "     order by createAt DESC\n" +
                "    LIMIT 18446744073709551615\n" +
                "  )as g\n" +
                "group by prodIdx;";

        List<GetBannerRes> getBannerRes;
        List<GetVisitAlikeRes> getVisitAlikeRes;
        List<GetTodayProdRes> getTodayProdRes;
        List<GetDisProdRes> getDisProdRes;
        List<GetRecOrderRes> getRecOrderRes;
        List<GetNowOrderRes> getNowOrderRes;
        List<GetNowReviewRes> getNowReviewRes;
        List<GetNewRes> getNewRes;
        return new GetHomeRes(
                getBannerRes=null,getVisitAlikeRes=null,getTodayProdRes=null,
                getDisProdRes=null, getRecOrderRes=null,
                getNowOrderRes=null,
                getNowReviewRes=this.jdbcTemplate.query(getUserQuery2,
                        (rs,rowNum)-> new GetNowReviewRes (
                                rs.getTimestamp("createAt"),
                                rs.getInt("prodIdx"),
                                rs.getString("prodName"),
                                rs.getInt("prodReviewNum"),
                                rs.getFloat("prodRatingAvg"),
                                rs.getString("prodImage"),
                                rs.getString("reviewComment"))
                ),getNewRes=null);
    }

    public GetHomeRes getNew(){
        String getUserQuery = "select f.*\n" +
                "from(\n" +
                "select k.prodIdx,\n" +
                "       k.prodName,\n" +
                "       prodImage,\n" +
                "       authorName,\n" +
                "       k.createAt\n" +
                "from Product k\n" +
                "        left join(\n" +
                "        select count(prodReviewIdx)               as prodReviewNum,\n" +
                "               sum(rating) / count(prodReviewIdx) as prodRatingAvg,\n" +
                "               prodIdx\n" +
                "        from ProductReview\n" +
                "        group by prodIdx) as r on r.prodIdx = k.prodIdx\n" +
                "        left join(\n" +
                "        select reviewComment,\n" +
                "               prodIdx,\n" +
                "               createAt\n" +
                "        from ProductReview r\n" +
                "        order by createAt DESC\n" +
                "        ) as rr on rr.prodIdx = k.prodIdx\n" +
                "        left join(\n" +
                "        select prodImage,\n" +
                "               prodIdx\n" +
                "        from ProductImage\n" +
                "        where mainImage = 'Y'\n" +
                "    ) as pi on pi.prodIdx = k.prodIdx\n" +
                "        left join (\n" +
                "            select authorIdx,\n" +
                "                   authorName\n" +
                "            from Author\n" +
                "    )as a on a.authorIdx=k.authorIdx\n" +
                "group by k.prodIdx) as f\n" +
                "ORDER BY createAt DESC;";

        List<GetBannerRes> getBannerRes;
        List<GetVisitAlikeRes> getVisitAlikeRes;
        List<GetTodayProdRes> getTodayProdRes;
        List<GetDisProdRes> getDisProdRes;
        List<GetRecOrderRes> getRecOrderRes;
        List<GetNowOrderRes> getNowOrderRes;
        List<GetNowReviewRes> getNowReviewRes;
        List<GetNewRes> getNewRes;

        return new GetHomeRes(
                getBannerRes=null,getVisitAlikeRes=null,getTodayProdRes=null,
                getDisProdRes=null, getRecOrderRes=null,getNowOrderRes=null,getNowReviewRes=null,
                getNewRes=this.jdbcTemplate.query(getUserQuery,
                (rs,rowNum)-> new GetNewRes (
                        rs.getInt("prodIdx"),
                        rs.getString("prodName"),
                        rs.getString("prodImage"),
                        rs.getString("authorName"),
                        rs.getTimestamp("createAt")))
                );

    }

    public int checkExistAddressUser(int idx){
        String checkExistUserQuery = "select exists(select userIdx from  User where userIdx = ? and status='Y')";
        int checkExistUserParams =idx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams);
    }

    public int checkAddressMax(int idx){
        String checkExistUserQuery = "select count(deliveryIdx) from DeliveryAddress where userIdx = ? and status='Y'";
        int checkExistUserParams =idx;
        return this.jdbcTemplate.queryForObject(checkExistUserQuery,
                int.class,
                checkExistUserParams);
    }

    public int createAddress(PostAddressReq postAddressReq){
        String createLikeQuery = "insert into DeliveryAddress (userIdx,deliveryName,deliveryPhoneNum,address) VALUES (?,?,?,?)";
        Object[] createLikeParams = new Object[]{postAddressReq.getUserIdx(),postAddressReq.getDeliveryName(),postAddressReq.getDeliveryPhoneNum(),postAddressReq.getAddress()};
        this.jdbcTemplate.update(createLikeQuery, createLikeParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

       public List<GetAddressRes> getAddress(){
        String getUsersQuery = "select deliveryIdx,userIdx,deliveryName,deliveryPhoneNum,address from DeliveryAddress";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetAddressRes(
                        rs.getInt("deliveryIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("deliveryName"),
                        rs.getString("deliveryPhoneNum"),
                        rs.getString("address")
                      )
                );
    }


       public int modifyAddressInfo(PatchAddressInfoReq patchAddressInfoReq){
        String modifyOrderQuery = "update DeliveryAddress set deliveryName=?,deliveryPhoneNum=?,address=? where deliveryIdx = ? ";
        Object[] modifyOrderParams = new Object[]{patchAddressInfoReq.getDeliveryName(),patchAddressInfoReq.getDeliveryPhoneNum(),
                patchAddressInfoReq.getAddress(),patchAddressInfoReq.getDeliveryIdx()};

        return this.jdbcTemplate.update(modifyOrderQuery,modifyOrderParams);
    }



}
