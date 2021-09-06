<<<<<<< HEAD
# 아이디어스B 서버 개발일지 - Bryn  

### <21/07/31>
기획서 변동 사항:dev/prod => test/product 이름 변경
####1.ec2 인스턴스 구축  
->볼륨 부족으로 작업 불가  
=>aws에서 ec2 볼륨 32G로 확장  
->이후 늘어난 저장공간 배치 오류  
=>구글링 통해 해결 완료  
####2.깃 연동  
->src refsrec master does not match any~오류  
=>구글링 통해 main이라는 브랜치 따로 만든 후 해결  
####3.test/product 서버 구축  
->SSL 인증서 받을 시 CNAME 타입 DNS여서 www.~주소 오류  
=>www.없는 주소로 SSL 적용 완료  
####4.RDS DB 구축(스키마 생성)  
####5.ERD 설계 시작  

### <21/08/01> 
%FE분과 소통%: 소셜로그인 부분 커밋 추가하신 거 보고 소셜로그인 차후에 진행하기로한거 맞는지 다시 확인함(맞음)  
           로그인, 회원가입부분 완료하셔서 완성되면 알려달라하심
####1.test/prod 서버 수정  
->서버분리 잘못된 방법으로 해서 yml에서 test(8080포트), prod(9000포트)로
분리한 후 ec2도 다시 수정 완료  
->로컬, ec2 포스트맨에서 8080포트로 test/log 작동하는 거 확인함   
->/test/log없이 도메인 주소 접속시 오류 확인해봐야함(슬랙에 질문 올림)
####2.ec2 서버 시간 서울로 설정  
->ec2 서버 시간 바꾼 후 스프링 부트 실행 시 오류 발생  
=>구글링통해 데이터베이스 시간과 ec2시간 Asia/Seoul로 동일하게 맞춘 후 오류 해결  
####3.ERD 설계 진행 중  70%
->테이블 17개 생성(클래스, 작가 상세페이지 일부분, 주문 일부분, 후원 나중에 더 구현해야함)  
->Product 테이블 나눌 거 없는지 더 생각해보면 좋을 것 같음  
####4.API 명세서 작성 시작  
####5.로그인,회원가입 코드 작성  
->작성 후 로컬에서 정상 작동 확인함. product 서버에서 확인 필요 및 validation 추가해야함  
####6.RDS와 템플릿 연결 시 오류 발생 및 해결  
->스키마 새로 생성해서 스키마 이름 오타없이 잘 적었는데 계속 알 수 없는 데이터베이스라고 스프링부트 실행안됨     
=> ec2 mysql 들어가서 생성한 스키마와 같은 이름으로 데이터베이스 생성 명령어 쳐주고 해결   
->이후 api 로컬에서 실행시 해당 테이블이 없다고 계속 오류 뜸  
=>오류 해결, 초기 템플릿을 갖다쓰느라 RDS 엔드포인트가 제대로 적혀 있지 않았음  
엔드포인트 제대로 yml에 다 적어준 후 정상 작동 확인함  

### <21/08/02>  
%FE분과 소통%: 피드백 전에 엮어보고 가시면 둘다 좋을 것 같아서 언제까지 드리면 피드백 전에  
엮어보시고 가실 수 있는지 물어보니 오늘 오전 중으로 보내주면 가능하다고 하심.  
->오전 중에 보내드리고 잘 엮어지는 거 확인 함. 
1차 피드백 이후 회의를 한 번 해야할 것 같아서 목요일 낮 정도에 회의 진행하기로 함. 
서로 전달 사항만 모아볼 수 있는 공간이 따로 있으면 좋을 것 같아서 카톡에 공지 만들어서 댓글에 전달사항 정리해서 공유함.    
<0802 FE분한테 전달사항>  
1. 회원가입 validation(잘못된 값 들어오면 처리)부분 오류코드 숫자가 수정된 게 있습니다.  
2. 탈퇴한 유저가 로그인 시 validation처리가 잘되는지 확인하기 위해
   userIdx 1번 상태를 N(삭제된 상태)로 바꿨습니다.  
