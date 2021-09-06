package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.model.*;
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
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

   /* public List<GetUserRes> getUsers() throws BaseException{
        try{
            List<GetUserRes> getUserRes = userDao.getUsers();
            return getUserRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }*/

   /* public List<GetUserRes> getUsersByEmail(String email) throws BaseException{
        try{
            List<GetUserRes> getUsersRes = userDao.getUsersByEmail(email);
            return getUsersRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
                    }*/


   /* public GetUserRes getUser(int userIdx) throws BaseException {
        try {
            GetUserRes getUserRes = userDao.getUser(userIdx);
            return getUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }*/

   public int checkEmail(String email) throws BaseException{
        try{
            return userDao.checkEmail(email);
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkPhoneNum(String num) throws BaseException{
        try{
            return userDao.checkPhoneNum(num);
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkAliveUser(String mail) throws BaseException {
        try {
            return userDao.checkAliveUser(mail);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkExistUser(String mail) throws BaseException {
        try {
            return userDao.checkExistUser(mail);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkExistUser(int idx) throws BaseException {
        try {
            return userDao.checkExistUser(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkExistLikeUser(int idx) throws BaseException {
        try {
            return userDao.checkExistLikeUser(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkExistVisitUser(int idx) throws BaseException {
        try {
            return userDao.checkExistVisitUser(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
   public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException{
       if(checkAliveUser(postLoginReq.getEmail())==1){
           throw new BaseException(POST_LOGINS_DELETED_USERS);
        }
       if(checkExistUser(postLoginReq.getEmail())==0){
           throw new BaseException(POST_LOGINS_NOTEXIST_USERS);
       }
        User user = userDao.getPwd(postLoginReq);
        String password;
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }
        if(postLoginReq.getPassword().equals(password)){
            int userIdx = userDao.getPwd(postLoginReq).getUserIdx();
            String jwt = jwtService.createJwt(userIdx);
            return new PostLoginRes(userIdx,jwt);
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }

    public int checkLike(int pIdx ,int uIdx) throws BaseException{
        try{
            return userDao.checkLike(pIdx,uIdx);
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkDeletedLike(int plIdx) throws BaseException{
        try{
            return userDao.checkDeletedLike(plIdx);
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkVisit(int uIdx) throws BaseException{
        try{
            return userDao.checkVisit(uIdx);
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkExistVisitProd(int idx) throws BaseException {
        try {
            return userDao.checkExistVisitProd(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public GetHomeRes getToday(int uidx) throws BaseException{
        try{
            GetHomeRes getHomeRes = userDao.getToday(uidx);
            return getHomeRes;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetHomeRes getNowOrder() throws BaseException{
        try{
            GetHomeRes getHomeRes = userDao.getNowOrder();
            return getHomeRes;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public GetHomeRes getNowReview() throws BaseException{
        try{
            GetHomeRes getHomeRes = userDao.getNowReview();
            return getHomeRes;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetHomeRes getNew() throws BaseException{
        try{
            GetHomeRes getHomeRes = userDao.getNew();
            return getHomeRes;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkExistAddressUser(int idx) throws BaseException {
        try {
            return userDao.checkExistAddressUser(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkAddressMax(int idx) throws BaseException {
        try {
            return userDao.checkAddressMax(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
     public List<GetAddressRes> getAddress(int idx) throws BaseException{
         if(checkExistUser(idx)==0){
             throw new BaseException(GET_ADDRESSES_NOTEXISTS_USER);
         }
       try{
            List<GetAddressRes> getAddressRes = userDao.getAddress();
            return getAddressRes;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkDelivery(int dIdx) throws BaseException{
        try{
            return userDao.checkDelivery(dIdx);
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
