package org.iclass.mvc.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeartResponseDTO {
    //좋아요 결과로 돌려줄 메시지와 좋아요 갯수.
    private String writer;  //글 작성자 (글 주인)
    private String alarm;   //메시지
    private int hearts;     //현재 좋아요 갯수
}

// 3-b. 좋아요 응답 dto : 어떤 기능의 구현인지에 따라 달라질 수 있는 필드들이다.