3. 피드백을 받다보니 하나의 test 계정을 정해 계속 쭉 실행을 해야되는 것 같습니다!  
   (userIdx 3번 박서준 회원을 계속 test 계정으로 사용하면 어떨까 싶습니다!)  
   ->로그인시 생성받은 jwt 토큰을 계속 바디에 넣어서 실행해야 앞으로 나오는 API들을
   실행할 수 있습니다!(한 번 로그인하면 한 유저만 계속 사용해서요!)   
4. 박서준 계정 이메일 양식에 danum.com이라 적혀있길래 daum.com 적으실라하신 것 같아
   수정했는데 맞나요??  
####1. 회원가입 API 전달/확인 완료   
####2. ERD 수정&&설명 영상 만들기  
->피드백 받은 걸로 다시 수정 진행 중(작가프로필,스토리,카트부분 테이블 추가 등등)      
####3. API 리스트업  
->피드백 받은 걸로 다시 수정 해야 함 
####4. 1차 피드백 진행  
%피드백 받은 내용%  
-FE 전달 시 validation까지 진행 후 보내야 함  
-API 기능 설계 pathvarible 인덱스 모호성 없애기(29)  
-API 기능 설계 순서 맞춰서 적기(37)  
-카트,주문 부분 FE분이랑 회의 진행해야함  
->목요일 낮 예정  
-탭이 달라질 경우 API는 하나,쿼리스트링으로 나타내기    
-프로필, 스토리 테이블 따로 생성해야함  
-댓글 테이블은 기능별로 따로하는 게 맞음(현재 구현된 상태)  
-API 유저 입장에서 만들기!  
->현재 설계한 것들 유저 입장 끝나면 클래스 유저 입장에서 진행  
-다음주 피드백까지 API 20개 정도 만들어 가야함  
%필수는 아니지만 제안 받은 부분%  
-세일에 제한 기간을 넣어서 그걸 기준으로 상태를 파악해도 좋을 것 같음  
-회원가입시 regax 처리를 더 추가해도 좋을 것 같음  
-조인을 사용해서 order의 총 금액을 계산할 경우 금액이 변경될 시  
이 전의 주문내역 가격도 변하므로 총 금액 칼럼을 하나 만들어도 좋을 것 같음  
####5. 로그인 API 완료 후 전달  
->FE분 잘 실행된다고 확인받음  

### <21/08/03>  
%FE분과 소통%: 작품 상세페이지가 작품마다 조금 달라서 카톡으로 어떻게 맞출지 여쭤봄
####1. ERD 수정 후 RDS에 반영  
->테이블 총 21개->29개로 증가  
####2. 피드백 내용으로 API 수정  
->행위주체 부분 헷갈려서 개발팀장님한테 문의 후 수정 완료 
####3. 작품 상세페이지 구현 위한 더미데이터 넣기  
####4. 작품 상세페이지 구현 쿼리 작성 진행 중  
->세일퍼센트로 세일된 가격 계산하는 쿼리 작성 중 실수 곱셈으로 인해  
자료형이 int에서 자동으로 실수형으로 바껴서 구글링 후 convert함수 활용해 해결  
####5. 찜 생성, 해제 API 개발  
->사용자가 찜을 눌렀다가 해제하더라도 그 기록들을 다 서버에 저장하고 있어야하고 활성화된 찜은 칼럼들 중 최대 하나만 존재해야하므로  
어떻게 구현을 해야할지 어려움을 겼음  
=>찜을 누를 땐, POST로 생성을 하는데 이때 이미 상태가 Y인게 존재한다면 생성이 안된다는 오류가 뜨고
아니라면 생성이 됨. PACTH로 해제할 때는 이미 해제가 됐다면 해제가 안되도록 구현함.  
이때 이미 찜 상태인 칼럼이 존재하는데 중복으로 활성화된 찜생성을 막거나 사용자가 여러 번 누르고 해제해서 칼럼이 여러 개일 경우  
오류를 막기위해서 작품조회시 무조건 해당 유저-작품간 최신 찜 상태 칼럼이 뜨도록 쿼리를 수정하고  
FE단에서 이 값에 따라 생성/해제 API로 넘어갈 수 있도록 현재 유저의 찜 상태와 찜 인덱스를 넘겨주도록 쿼리를 수정함.
->서버에서 계속 404에러 떠서 시간 잡아먹음  
=>무중단 서비스 중지하고 다시 재시작하고 해결  

