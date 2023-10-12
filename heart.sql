-- 이미 있는 테이블 : 이미지 게시판
CREATE TABLE gallery(
                        idx number(5) PRIMARY KEY,
                        title varchar2(50) NOT NULL,
                        filenames varchar2(200) NOT NULL,
                        createAt timestamp DEFAULT sysdate,
                        hearts number(5) DEFAULT 0,
                        writer varchar2(50)
);

CREATE SEQUENCE gal_idx_seq;


-- 새로 만들 테이블 : userid 가 idx 번호 글 좋아요 기록
CREATE TABLE tbl_hearts (   
	userid varchar2(50) NOT NULL,    
	idx number(5) NOT NULL,
    status varchar2(5) DEFAULT 'read'   --임시 컬럼(상태 체크) : 좋아요를 받은 글 작성자가 로그인을 한 상태이면 'read'
                                        --                  :                       로그인 상태가 아니면 'none'
);

-- 테이블만 만들고 아래 sql 실행은 참고만 합니다
-- 좋아요 저장
INSERT INTO tbl_hearts(userid,gidx) VALUES ('twice',1);

-- 게시글 1번에 사용자 twice 가 좋아요 삭제(취소)
DELETE FROM tbl_hearts WHERE idx = 1 AND userid='twice'

-- idx 글의 좋아요 갯수 수정하기
UPDATE gallery
SET hearts = (SELECT count(*)
              FROM tbl_hearts
              GROUP BY idx
              HAVING idx = 1);

--게시글 idx 의 좋아요 갯수
SELECT count(*)
FROM tbl_hearts
GROUP BY idx;



