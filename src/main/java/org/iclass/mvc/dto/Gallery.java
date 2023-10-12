package org.iclass.mvc.dto;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Gallery {		//POJO : bean 생성이 안되는 일반적인 자바 객체
	private int idx;
	private String title;
	private String filenames;		//파일명 여러개를 , 로 구분 해서
	private LocalDateTime createAt;
	private String writer;
	private int hearts;
	
	//테이블 컬럼에는 없고, 파일업로드에 순수하게 DTO 용도로 사용합니다.
	private List<MultipartFile> pics;	//여러개 파일 첨부하기 위해 List<> 사용함

}
/*
create table GALLERY
(
    IDX       NUMBER(5) not null
        primary key,
    TITLE     VARCHAR2(40),
    WDATE     DATE,
    FILENAMES VARCHAR2(200),
    WRITER    VARCHAR2(50),
    HEARTS    NUMBER default 0
)
/

 */
