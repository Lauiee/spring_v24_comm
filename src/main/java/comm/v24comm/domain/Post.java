package comm.v24comm.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity // jpa가 사용하는 객체
@Table(name ="discussion")
public class Post {
    @Id // 테이블의 pk와 해당 필드 매핑
    @GeneratedValue(strategy = GenerationType.IDENTITY) // jpa가 알아서 pk값 생성
    private Long id;

    public Post() {
    }

    private String title; // 게시물 제목
    private String content; // 게시물 내용
    private String writer; // 작성자
    public Post(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
