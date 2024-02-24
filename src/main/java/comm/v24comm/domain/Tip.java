package comm.v24comm.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity // jpa가 사용하는 객체
@Table(name ="tip")
public class Tip {
    @Id // 테이블의 pk와 해당 필드 매핑
    @GeneratedValue(strategy = GenerationType.IDENTITY) // jpa가 알아서 pk값 생성
    private Long id;

    public Tip() {
    }

    private String title; // 게시물 제목
    private String content; // 게시물 내용
    private String writer; // 작성자
    private String imagePath; // 이미지의 경로를 저장할 필드 추가


    public Tip(String title, String content, String writer, String imagePath) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.imagePath = imagePath; // 이미지의 경로를 저장할 필드 추가
    }
}