### <21/08/04>  
%FE분과 소통%: 작품 상세페이지 구현하면서 서로 궁금한 점들 상의하며 틀 맞춰감.  
내일 회의 3시쯤 진행하기로 함  
####1. 작품 상세페이지 부분 쿼리 작성 및 API 구현 완료  
->작품 상세페이지에 데이터 양이 상당히 많아서 어떻게 쿼리들을 나누고 정보들을 어떻게 유기적으로 처리할 지 고민을 함  
=>1:다 관계인 것들을 쿼리로 나누고 판매중인 다른 작품들은 작가 인덱스로 엮어서  
이 작품과 함꼐 본 작품들은 작품 카테고리로 엮어서 카테고리 관련 인기작품들은 해당 카테고리 작품들의 찜수가 많은 순대로 추천 작품들이
뜨도록 구성함.  
->해당 작품의 작가의 모든 작품들의 찜의 갯수를 세는 로직을 구현할 때 계속 다른 숫자가 뜸  
=>유기적으로 다시 종이에 써서 연결을 확인해본 후 group by 활용해 해결   
->명세서 다 작성 후 서버에 올리는데 깃으로 받고 무중단 서비스 중지 후 재시작해도 연결이 안됨  
=>다시 중단하고 시작한 뒤에 좀 기다리니까 연결됨 앞으로도 무중단 서비스 시작 후 좀 기다리고 실행해봐야 될 것 같음  
####2. 작품 상세페이지 명세서 작성  
####3. 더미데이터 넣기 40%  
->사용자단 아닌 이미지들은 서버 폴더에, 사용자단에서 처리되는 이미지들은 url로 받기 위해 리뷰 이미지 링크 수정함  
=>FE분에게 작품 상세페이지 리뷰부분 url로 이미지 받는 거 전달 완료  
->작품 상세페이지 구현에 필요한 더미데이터들만 우선적으로 집어넣음  

### <21/08/05>   
####1. FE분과 회의 진행   
:작가 상세페이지 부분 의논, 주문/결제 방식 의논, 홈화면 구현 방식 의논  
->더미데이터 값들 수정해야 함  
->홈화면 구현 이후 즉시구매 부분 먼저 진행하기로 함  
####2. 작품 댓글 생성/삭제 API 개발  
####3. ERD 및 API 수정  
최근 조회한 작품과 유사한 작품 구현하기 위해 ERD, API 추가 설계  
홈화면 배너부분 ERD 추가  
####4. 서버 폴더에 있는 이미지 저장시 연결 안되는 문제 발생  
->검색해보니 아이디어스 웹사이트에서 이미지 링크를 받을 수 있어서 하나씩 다 캡처해서 사진 따다가
그 방법으로 바꿈  
####5. 장바구니, 주문 로직 피드백 반영해서 수정   
->즉시구매와 장바구니를 통한 주문/결제 방식으로 나누어 로직 생각해봄  
####6. 홈화면 쿼리 작성 시작  

