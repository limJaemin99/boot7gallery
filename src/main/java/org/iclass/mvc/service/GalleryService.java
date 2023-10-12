package org.iclass.mvc.service;

import java.util.List;
import java.util.Map;

import org.iclass.mvc.dto.Gallery;
import org.iclass.mvc.dto.Heart;

public interface GalleryService {		//서비스를 인터페이스로 하는 이유는?
	//같은 기능을 여러가지 방법으로 구현해서 테스트 또는 업그레이드할 때 기존과 호환 시키기 위해서 합니다.
	
	List<Gallery> getList();
	int save(Gallery dto);
	List<Gallery> getMyList(String writer);		//writer 가 작성한 글들
	
	/////////3-d. 좋아요 ///////
	Map<String,String> processHeartCount(String data) ;		//json 문자열 받아서 좋아요 처리
	List<Integer> myHearts(String userid);		//로그인한 사용자가 좋아요 누른 글 목록
	int hearts(int idx);			//특정글의 좋아요 갯수 리턴
}
