package org.iclass.mvc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.iclass.mvc.dto.Gallery;
import org.iclass.mvc.dto.Heart;
@Mapper
public interface GalleryMapper {

	List<Gallery> getList();
	int save(Gallery dto);
	List<Gallery> getMyList(String writer);		//writer 가 작성한 글들
	
	////////3-c. 좋아요 ////////////////////
	void updateHeartCount(int idx);					//특정 글의 좋아요 갯수 컬럼값 수정
	int hearts(int idx);	//특정글의 좋아요 갯수 리턴
	void heartTrue(Heart heart);		//좋아요 등록
	void heartFalse(Heart heart);		//등록된 좋아요 취소
	
	List<Integer> myHearts(String userid);		//userid가 좋아요 한 글의 목록
}
