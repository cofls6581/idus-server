package com.example.demo.src.user;

import com.example.demo.src.userProduct.model.PatchProdCommentReq;
import com.example.demo.src.userProduct.model.ProdComment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;


    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * 회원 조회 API
     * [GET] /users
     * 회원 번호 및 이메일 검색 조회 API
     * [GET] /users? Email=
     * @return BaseResponse<List<GetUserRes>>
     */
    //Query String
    /*@ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String Email) {
        try{
            if(Email == null){
                List<GetUserRes> getUsersRes = userProvider.getUsers();
                return new BaseResponse<>(getUsersRes);
            }
            // Get Users
            List<GetUserRes> getUsersRes = userProvider.getUsersByEmail(Email);
            return new BaseResponse<>(getUsersRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }*/

    /**
     * 회원 1명 조회 API
     * [GET] /users/:userIdx
     * @return BaseResponse<GetUserRes>
     */
    // Path-variable
   /* @ResponseBody
    @GetMapping("/{userIdx}") // (GET) 127.0.0.1:9000/app/users/:userIdx
    public BaseResponse<GetUserRes> getUser(@PathVariable("userIdx") int userIdx) {
        // Get Users
        try{
            GetUserRes getUserRes = userProvider.getUser(userIdx);
            return new BaseResponse<>(getUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }*/

    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        if(postUserReq.getEmail() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        if(postUserReq.getUserName() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_USERNAME);
        }
        if(postUserReq.getPhoneNum() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_PHONENUM);
        }
        if(postUserReq.getPassword() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
        }
        //이메일 정규표현
        if(!isRegexEmail(postUserReq.getEmail())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 로그인 API
     * [POST] /users/login
     * @return BaseResponse<PostLoginRes>
     */
   @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try{
            if(postLoginReq.getEmail() == null){
                return new BaseResponse<>(POST_LOGINS_EMPTY_EMAIL);
            }
            if(postLoginReq.getPassword() == null){
                return new BaseResponse<>(POST_LOGINS_EMPTY_PASSWORD);
            }
            if(!isRegexEmail(postLoginReq.getEmail())){
                return new BaseResponse<>(POST_LOGINS_INVALID_EMAIL);
            }
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저정보변경 API
     * [PATCH] /users/:userIdx
     * @return BaseResponse<String>
     */
  /* @ResponseBody
    @PatchMapping("/{userIdx}")
    public BaseResponse<String> modifyUserName(@PathVariable("userIdx") int userIdx, @RequestBody User user){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경
            PatchUserReq patchUserReq = new PatchUserReq(userIdx,user.getUserName());
            userService.modifyUserName(patchUserReq);

            String result = "";
        return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }*/

    /**
     * 찜 생성 API
     * [POST] /users/likes
     * @return BaseResponse<PostLikeRes>
     */
    // Body
    @ResponseBody
    @PostMapping("/likes")
    public BaseResponse<PostLikeRes> createLike( @RequestBody PostLikeReq postLikeReq) {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (postLikeReq.getUserIdx() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
            catch (BaseException exception) {
                return new BaseResponse<>((exception.getStatus()));
        }
        if(postLikeReq.getUserIdx() == 0){
            return new BaseResponse<>(POST_LIKES_EMPTY_USERIDX);
        }
        if(postLikeReq.getProdIdx() == 0){
            return new BaseResponse<>(POST_LIKES_EMPTY_PRODIDX);
        }
        try{
            PostLikeRes postLikeRes = userService.createLike(postLikeReq);
            return new BaseResponse<>(postLikeRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 찜 해제 API
     * [PATCH] /users/:userIdx/likes/:prodLikeIdx/status
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userIdx}/likes/{prodLikeIdx}/status")
    public BaseResponse<String> deleteLike(@PathVariable("userIdx") int idx, @PathVariable("prodLikeIdx") int plidx,@RequestBody ProductLike productLike){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(idx!= userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PatchLikeReq patchLikeReq = new PatchLikeReq(plidx);
            if(patchLikeReq.getProdLikeIdx() == 0){
                return new BaseResponse<>(DELETE_LIKES_EMPTY_IDX);
            }
            userService.deleteLike(patchLikeReq);
            String result = "";
        return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 방문기록 생성 API
     * [POST] /users/:userIdx
     * @return BaseResponse<PostVisitRes>
     */
    // Body
    @ResponseBody
    @PostMapping("/visits")
    public BaseResponse<PostVisitRes> createVisit( @RequestBody PostVisitReq postVisitReq) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (postVisitReq.getUserIdx() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

        if(postVisitReq.getUserIdx() == 0){
            return new BaseResponse<>(POST_VISITS_EMPTY_USERIDX);
        }
        if(postVisitReq.getProdIdx() == 0){
            return new BaseResponse<>(POST_VISITS_EMPTY_PRODIDX);
        }
        try{
            PostVisitRes postVisitRes = userService.createVisit(postVisitReq);
            return new BaseResponse<>(postVisitRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 방문 기록 수정 API
     * [PATCH] /users/:userIdx/visits
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userIdx}/visits")
    public BaseResponse<String> patchVisit(@PathVariable("userIdx") int idx,@RequestBody Visit visit){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(idx!= userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PatchVisitReq patchVisitReq = new PatchVisitReq(idx,visit.getProdIdx());
            if(patchVisitReq.getProdIdx() == 0){
                return new BaseResponse<>(PATCH_VISITS_EMPTY_PRODIDX);
            }
            userService.patchVisit(patchVisitReq);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 홈화면 탭별 조회 API
     * * [GET] /users/:userIdx/homes/?tab=&?now=
     * @return BaseResponse<List<GetStoreRes>>
     */
    //Query String
    @ResponseBody
    @GetMapping("/{userIdx}/homes")
    public BaseResponse<GetHomeRes> getHome(@PathVariable ("userIdx") int userIdx, @RequestParam(required = true) String tab,@RequestParam(required = false) String now) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx!= userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            if(tab.equals("today")) {
                GetHomeRes getHomeRes = userProvider.getToday(userIdx);
                return new BaseResponse<>(getHomeRes);
            }
            else if(tab.equals("now")&&now==null){
                GetHomeRes getHomeRes = userProvider.getNowOrder();
                return new BaseResponse<>(getHomeRes);
            }
            else if(tab.equals("now")&&now.equals("review")) {
                GetHomeRes getHomeRes = userProvider.getNowReview();
                return new BaseResponse<>(getHomeRes);
            }
            else if(tab.equals("new")) {
                GetHomeRes getHomeRes = userProvider.getNew();
                return new BaseResponse<>(getHomeRes);
            }
            else{
                return new BaseResponse<>(INVALID_TAB_INPUT);
            }
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 유저 배달지 생성 API
     * [POST] /users/addresses
     * @return BaseResponse<PostAddressRes>
     */
    // Body
    @ResponseBody
    @PostMapping("/addresses")
    public BaseResponse<PostAddressRes> createAddress(@RequestBody PostAddressReq postAddressReq) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (postAddressReq.getUserIdx() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
        if(postAddressReq.getUserIdx() == 0){
            return new BaseResponse<>(POST_ADDRESSES_EMPTY_USERIDX);
        }
        if(postAddressReq.getDeliveryName()== null){
            return new BaseResponse<>(POST_ADDRESSES_EMPTY_NAME);
        }
        if(postAddressReq.getDeliveryPhoneNum()== null){
            return new BaseResponse<>(POST_ADDRESSES_EMPTY_PHONENUM);
        }
        if(postAddressReq.getAddress()== null){
            return new BaseResponse<>(POST_ADDRESSES_EMPTY_ADDRESS);
        }
        try{
            PostAddressRes postAddressRes = userService.createAddress(postAddressReq);
            return new BaseResponse<>(postAddressRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저 배송지 조회 API
     * [GET] /users/:userIdx/addresses
     * @return BaseResponse<List<GetAddressRes>>
     */
    @ResponseBody
    @GetMapping("{userIdx}/addresses")
    public BaseResponse<List<GetAddressRes>> getAddress(@PathVariable ("userIdx") int userIdx) {
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
            List<GetAddressRes> getAddressRes = userProvider.getAddress(userIdx);
            return new BaseResponse<>(getAddressRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저배송지 삭제 API
     * [PATCH] /users/:userIdx/addresses/status
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userIdx}/addresses/status")
    public BaseResponse<String> deleteAddress(@PathVariable("userIdx") int userIdx, @RequestBody DeliveryAddress deliveryAddress){
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
            PatchAddressReq patchAddressReq = new PatchAddressReq(deliveryAddress.getDeliveryIdx());
            if(patchAddressReq.getDeliveryIdx()== 0){
                return new BaseResponse<>(DELETE_DELIVERY_EMPTY_DELIVERYIDX);
            }
            userService.deleteAddress(patchAddressReq);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저 배송지 변경 API
     * [PATCH] /users/:userIdx/addresses/:deliveryIdx/status
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userIdx}/addresses/{deliveryIdx}/status")
    public BaseResponse<String> modifyAddressInfo(@PathVariable("userIdx") int userIdx,@PathVariable("deliveryIdx") int deliveryIdx, @RequestBody DeliveryAddress deliveryAddress){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PatchAddressInfoReq patchAddressInfoReq = new PatchAddressInfoReq(deliveryIdx,deliveryAddress.getDeliveryName(),
                    deliveryAddress.getDeliveryPhoneNum(),deliveryAddress.getAddress());

            if(patchAddressInfoReq.getDeliveryPhoneNum()== null){
                return new BaseResponse<>(PATCH_ADDRESSES_EMPTY_PHONENUM);
            }
            if(patchAddressInfoReq.getDeliveryName()== null){
                return new BaseResponse<>(PATCH_ADDRESSES_EMPTY_NAME);
            }
            if(patchAddressInfoReq.getAddress()== null){
                return new BaseResponse<>(PATCH_ADDRESSES_EMPTY_ADDRESS);
            }
            userService.modifyAddressInfo(patchAddressInfoReq);

            String result = "";
        return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 카카오 API
     * [GET] /users/kakaoLogin
     */
   /* @GetMapping("/kakaoLogin")
    public @ResponseBody String kakaocallback(String code) throws JsonProcessingException {

        RestTemplate rt=new RestTemplate();
        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String,String> params= new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id","26cae851c1100181b6c1ee8b1d32632c");
        params.add("redirect_uri","https://product.brynserver.site:9000/users/kakaoLogin");
        params.add("code",code);

        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest=
                new HttpEntity<>(params,headers);

        ResponseEntity<String> response= rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper obMapper = new ObjectMapper();
        OAuthToken oauthToken=null;
        oauthToken= obMapper.readValue(response.getBody(),OAuthToken.class);


        RestTemplate rt2=new RestTemplate();
        HttpHeaders headers2=new HttpHeaders();
        headers2.add("Authorization","Bearer "+oauthToken.getAccess_token());
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest2=
                new HttpEntity<>(headers2);

        ResponseEntity<String> response2= rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );
        return response2.getBody();
    }*/


    @ResponseBody
    @PostMapping("/kakaoLogin")
    public BaseResponse<PostKakaoRes> postLoginUser(@RequestHeader("X-ACCESS-TOKEN") String token){

        if (token == null || token.length() == 0) {
            return new BaseResponse(EMPTY_TOKEN);
        }

        try{
            PostKakaoRes postKakaoRes;
            postKakaoRes= userService.kakaoLogin(token);
            return new BaseResponse<>(postKakaoRes);

        }catch (Exception e){
            e.printStackTrace();
            return new BaseResponse<>(FAILED_TO_KAKAOLOGIN);
        }

    }

}
