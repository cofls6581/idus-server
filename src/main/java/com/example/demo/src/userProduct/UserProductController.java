package com.example.demo.src.userProduct;
import com.example.demo.src.user.model.PostLikeReq;
import com.example.demo.src.user.model.PostLikeRes;
import com.example.demo.src.userProduct.UserProductProvider;
import com.example.demo.src.userProduct.UserProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.userProduct.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/users/{userIdx}/products")
public class UserProductController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProductProvider userProductProvider;
    @Autowired
    private final UserProductService userProductService;
    @Autowired
    private final JwtService jwtService;

    public UserProductController(UserProductProvider userProductProvider, UserProductService userProductService, JwtService jwtService){
        this.userProductProvider = userProductProvider;
        this.userProductService = userProductService;
        this.jwtService = jwtService;
    }

    /**
     * 작품 상세페이지 조회 API
     * [GET] /users/:userIdx/products/:prodIdx
     * @return BaseResponse<GetProductRes>
     */
    @ResponseBody
    @GetMapping("/{prodIdx}")
    public BaseResponse<GetProductRes> getProduct( @PathVariable("userIdx") int userIdx, @PathVariable("prodIdx") int prodIdx) {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

        try{
            GetProductRes getProductRes = userProductProvider.getProduct(userIdx,prodIdx);
            return new BaseResponse<>(getProductRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 작품 댓글 생성 API
     * [POST] /users/:userIdx/products/comments
     * @return BaseResponse<PostLikeRes>
     */
    // Body
    @ResponseBody
    @PostMapping("/comments")
    public BaseResponse<PostProdCommentRes> createProdComment(@PathVariable("userIdx") int userIdx, @RequestBody PostProdCommentReq postProdCommentReq) {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
        if(postProdCommentReq.getUserIdx() == 0){
            return new BaseResponse<>(POST_PRODCOMMENTS_EMPTY_USERIDX);
        }
        if(postProdCommentReq.getProdIdx() == 0){
            return new BaseResponse<>(POST_PRODCOMMENTS_EMPTY_PRODIDX);
        }
        if(postProdCommentReq.getProductComment() == null){
            return new BaseResponse<>(POST_PRODCOMMENTS_EMPTY_COMMENT);
        }
        try{
            PostProdCommentRes postProdCommentRes = userProductService.createProdComment(postProdCommentReq);
            return new BaseResponse<>(postProdCommentRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 작품 댓글 삭제 API
     * [PATCH] /users/:userIdx/products/comments/status
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/comments/status")
    public BaseResponse<String> deleteComment(@PathVariable("userIdx") int userIdx,  @RequestBody ProdComment prodComment){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
        try {
            PatchProdCommentReq patchProdCommentReq = new PatchProdCommentReq(prodComment.getProdCommentIdx(),userIdx);
            if(patchProdCommentReq.getProdCommentIdx() == 0){
                return new BaseResponse<>(DELETE_PRODCOMMENTS_EMPTY_IDX);
            }
            userProductService.deleteComment(patchProdCommentReq);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 작품 옵션 조회 API
     * [GET] /users/:userIdx/products/:prodIdx/sides
     * @return BaseResponse<GetProductRes>
     */
    @ResponseBody
    @GetMapping("/{prodIdx}/sides")
    public BaseResponse<List<GetProdOptionRes>> getProdOption( @PathVariable("userIdx") int userIdx, @PathVariable("prodIdx") int prodIdx) {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

        try{
            List<GetProdOptionRes> getProdOptionRes = userProductProvider.getProdOption(prodIdx);
            return new BaseResponse<>(getProdOptionRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
