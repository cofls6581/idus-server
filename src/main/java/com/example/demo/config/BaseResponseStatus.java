package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EMPTY_USERNAME(false,2018,"이름을 입력해주세요."),
    POST_USERS_EMPTY_PHONENUM(false,2019,"전화번호를 입력해주세요."),
    POST_USERS_EMPTY_PASSWORD(false,2020,"비밀번호를 입력해주세요."),
    // [POST] /users/login
    POST_LOGINS_EMPTY_EMAIL(false, 2021, "이메일을 입력해주세요."),
    POST_LOGINS_EMPTY_PASSWORD(false,2022,"비밀번호를 입력해주세요."),
    POST_LOGINS_INVALID_EMAIL(false, 2023, "이메일 형식을 확인해주세요."),
    // [POST] /users/likes
    POST_LIKES_EMPTY_USERIDX(false, 2024, "유저인덱스를 입력해주세요."),
    POST_LIKES_EMPTY_PRODIDX(false, 2025, "작품인덱스를 입력해주세요."),
    // [POST] /users/:userIdx/products/comments
    POST_PRODCOMMENTS_EMPTY_USERIDX(false, 2026, "유저인덱스를 입력해주세요."),
    POST_PRODCOMMENTS_EMPTY_PRODIDX(false, 2027, "작품인덱스를 입력해주세요."),
    POST_PRODCOMMENTS_EMPTY_COMMENT(false, 2028, "댓글 내용을 입력해주세요."),
    // [POST] /users/:userIdx/visits
    POST_VISITS_EMPTY_USERIDX(false, 2029, "유저인덱스를 입력해주세요."),
    POST_VISITS_EMPTY_PRODIDX(false, 2030, "작품인덱스를 입력해주세요."),
    // [GET] /users/:userIdx/homes/?tab=
    INVALID_TAB_INPUT(false, 2031, "탭 이름을 정확하게 입력해주세요."),
    // [POST] /users/:userIdx/addresses
    POST_ADDRESSES_EMPTY_USERIDX(false, 2032, "유저인덱스를 입력해주세요."),
    POST_ADDRESSES_EMPTY_NAME(false, 2033, "받는 분을 입력해주세요."),
    POST_ADDRESSES_EMPTY_PHONENUM(false, 2034, "전화번호를 입력해주세요."),
    POST_ADDRESSES_EMPTY_ADDRESS(false, 2035, "주소를 입력해주세요."),
    // [POST] /users/:userIdx/orders
    POST_ORDERS_EMPTY_USERIDX(false, 2036, "유저인덱스를 입력해주세요."),
    POST_ORDERS_EMPTY_PRODIDX(false, 2037, "작품인덱스를 입력해주세요."),
    POST_ORDERS_EMPTY_AUTHORIDX(false, 2038, "작가인덱스를 입력해주세요."),
    POST_ORDERS_EMPTY_PRODCOUNT(false, 2039, "작품수량을 입력해주세요."),
    POST_ORDERS_EMPTY_PRODSIDEIDX(false, 2040, "옵션인덱스를 입력해주세요."),
    POST_ORDERS_EMPTY_PRODSIDECOUNT(false, 2041, "옵션수량을 입력해주세요."),
    POST_ORDERS_EMPTY_PRODPRICE(false, 2057, "작품별 가격을 입력해주세요."),
    POST_ORDERS_EMPTY_PRODN(false, 2060, "작품별 총 개수를 입력해주세요."),
    POST_ORDERS_EMPTY_PRODSIDEN(false, 2061, "작품 옵션 총 갯수 입력해주세요."),
    //[POST] /users/:userIdx/orders/reviews
    POST_REIVEWS_EMPTY_ORDERPRODIDX(false, 2042, "주문작품인덱스를 입력해주세요."),
    POST_REIVEWS_EMPTY_USERIDX(false, 2043, "유저인덱스를 입력해주세요."),
    POST_REIVEWS_EMPTY_PRODIDX(false, 2044, "작품인덱스를 입력해주세요."),
    POST_REIVEWS_EMPTY_RATING(false, 2045, "평점을 입력해주세요."),
    POST_REIVEWS_EMPTY_REVIEWCOMMENT(false, 2046, "리뷰내용을 입력해주세요."),
    // [PATCH] /users/:userIdx/addresses/status
    DELETE_DELIVERY_EMPTY_DELIVERYIDX(false, 2047, "배송지 인덱스를 입력해주세요."),
    // [PATCH] /users/:userIdx/likes/:prodLikeIdx/status
    DELETE_LIKES_EMPTY_IDX(false, 2048, "찜 인덱스를 입력해주세요."),
    // [PATCH] /users/:userIdx/orders/reviews/status
    DELETE_REIVEWS_EMPTY_IDX(false, 2049, "리뷰 인덱스를 입력해주세요."),
    //[PATCH] /users/:userIdx/products/comments/status
    DELETE_PRODCOMMENTS_EMPTY_IDX(false, 2050, "작품댓글 인덱스를 입력해주세요."),
    //[PATCH] /users/:userIdx/visits
    PATCH_VISITS_EMPTY_PRODIDX(false, 2051, "작품 인덱스를 입력해주세요."),
    //[PATCH] /users/:userIdx/addresses/:deliveryIdx/status
    PATCH_ADDRESSES_EMPTY_PHONENUM(false, 2052, "받는 분 전화번호를 입력해주세요."),
    PATCH_ADDRESSES_EMPTY_NAME(false, 2053, "받는 분을 입력해주세요."),
    PATCH_ADDRESSES_EMPTY_ADDRESS(false, 2054, "주소를 입력해주세요."),
    //[PATCH] /users/:userIdx/orders/reviews
    PATCH_REIVEWS_EMPTY_REVIEWCOMMENT(false, 2055, "리뷰내용을 입력해주세요."),
    PATCH_REIVEWS_EMPTY_RATING(false, 2056, "평점을 입력해주세요."),
    // [POST] /users/:userIdx/orders/requests
    POST_REQUESTS_EMPTY_ORDERPRODIDX(false, 2061, "주문작품인덱스를 입력해주세요."),
    POST_REQUESTS_EMPTY_REQUEST(false, 2062, "요청사항을 입력해주세요."),
    POST_REQUESTS_EMPTY_PRODIDX(false, 2063, "작품 인덱스를 입력해주세요."),
    POST_REQUESTS_EMPTY_USERIDX(false, 2064, "유저 인덱스를 입력해주세요."),
    // [PATCH] /users/:userIdx/orders/requests
    PATCH_REQUESTS_EMPTY_REQUESTS(false, 2065, "요청사항을 입력해주세요."),
    PATCH_REQUESTS_EMPTY_REQUESTIDX(false, 2066, "요청사항 인덱스를 입력해주세요."),
    // [PATCH] /users/:userIdx/orders/requests/status
    DELETE_REQUESTS_EMPTY_REQUESTIDX(false, 2067, "요청사항 인덱스를 입력해주세요."),
    // [PATCH] /users/:userIdx/orders/payments
    PATCH_ORDERS_EMPTY_ORDERIDX(false, 2068, "주문 인덱스를 입력해주세요."),
    PATCH_ORDERS_EMPTY_PRICE(false, 2069, "총 가격을 입력해주세요."),
    PATCH_ORDERS_EMPTY_DELIVERYIDX(false, 2070, "배송지 인덱스를 입력해주세요."),
    PATCH_ORDERS_EMPTY_PAYMENTTYPE(false, 2071, "지불방식을 입력해주세요."),
    // [POST] /users/:userIdx/orders/baskets
    POST_BASKETS_EMPTY_PRODIDX(false, 2072, "작품인덱스를 입력해주세요."),
    POST_BASKETS_EMPTY_USERIDX(false, 2073, "유저인덱스를 입력해주세요."),
    POST_BASKETS_EMPTY_AUTHORIDX(false, 2074, "작가인덱스를 입력해주세요."),
    POST_BASKETS_EMPTY_PRODCOUNT(false, 2075, "작품 수량을 입력해주세요."),
    POST_BASKETS_EMPTY_PRODPRICE(false, 2076, "작품 가격을 입력해주세요."),
    POST_BASKETS_EMPTY_PRODSIDEIDX(false, 2077, "작품옵션인덱스를 입력해주세요."),
    // [POST] /users/:userIdx/orders/baskets/payments
    POST_BASKETORDERS_EMPTY_BASKETIDX(false, 2078, "장바구니 인덱스를 입력해주세요."),
    POST_BASKETORDERS_EMPTY_ORDERPRODIDX(false, 2079, "주문작품 인덱스를 입력해주세요."),
    // [PATCH] /users/:userIdx/orders/baskets/:basketIdx
    PATCH_BASKETS_EMPTY_ORDERPRODIDX(false, 2081, "주문작품 인덱스를 입력해주세요."),
    PATCH_BASKETS_EMPTY_PRODN(false, 2082, "옵션 수량을 입력해주세요."),
    // [PATCH] /users/:userIdx/orders/baskets/status
    DELETE_BASKET_EMPTY_IDX(false, 2083, "장바구니 인덱스를 입력해주세요."),
    EMPTY_TOKEN(false, 2084, "받은 토큰이 없습니다."),


    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    POST_USERS_EXISTS_EMAIL(false,3010,"중복된 이메일입니다."),
    POST_USERS_EXISTS_PHONENUM(false,3011,"중복된 전화번호입니다."),
    // [POST] /users/login
    FAILED_TO_LOGIN(false,3014,"비밀번호가 틀렸습니다."),
    POST_LOGINS_DELETED_USERS(false,3015,"탈퇴한 회원입니다."),
    POST_LOGINS_NOTEXIST_USERS(false,3016,"없는 회원입니다."),
    //  DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    // [POST] /users/likes
    POST_LIKES_EXISTS_STATUS(false,3017,"이미 찜이 눌러져있습니다."),
    // [PATCH] /users/:userIdx/likes/:prodLikeIdx/status
    DELETE_FAIL_LIKE(false,3018,"찜 해제에 실패했습니다."),
    LIKES_DUPLICATE_DELETED(false,3019,"이미 찜이 해제돼있습니다."),
    POST_LIKES_NOTEXISTS_USER(false,3027,"존재하지 않는 유저입니다."),
    // [POST] /users/:userIdx/products/comments
    POST_COMMENTS_NOTEXISTS_USER(false,3020,"존재하지 않는 유저입니다."),
    POST_COMMENTS_NOTEXISTS_PRODUCT(false,3021,"존재하지 않는 작품입니다."),
    // [PATCH] /users/:userIdx/products/comments/status
    PATCH_PRODCOMMENTS_NOTEXISTS_PRODCOMMENT(false,3022,"존재하지 않는 댓글입니다."),
    PATCH_PRODCOMMENTS_NOTEXISTS_USER(false,3023,"존재하지 않는 유저입니다."),
    PATCH_PRODCOMMENTS_NOTEXITST_AUTHORITY(false,3024,"댓글은 본인만 삭제할 수 있습니다."),
    DELETE_FAIL_PRODCOMMENTS(false,3025,"댓글삭제에 실패했습니다."),
    // [POST] /users/visits
    POST_VISITS_EXISTS_STATUS(false,3026,"이미 생성돼있습니다."),
    POST_VISITS_NOTEXISTS_USER(false,3028,"존재하지 않는 유저입니다."),
    // [PATCH] /users/:userIdx/visits
    VISITS_NOTEXISTS_PROD(false,3029,"존재하지 않는 작품입니다."),
    // [GET] /users/:userIdx/products/:prodIdx/sides
    GET_PRODOP_NOTEXIST_PROD(false,3030,"존재하지 않는 작품입니다."),
    // [POST] /users/addresses
    POST_ADDRESSES_NOTEXISTS_USER(false,3031,"존재하지 않는 유저입니다."),
    POST_ADDRESSES_MAX(false,3032,"3개까지만 저장가능합니다. 필요시 기존 주소를 수정해주세요."),
    // [GET] /users/:userIdx/addresses
    GET_ADDRESSES_NOTEXISTS_USER(false,3033,"존재하지 않는 유저입니다."),
    // [GET] /users/:userIdx/products/:prodIdx
    GET_PRODUCTS_NOTEXISTS_PRODUCT(false,3034,"존재하지 않는 작품입니다."),
    GET_PRODUCTS_NOTEXISTS_USER(false,3035,"존재하지 않는 유저입니다."),
    // [PATCH] /users/:userIdx/addresses/status
    DELETE_ADDRESS_NOTEXISTS_IDX(false,3036,"이미 삭제됐거나 존재하지 않는 배달지인덱스입니다."),
    DELETE_FAIL_ADDRESS(false,3037,"배달지 삭제에 실패했습니다."),
    // [PATCH] /users/:userIdx/addresses/{deliveryIdx}/status
    PATCH_ADDRESSINFO_NOTEXISTS_IDX(false,3038,"존재하지않는 배달인덱스입니다."),
    MODIFY_FAIL_ADDRESSINFO(false,3039,"배달지 정보 변경에 실패했습니다."),
    // [POST] /users/:userIdx/orders
    POST_ORDERS_NOTEXISTS_USER(false,3040,"존재하지않는 유저입니다."),
    POST_ORDERS_NOTEXISTS_PROD(false,3041,"존재하지않는 작품입니다."),
    POST_ORDERS_NOTEXISTS_AUTHOR(false,3042,"존재하지않는 작가입니다."),
    POST_ORDERS_NOTEXISTS_PRODSIDE(false,3043,"존재하지않는 작품옵션입니다."),
    //[POST] /users/:userIdx/orders/reviews
    POST_REVIEWS_NOTEXISTS_USER(false,3044,"존재하지않는 유저입니다."),
    POST_REVIEWS_NOTEXISTS_PROD(false,3045,"존재하지않는 작품입니다."),
    POST_REVIEWS_EXISTS_STATUS(false,3046,"이미 작성한 리뷰입니다."),
    POST_REVIEWS_NOTHAVE_AUTHORITY(false,3050,"구매자만 리뷰를 작성할 수 있습니다."),
    //[PATCH] /users/:userIdx/orders/reviews/status
    REVIEWS_DUPLICATE_DELETED(false,3047,"이미 삭제된 리뷰입니다."),
    PATCH_REVIEWS_NOTEXISTS_PROD(false,3048,"존재하지않는 리뷰입니다."),
    //[PATCH] /users/:userIdx/orders/reviews
    PATCH_REVIEWINFOS_NOTEXISTS_PROD(false,3049,"존재하지않는 리뷰입니다."),
    //[GET] /users/:userIdx/orders/:orderIdx/baskets
    GET_NOWBASKETS_NOTEXISTS_ORDER(false,3053,"존재하지않는 주문입니다."),
    //// [POST] /users/:userIdx/orders/requests
    POST_REQUESTS_NOTEXISTS_USER(false,3054,"존재하지않는 유저입니다."),
    POST_REQUESTS_NOTEXISTS_PROD(false,3055,"존재하지않는 작품입니다."),
    POST_REQUESTS_EXISTS_STATUS(false,3056,"이미 요청사항이 존재합니다."),
    POST_REQUESTS_NOTHAVE_AUTHORITY(false,3057,"요청사항을 적을 권한이 없는 유저입니다."),
    // [PATCH] /users/:userIdx/orders/requests
    PATCH_REQUEST_NOTEXISTS_REQUESTIDX(false,3058,"존재하지 않는 요청사항인덱스입니다."),
    PATCH_FAIL_REQUEST(false,3059,"요청사항 수정에 실패했습니다."),
    // [PATCH] /users/:userIdx/orders/requests/status
    DELETE_REQUEST_NOTEXISTS_REQUESTIDX(false,3060,"이미 삭제됐거나 존재하지 않는 요청사항인덱스입니다."),
    DELETE_FAIL_REQUEST(false,3061,"요청사항 삭제에 실패했습니다."),
    // [GET] /users/:userIdx/orders/:orderIdx/payments
    GET_PAYMENTS_NOTEXISTS_ORDER(false,3062,"존재하지 않는 주문입니다."),
    // [PATCH] /users/:userIdx/orders/payments
    PATCH_ORDERS_NOTEXISTS_ORDERIDX(false,3063,"존재하지 않는 주문 인덱스입니다."),
    PATCH_ORDERS_NOTEXISTS_DELIVERYIDX(false,3064,"존재하지 않는 배솓지 인덱스입니다."),
    // [POST] /users/:userIdx/orders/baskets
    POST_BASKETS_NOTEXISTS_USER(false,3065,"존재하지않는 유저입니다."),
    POST_BASKETS_NOTEXISTS_PROD(false,3066,"존재하지않는 작품입니다."),
    POST_BASKETS_NOTEXISTS_AUTHOR(false,3067,"존재하지않는 작가입니다."),
    POST_BASKETS_NOTEXISTS_PRODSIDEIDX(false,3068,"존재하지않는 작품옵션입니다."),
    // [GET] /users/:userIdx/orders/baskets
    GET_BASKETS_NOTEXISTS_USER(false,3069,"존재하지않는 유저인덱스입니다."),
    // [POST] /users/:userIdx/orders/baskets/payments
    POST_ORDERS_NOTEXISTS_ORDERPRODIDX(false,3070,"존재하지않는 주문작품 인덱스입니다."),
    POST_ORDERS_NOTEXISTS_BASKETIDX(false,3071,"존재하지않는 장바구니 인덱스입니다."),
    // [GET] /users/:userIdx/orders
    GET_USERORDERS_NOTEXISTS_USER(false,3072,"존재하지않는 유저 인덱스입니다."),
    // [PATCH] /users/:userIdx/orders/baskets/:basketIdx
    PATCH_BASKETS_NOTEXISTS_ORDERPRODIDX(false,3074,"존재하지않는 주문작품 인덱스입니다."),
    PATCH_FAIL_PRODN(false,3075,"옵션수량 변경에 실패했습니다."),
    // [PATCH] /users/:userIdx/orders/baskets/status
    DELETE_BASKETS_NOTEXISTS_BASKETIDX(false,3076,"존재하지않는 장바구니 인덱스입니다."),
    DELETE_FAIL_BASKET(false,3077,"장바구니 삭제에 실패했습니다."),

    INVALID_TOKEN(false,3078,"유효하지 않은 토큰입니다."),
    FAILED_TO_KAKAOLOGIN(false,3079,"카카오 로그인에 실패했습니다."),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");


    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
