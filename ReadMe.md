#### 데이터 송수신이 발생했을 때 처리 할 내용을 정해본다.
#### 1. 새로운 클라이언트와 소켓이 연결되면 모든 접속자들에게 'id ... 님이 접속하였습니다. ' 라고 보낸다. 아래 소스파일 수정하고 테스트 합니다.
* AlertHandler.java 
* socketConnect.js
* header.html
* 실행 : 브라우저 2~3개를 열고 서로 다른 값 id로 로그인 합니다. ( pdf 파일 참고)

#### 2. 지정된 사용자에게만 메시지를 보내봅시다. - gallery 에 작성된 글에 '좋아요' 를 클릭하면 글 작성자에게 메시지를 보냅니다.<br>
__소스파일 작성 순서__
* footer.html
* index.html
* gallery.html
* socketConnect.js
* AlertHandler.java 
* 실행 : 1번과 동일하게 브라우저 2~3개를 열고 서로 다른 값 id로 로그인 합니다. (오픈카톡방 pdf 파일 참고)

#### 3. 좋아요  추가 또는 취소를 db에 적용하기
__heart 로 시작하는 메시지의 json 문자열을 역직렬화하여 db 관련 처리__

* heart.sql 참고하여 tbl_hearts 테이블을 만들고 pdf파일 참고하여 데이터를 추가합니다.

* db 관련 추가
    * Heart.java
    * gallery.xml 
    * GalleryMapper.java

	
* 서비스 로직 추가
    * GalleryService.java

	
* 웹소켓에서 받은 메시지 처리 방법 변경
    * GalleryServiceImpl.java (소스 파일명 스펠링 오타 맨 마지막에 수정할 예정)
    * AlertHandler.java


* 로그인 사용자가 좋아요 한 글은 체크 표시하기
	* gallery.html
	* GalleryController.java    
