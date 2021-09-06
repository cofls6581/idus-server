package com.example.demo.src.userProduct;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.userProduct.UserProductDao;
import com.example.demo.src.userProduct.model.*;
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
public class UserProductProvider {
    private final UserProductDao userProductDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProductProvider(UserProductDao userProductDao, JwtService jwtService) {
        this.userProductDao = userProductDao;
        this.jwtService = jwtService;
    }

    public GetProductRes getProduct(int userIdx, int prodIdx) throws BaseException {
        if(checkExistUser(userIdx)==0){
            throw new BaseException(GET_PRODUCTS_NOTEXISTS_USER);
        }
        if(checkExistProduct(prodIdx)==0){
            throw new BaseException(GET_PRODUCTS_NOTEXISTS_PRODUCT);
        }
        try {
            GetProductRes getProductRes = userProductDao.getProduct(userIdx, prodIdx);
            return getProductRes;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkExistUser(int idx) throws BaseException {
        try {
            return userProductDao.checkExistUser(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkExistProduct(int idx) throws BaseException {
        try {
            return userProductDao.checkExistProduct(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkExistComment(int idx) throws BaseException {
        try {
            return userProductDao.checkExistComment(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkCanDelete(int userIdx, int prodIdx) throws BaseException {
        try {
            return userProductDao.checkCanDelete(userIdx,prodIdx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetProdOptionRes> getProdOption(int prodIdx) throws BaseException {
        if(checkExistOpProd(prodIdx)==0){
            throw new BaseException(GET_PRODOP_NOTEXIST_PROD);
        }
        try {
            List<GetProdOptionRes> getProdOptionRes = userProductDao.getProdOption(prodIdx);
            return getProdOptionRes;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkExistOpProd(int idx) throws BaseException {
        try {
            return userProductDao.checkExistOpProd(idx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
