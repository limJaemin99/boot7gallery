/**
 * socketConnect.js : 소켓라이브러리 사용하여 소켓통신을 위한 객체를 생성
 * 소켓 변수는 전역변수.
 */
'use strict'    //use strict : 암묵적인 "느슨한 모드(sloppy mode)"를 해제하고,명시적인 "엄격 모드(Strict Mode)"를 사용하는 방법
let websocket       //▲ 오류 감지, 보안 강화, 성능 최적화, ECMAScript 5 준수 효과
//소켓 생성과 정의를 함수
const socket_func = function(){
    websocket = new WebSocket("ws://localhost:8086/alarm");      //소켓통신의 URL을 인자로 전달. 웹소켓 프로토콜 ws 사용
    //객체가 생성되면서 통신이 시작됨.

    //소켓의 이벤트(통신 열림,닫힘,데이터 수신) 처리 함수를 정의
    websocket.onopen = on_Open          //소켓 통신 시작 (초기화) 이벤트
    websocket.onmessage = on_Message    //소켓 통신 데이터 수신 이벤트 (onmessage 는 소켓 이벤트, 함수 이름 on_Message)
    websocket.onclose = on_Close         //소켓 통신 닫힘 이벤트

    //웹소켓이 시작됬을 때 처음 한번만 실행되는 메소드
    function on_Open(){
        console.log("소켓 통신이 열림.")
        if (userid!=null)
            websocket.send('open/'+userid)      //1-d 데이터 서버에게 보내기 (일반 문자열 or json)
    }

    function on_Close(message){  //웹소켓이 닫혔을때 실행되는 메소드.
        console.log('소켓 통신이 닫힘.')
    }

    //데이터를 받으면 실행되는 메소드
    function on_Message(message){   //message 인자는 서버에서 소켓통신으로 보낸 데이터 저장. 객체 타입.
        console.log('받은 데이터 : ' , message)  // ▶ MessageEvent {isTrusted: true, data: 'twice 님이 접속하였습니다.', origin: 'ws://localhost:8086', lastEventId: '', source: null, …}
        console.log('받은 데이터 : ' + message)  // ▶ [object MessageEvent]

        if(message.data.includes("alarm")){ //message 에 alarm 이 있으면 실행
            const heartObj = JSON.parse(message.data)
            document.querySelector('#message').innerHTML = heartObj.alarm
        } else
        //1-f. 받은 데이터를 header.html 에 출력하기. 받은 메시지 message 는 객체이고 이 중 data 프로퍼티값이 실제 내용.
        document.querySelector('#message').innerHTML = message.data
    }

}  //socket_func() 함수 끝.

//userid 세션의 값이 있을 때 즉 로그인이 됐을때만 소켓통신 시작.
if(userid != '') socket_func()

function logout() {      //로그아웃 함수
    // e.preventDefault()
    // e.stopPropagation()
    // if(e.target.tagName !='A') return

    websocket.send('close/'+userid)
    websocket.close()      //소켓통신 연결종료
    location.href='/logout'
}

//좋아요 체크박스 이벤트 함수
function heart(idx,value,writer){
    //★ 서비스 클래스에서 Heart 타입의 자바 객체로 매핑될 것이므로 key 이름이 Heart 클래스 필드와 일치해야 한다.
    const heartDto = {writer:writer,idx:idx,value:value,userid:userid}

    console.log(heartDto)
    websocket.send('heart/'+JSON.stringify(heartDto))   //직렬화한 문자열을 보내기 (앞에는 'heart/' 로 시작)

}
