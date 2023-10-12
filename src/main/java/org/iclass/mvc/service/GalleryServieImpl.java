package org.iclass.mvc.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.iclass.mvc.dao.GalleryMapper;
import org.iclass.mvc.dto.Gallery;
import org.iclass.mvc.dto.Heart;
import org.iclass.mvc.dto.HeartResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
@Transactional
public class GalleryServieImpl implements GalleryService{

	//의존성 주입:필드 주입은 사용하지 않습니다.
	private GalleryMapper dao;
	
	//@Autowired 생략(생성자 주입)
	public GalleryServieImpl(GalleryMapper dao) {
		this.dao=dao;
	}
	
	@Override
	public List<Gallery> getList() {
		return dao.getList();
	}

	@Override
	public int save(Gallery dto) {		//controller에서 넘어온 파라미터 값들로 insert + 파일업로드
		String path ="D:\\iclass0419\\upload";
		StringBuilder filenames = new StringBuilder(); 	//테이블 컬럼으로 전달될 파일명들(,로 구분)
		//파일업로드
		for(MultipartFile f:dto.getPics()) {	//첨부 파일이 여러개일 경우 반복
			if(f.getSize()!=0) {	//getSize()는 첨부파일의 크기
				String ofilename = f.getOriginalFilename();		//원래의 파일명 (파일이름.확장자)
//				String prefix = ofilename.substring(0, ofilename.lastIndexOf("."));   //원래의 파일이름
				String postfix = ofilename.substring(ofilename.lastIndexOf("."));	 //확장자
				StringBuilder newfile = new StringBuilder("gallery_")
				//		.append(prefix)		//원래의 파일이름
						.append(UUID.randomUUID().toString().substring(0,8)).append(postfix);
						// ▲ 사용자가 전송한 파일명 사용하지 않고 UUID.randomUUID() 로 랜덤 문자열 생성한 것 (8글자로 함)
				//path 폴더에 newfile 로  File 객체 생성해서 저장 준비
				File file = new File(path + "\\"+newfile);		
				//저장
				try {
					f.transferTo(file);				// f 파일의 내용을 file 객체로 전송 (파일 복사)
					filenames.append(newfile).append(",");  //db 테이블에 들어갈 파일명
				} catch (IOException e) {	}
			}	
		}	
		dto.setFilenames(filenames.toString());
		return dao.save(dto);
	}

	@Override
	public List<Gallery> getMyList(String writer) {
		return dao.getMyList(writer);
	}

	//////////////3-f. 좋아요 ///////////////////////////////////////////////////////
	@Override
	public Map<String,String> processHeartCount(String data) {	// ◀◀◀ 이 data 는 json 문자열 ★
		//이 메소드는 여러개의 insert,update,delete DML 을 실행하므로 한 묶음의 실행인 트랜잭션 처리가 필요하여 어노테이션을 추가함.
		//좋아요 문자열 정보를 받아 객체로 만들고 다시 필요한 결과값을 직렬화 한다.
		// ▲ 맨 위 참고

		Heart heart = null;
		Map<String,String> resultMap = new HashMap<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();		//json 과 자바 객체를 변환하는 매퍼
			heart = objectMapper.readValue(data,Heart.class);	//받은 문자열 -> 역직렬화 (Heart 타입 객체)
			log.info("━━━━━━━━━━ service heart 정보 : {}",heart);	//누가 어느 글에 좋아요를 등록/취소 했는지에 대한 값은 heart 객체에 저장
			String alarm = "[" + heart.getUserid() + "] 님이 gallery [" + heart.getIdx() + "] 번 글에";
			if (heart.isValue()){
				dao.heartTrue(heart);	//좋아요 등록
				alarm += " 좋아요를 남겼습니다.";
			} else {
				dao.heartFalse(heart);	//좋아요 취소
				alarm += " 좋아요를 취소했습니다.";
			}

			dao.updateHeartCount(heart.getIdx());	//좋아요 갯수 update
			int cnt = dao.hearts(heart.getIdx());	//변경된 갯수 가져오기

			HeartResponseDTO responseDTO = HeartResponseDTO.builder()
											.alarm(alarm)
											.hearts(cnt)
											.writer(heart.getWriter())
											.build();
			String result = objectMapper.writeValueAsString(responseDTO);	//직렬화 (자바 객체 ▶ json 문자열)

			resultMap.put("writer",heart.getWriter());
			resultMap.put("result",result);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return resultMap;
	}

	@Override   //로그인한 사용자가 좋아요 누른 글 목록
	public List<Integer> myHearts(String userid) {
		return dao.myHearts(userid);
	}
	
	@Override   //특정글의 좋아요 갯수 리턴
	public int hearts(int idx) {
		return dao.hearts(idx);
	}

}
