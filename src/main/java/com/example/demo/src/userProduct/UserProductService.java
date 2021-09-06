package com.example.demo.src.userProduct;


import com.example.demo.config.BaseException;
import com.example.demo.src.userProduct.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserProductService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserProductDao userProductDao;
    private final UserProductProvider userProductProvider;
    private final JwtService jwtService;


    @Autowired
    public UserProductService(UserProductDao userProductDao, UserProductDao userProductDao1, UserProductProvider userProductProvider, JwtService jwtService) {
        this.userProductDao = userProductDao;
        this.userProductProvider = userProductProvider;
        this.jwtService = jwtService;

    }
    //작품댓글생성
    public PostProdCommentRes createProdComment(PostProdCommentReq postProdCommentReq) throws BaseException {
        if(userProductProvider.checkExistUser(postProdCommentReq.getUserIdx())==0){
            throw new BaseException(POST_COMMENTS_NOTEXISTS_USER);
        }
        if(userProductProvider.checkExistProduct(postProdCommentReq.getProdIdx())==0){
            throw new BaseException(POST_COMMENTS_NOTEXISTS_PRODUCT);
        }
        try{
            int prodCommentIdx = userProductDao.createProdComment(postProdCommentReq);
            return new PostProdCommentRes(prodCommentIdx);
        } catch (Exception exception) {
            exception.printStackTrace();
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //작품댓글삭제
    //카트 삭제
    public void deleteComment(PatchProdCommentReq patchProdCommentReq) throws BaseException {
        if(userProductProvider.checkExistComment(patchProdCommentReq.getProdCommentIdx())==0){
            throw new BaseException(PATCH_PRODCOMMENTS_NOTEXISTS_PRODCOMMENT);
        }
        if(userProductProvider.checkExistUser(patchProdCommentReq.getUserIdx())==0){
            throw new BaseException(PATCH_PRODCOMMENTS_NOTEXISTS_USER);
        }
        if(userProductProvider.checkCanDelete(patchProdCommentReq.getUserIdx(),patchProdCommentReq.getProdCommentIdx())==0){
            throw new BaseException(PATCH_PRODCOMMENTS_NOTEXITST_AUTHORITY);
        }
        try{
            int result = userProductDao.deleteComment(patchProdCommentReq);
            if(result == 0){
                throw new BaseException(DELETE_FAIL_PRODCOMMENTS);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
