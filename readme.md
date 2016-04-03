## Spring 4 + MyBatis 3 + MariaDB 기반 게시판 ##
본 샘플은  Spring 4 + MyBatis 3 + MariaDB (Maven) 기반으로  게시판을 만드는 과정을 단계별로 구현한 샘플이다.

자세한 설명은 [이곳에서](http://forest71.tistory.com/2) 얻을 수 있다. 

각 내용은 다음과 같이 구성되었다.

### 1. board Step 1 (board1) ###
- List: 모든 게시물 출력
- Form: 사용자 입력 내용 저장
- Update: 사용자 입력 내용 수정
- Read:   사용자 입력 내용 보기
- Delete: 지정된 게시물 삭제

URL: http://localhost:8080/board/board1List

### 2. board Step 2 (board2) ###
- List: 페이징, 새로운 번호 부여
- Form: 입력/수정을 하나로
- Read: 조회수 
- Delete: 삭제에서 숨기기로

URL: http://localhost:8080/board/board2List


### 3. board Step 3 (board3) ###
- List: 검색, 제목을 한 줄로 표시 ==> 페이징을 공통으로 
- Form: 필수입력, 수정/저장 서비스 하나로
- Read: 스크립트 실행 방지

### 4. board Step 4 (board3) ###
- 자료실

##### 5. board Step 5 (board5) #####
- 계층형 게시판

##### 6. board Step 6 (board6) #####
- 댓글

##### 7. board Step 7 (board7) #####
- JQuery 활용
- 댓글, 파일 다운로드

##### 8. board Step 8 (board8) #####
- 멀티 게시판

### 개발 환경 ### 
    Programming Language - Java
    IDE - Eclipse
    DB - MariaDB
    Framework - MyBatis, SpringMVC
    Build Tool - Maven

### 설치 ###

먼저 다음과 같은 테이블을 생성해야 한다.
 
    CREATE TABLE TBL_BOARD (
      BRDNO int(11) NOT NULL AUTO_INCREMENT,
      BRDTITLE varchar(255),
      BRDWRITER varchar(20),
      BRDMEMO   varchar(4000),
      BRDDATE	datetime,
      BRDHIT INT,
      BRDDELETEFLAG CHAR(1)
      PRIMARY KEY (BRDNO)
    ) ;

    CREATE TABLE TBL_BOARDFILE (
    	FILENO INT(11)  NOT NULL AUTO_INCREMENT,
    	BRDNO INT(11),
    	FILENAME VARCHAR(100),
    	REALNAME VARCHAR(30),
    	FILESIZE INT,
    	PRIMARY KEY (FILENO)
    );

\board\src\main\webapp\WEB-INF 폴더에 있는 applicationContext.xml에서 적절한 DB 접속 정보를 입력하고 실행하면 된다.