### <21/08/06>  
####1.쿼리 작성 시 에러 발생   
->order by를 처리한 후 group by를 같은 depth에서 처리하려고하자 오류  
=> 구글링통해 쿼리문을 한 번더 감싸 깊이를 다르게 해준 후 해결  
-> 이후 order by를 잘 실행해도 다시 감싸서 group by하면 앞 선 order by가 무시됨  
=>구글링통해 order by문 아래에 LIMIT 18446744073709551615 적어준 후 오류 해결  
(참고사이트: https://jaenjoy.tistory.com/39)  
####2.ERD 수정  
Order,OrderProduct 칼럼 수정  
####3.API 기능 설계 추가    
####4.홈 화면 GET API 구현, 명세서 작성 완료  
####5.더미데이터 넣기  
홈화면 조회위해서 작품 10개정도 더미데이터 추가  
주문, 리뷰쪽 더미데이터 추가  
####6. 쿼리 수정  
전체 평점 계산시 소수점 1자리까지만 뜨도록 수정  
####7.방문기록 생성/수정 API 구현, 명세서 작성 완료  
->작품 홈화면 유저가 방금 본 작품과 유사한 작품 구현하기 위해  
####8.배달지 생성/조회/삭제/변경 API 구현 완료 명세서 작성 완료   
####9. 작품 상세페이지 validation 추가하여 명세서 반영 완료  

### <21/08/07>  
%FE분과 소통%: 주문 로직 처리 관련하여 일차원 배열 POST 인풋으로 가능하신지 여쭤봄  
->가능할 것 같다 하심  
홈화면 new 탭부분에 정보 컬럼항목 다른거 준 거 있다고 연락주심  
->주문 로직 다시 짜면서 DB구성이 좀 바껴서 홈화면 쿼리를 좀 수정했는데 그러면서 컬럼이 빠진 것 같음.  
=>금방 수정할 것 같아서 자기전에 수정하고 잘 예정  
####1. 주문 로직 구현, 로컬에서 확인 완료  
->구현하다보니 주문:주문작품:주문작품옵션이 1:N:M관계, 이걸 트랜잭션해서 처리하려니 너무 복잡하고 데이터 주시는 것도 이차원 배열을
주셔야하는데 그렇게 되면 산으로 갈 것 같아서 기존의 한 API로 구상했던 것을 아이더스 앱의 페이지화면 별로 두 단계로 나눠 
API 2개로 나눔(작품 담는 API, 주문서 작성하는 API)  
->트랜잭션 활용해야하는데 사용해본적이 없어서 어떻게 구현해야할지 애먹음  
=>구글링 통해 @Transactional 사용하면 된다는 거 알게되고 그거 사용해서 구현  
->트랜잭션 적용 후 한 메서드 다오에서 insert문 두개를 반복문 안에서 동시에 처리해야하는데 구문 작성에 있어 어려움 겪음  
=>인서트 두개를 합치는 게 문제가 있는 것 같아서 인서트 1번 업데이트 1번으로 수정함  
->이후 업데이트 sql문에 order by랑 offset을 활용해서 알맞는 행 위치에 값 넣으려고 하는데 계속 인식이 안됨 
->물음표, 변수에 상수값 넣는거, 변수값을 넣는 거 다해봤는데 안됨  
=>update 뒤 order by에 변수를 쓴다는 게 문제가 있는 것 같아서 아예 상수로 들어가도록 반복문을 다오에서 서비스로 옮기고
코드를 한 번 전체적으로 수정함  
=>이후 로컬에서 잘 실행되는 거 확인함  
####2. 슬랙 답변 기다리면서 리뷰 작성/생성/수정 API 먼저 개발  
->조회는 주문 구현 후 주문 로직이 정확해지면 구현하는 게 좋을 것 같아서 주문 끝나고 구현 예정  
####3. 전체적으로 형식적 validation 처리 부족한 부분 다시 검토하면서 다 추가함  
(index 8,17,31,33,52,54,55에 validation 일부 추가)  
####4. WInSCP에서 푸티 접속 눌러도 씹히는 문제 발생  
->WinSCP 업데이트하라는 알림 계속 무시하고 있었는데 그것때문에 그런가하고 업데이트 진행  
->그래도 안됨  
=>노트북 재시작했더니 해결됨. 요즘 하루종일 노트북 써서 잠깐 노트북이 이상해졌던 것 같음  

### <21/08/08>  
####1. 작품 주문 API 실행시 2번에 1번 꼴로 값이 안들어오는 오류 발생  
->slack에 질문올리고 기다리는 중  
####2. API 구현방식 변경(주문)    
원래는 주문서까지 넘어간 이후에 Order테이블 행을 생성하려고했는데(1:N:M관계 처리 쉽게 바꾸고자) 그렇게 되면
주문서 이전 페이지의 장바구니 페이지에서 정보를 조회할 때 OrderProduct당 API를 다 호출해야되고 그 결과를
FE에서 다시 처리를 해서 붙여야되는 걸 알게 됨.  
그래서 다시 1:N:M 관계를 처리할 방법이 없을까 생각해보다 2차원 배열을 쓰지않더라도 서버단에서 for문을
이중 for문으로 바꾸고 거기에 변수를 적절하게 계산해서 넣으면 2차원 배열까지 안받고 처리할 수 있는 걸 알게됨.  
이후 다시 작품 즉시주문 POST API 수정  
->이후 포스트맨으로 로컬에서 실험해보는데 같은 값을 넣어도 매번 다른 값들이 DB에 들어와서 개발팀장님한테 물어봄  
####3. 즉시구매 이후 장바구니 페이지 GET API 구현 및 명세서 작성 완료  
####4. 3번 페이지에서 요청사항 적는 부분 PATCH API 구현 중   
->원래는 OrderProduct에 request 컬럼을 null로 둔 걸 patch 하려고 했는데 앱상에서 작가당 요청사항이 한 번 입력되고
그 요청사항이 모든 OrderProduct에 해당돼서 API를 orderProduct 당으로 매번 다 호출하기엔 나중에 장바구니에서 사용시 호출되는 API가 너무 많아짐  
=>작품당으로 배열을 이용해 작가당 한번에 등록할 수 있도록 request라는 테이블을 따로 빼서 넣는거로 수정 중    

### <21/08/09>
####1. 작품 즉시 구매 API 값 이상하게 들어오는 오류 해결  
->DB 오류 들어오는 거 분석하다보니 order by에서 앞선 행의 최신 인덱스를 잡아오는 게 잘 안되고 있었음  
(insert문이 생성되는데 걸리는 시간이 오락가락해서 그게 order by createAt으로 연결되어 문제가 발생했던 것 같음, 구글링해보니 insert문이랑 limit이 원래 좀 
수행 시간이 느리다써있었음, 예전에 RDS 생성할때도 내 RDS 생성에 걸리는 시간이 다른 분들에 비해 훨씬 느렸던 것 같은데 그것도 원인 중 하나가 될 수도 있을 것 같음)    
=>예전에 어셈블리 수업에서 시간차로인한 데이터의 잘못된 접근을 막기위해 nop으로 강제 delay slot 넣어줬던게 생각나서 자바에서 시간지연시키는 
코드 구글링해서 넣어줬더니 오류 해결됨  
####2. 2차 피드백 내용 정리  
-홈화면 null 최대한 피하게 안되면 0으로(지금은 이미 엮은 상태이므로 다음 프로젝트부터 반영)  
-실시간 구매, 실시간 후기 쿼리스트링으로 나눠서  
-페이징 처리 여쭤보기  
-앱 사진 넣기  
-25개~30몇개정도 API  
-소셜로그인 해보기  
-금요일 정오까지 5분~10분 시연 영상 포스트맨 상으로 보여주기(url product서버로, 디테일 신경 쓴 부분 등등)     
####3. FE분과 회의  
-홈화면 부분 new 탭 나누는 거 아직 안엮으셨다고 그래서 피드백 받은 내용대로 실시간구매,실시간후기 나눠서 다시 API 드릴예정  
-페이징처리 여쭤보니 어려울 것 같아서 일단 주문부분 같이 중요한 부분들 끝내시고 상황을 봐야할 것 같다는데 안할 것 같으심  
-서로 피드백 받은 내용 공유하며 다시 한 번 개발 순서 확인함  
-주문 부분 오늘 내로 해결해서 드린다고했더니 UI 채울부분 많아서 그거하고있으면 된다고 할 거 많다고 걱정하지말라해주심  
####4. 피드백 받은 홈화면 now 탭 다시 tab 나누는 거 해서 드림  
->defalut로 실시간 구매가 뜨도록 디테일 신경씀
####5. 요청사항 POST API 구현 및 명세서 작성 완료  
처음에는 orderProduct 인덱스 하나를 받아서 매번 API 여러 번 실행하는 로직으로 짜다가
그러면 엮을 때 실행 횟수가 너무 많아질 것 같아서 작품별로 요청사항 하나를 공유하므로 배열로 작품별 OrderProduct 인덱스를 받아서
요청사항 하나가 다 해당 주문작품인덱스들에 들어갈 수 있도록 코드 구현함  

### <21/08/10>
%FE분과 소통%:즉시구매 주문 POST부분 인자 물어보셔서 설명해드림, 즉시구매 장바구니 페이지 부분 가격 물어보셔서 확인해보니 가격 안넣은 거 알고
수정해서 다시 드림  
####1. 요청사항 수정/삭제 API 구현 및 명세서 작성    
####2. 즉시구매 장바구니 페이지 옵션포함 작품별 가격 내용 추가  
####3. 결제 페이지 구현(GET/PATCH) 및 명세서 작성 완료    
Order ERD 수정함(배송지,결제 정보 담는 부분 추가)  
Payment ERD 수정함(대표결제 구분하는 컬럼 추가)  
####4. 명세서에 앱화면 캡처 사진 다 넣기  

### <21/08/11>  
%FE분과 소통%: 장바구니 버튼당 API 연결하시는 거 안내해드림,  오늘까지 API 만들고 내일은 소셜로그인 해볼 생각이라 오늘 만들 API들
얘기해드림, 장바구니 조회 부분 작품 대표 사진 까먹고 안넣어서 바로 수정해서 드림  
####1. 장바구니 POST/GET API 구현 및 명세서 작성  
####2. 유저 내정보페이지 주문 배송 API 구현 및 명세서 작성  
->Order 테이블 statement 주문 배송 페이지 보고 한 단계 더 추가함(취소 완료 단계)    
####3. 장바구니에서 주문 생성 API 구현 및 명세서 작성  
####4. 장바구니 수정/삭제 API 구현 및 명세서 작성  
####5. 오늘 만든 API들 명세서에 앱 캡처 사진 넣기  

### <21/08/11>  
%FE분과 소통%: 어디까지 엮으셨는지 물어봄. 엮으시면서 주문 순서와 다르게 중간부터 API가 실행되는 등 원래 로직대로 실행이 안되면서
홈 화면에서 실시간 구매 부분에 null 값이 껴있는 경우가 있는거 확인함. 다 엮으시고 시연영상 찍으시기 전에 null 값 뜨는 부분있으면 데이터
수정해드리기로함  
####1. 카카오 로그인 구현 및 명세서 작성 완료   
구글에 카카오로그인을 쳐서 따라하며 공부를 하고있었는데 대부분 클라+서버를 동시에 하는 경우가 많아서
공부하다보니 어디까지 클라가하고 어디까지 서버가 해야되는 건지 헷갈리기 시작함  
->클라이언트분에게 해당 사항 공유하며 서로 어떤 값을 주고 어떤 값을 드릴 것 같은지 상의해봄    
->둘다 잘몰라서 결론이 안나는 것 같길래 슬랙을 통해 개발팀장님한테 여쭤봄  
=>클라이언트가 엑세스 토큰을 전달하고 서버에서 전달받은 토큰을 parsing한 후 jwt 토큰 생성해서 response로 드리면 된다는 거 알게됨  
->parsing하는 과정에서 com.google.gson.JsonElement import가 계속 안되서 jackson도 사용해보고 했지만 잘 안됨  
->구글링통해 dependency 추가해줘야된다는 거 알게 되고 xml 파일에 코드 적어서 실행해봤는데 안됨  
=>구글링하다가 build.gradle에 적으면 된다는 거 발견하고 dependcy 추가한 후 빌드하고 실행했더니 정상 작동 확인  
####2. validation 처리 빠진 거 없는지 전체적으로 확인  
####3. 주문 생성시 강제지연으로 실행 느린 거 최적화  
강제시간지연 코드마다 5초씩 지연을 했었는데 최적화 통해 1초로 줄임  
millisecond 단위도 실행을 해봤지만 너무 짧아서 데이터가 이상하게 들어감  