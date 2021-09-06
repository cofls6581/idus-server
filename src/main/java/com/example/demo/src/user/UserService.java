package com.example.demo.src.user;



import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.model.*;
import com.example.demo.src.user.kakao;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;
    private final kakao kakao;

    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService, kakao kakao) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;
        this.kakao=kakao;
    }

    //POST
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        //중복
        if(userProvider.checkEmail(postUserReq.getEmail()) ==1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }
        if(userProvider.checkPhoneNum(postUserReq.getPhoneNum()) ==1){
            throw new BaseException(POST_USERS_EXISTS_PHONENUM);
        }
        String pwd;
        try{
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword());
            postUserReq.setPassword(pwd);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            int userIdx = userDao.createUser(postUserReq);
            return new PostUserRes(userIdx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

  /*  public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
        try{
            int result = userDao.modifyUserName(patchUserReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }*/

    public PostLikeRes createLike(PostLikeReq postLikeReq) throws BaseException {
        if(userProvider.checkExistLikeUser(postLikeReq.getUserIdx())==0){
            throw new BaseException(POST_LIKES_NOTEXISTS_USER);
        }
        if(userProvider.checkLike(postLikeReq.getProdIdx(),postLikeReq.getUserIdx()) ==1){
            throw new BaseException(POST_LIKES_EXISTS_STATUS);
        }
        try{
            int prodLikeIdx = userDao.createLike(postLikeReq);
            return new PostLikeRes(prodLikeIdx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteLike(PatchLikeReq patchLikeReq) throws BaseException {
        if(userProvider.checkDeletedLike(patchLikeReq.getProdLikeIdx()) ==1){
            throw new BaseException(LIKES_DUPLICATE_DELETED);
        }
        try{
            int result = userDao.deleteLike(patchLikeReq);
            if(result == 0){
                throw new BaseException(DELETE_FAIL_LIKE);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostVisitRes createVisit(PostVisitReq postVisitReq) throws BaseException {
        if(userProvider.checkExistVisitUser(postVisitReq.getUserIdx())==1){
            throw new BaseException(POST_VISITS_NOTEXISTS_USER);
        }
        if(userProvider.checkVisit(postVisitReq.getUserIdx()) ==1){
            throw new BaseException(POST_VISITS_EXISTS_STATUS);
        }
        try{
            int prodVisitIdx = userDao.createVisit(postVisitReq);
            return new PostVisitRes(prodVisitIdx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void patchVisit(PatchVisitReq patchVisitReq) throws BaseException {
        if(userProvider.checkExistVisitProd(patchVisitReq.getProdIdx()) ==0){
            throw new BaseException(VISITS_NOTEXISTS_PROD);
        }
        try{
            int result = userDao.patchVisit(patchVisitReq);
            if(result == 0){
                throw new BaseException(DELETE_FAIL_LIKE);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostAddressRes createAddress(PostAddressReq postAddressReq) throws BaseException {
        if(userProvider.checkExistAddressUser(postAddressReq.getUserIdx())==0){
            throw new BaseException(POST_ADDRESSES_NOTEXISTS_USER);
        }
        if(userProvider.checkAddressMax(postAddressReq.getUserIdx())==3){
            throw new BaseException(POST_ADDRESSES_MAX);
        }
        try{
            int deliveryIdx = userDao.createAddress(postAddressReq);
            return new PostAddressRes(deliveryIdx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteAddress(PatchAddressReq patchAddressReq) throws BaseException {
        if(userProvider.checkDelivery(patchAddressReq.getDeliveryIdx()) ==0){
            throw new BaseException(DELETE_ADDRESS_NOTEXISTS_IDX);
        }
        try{
            int result = userDao.deleteAddress(patchAddressReq);
            if(result == 0){
                throw new BaseException(DELETE_FAIL_ADDRESS);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

   public void modifyAddressInfo(PatchAddressInfoReq patchAddressInfoReq) throws BaseException {

        if(userProvider.checkDelivery(patchAddressInfoReq.getDeliveryIdx()) ==0){
            throw new BaseException(PATCH_ADDRESSINFO_NOTEXISTS_IDX);
        }
        try{
            int result = userDao.modifyAddressInfo(patchAddressInfoReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_ADDRESSINFO);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostKakaoRes kakaoLogin (String token) throws BaseException{
        long userId = kakao.KakaoUserId(token);
        String jwt = jwtService.createJwt(userId);
        return new PostKakaoRes(jwt,userId);
    }

}
