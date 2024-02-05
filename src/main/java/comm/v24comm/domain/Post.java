package comm.v24comm.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity // jpa가 사용하는 객체
public class Post {
    @Id // 테이블의 pk와 해당 필드 매핑
    @GeneratedValue(strategy = GenerationType.IDENTITY) // jpa가 알아서 pk값 생성
    private Long id;

    public Post() {
    }

    private String title; // 게시물 제목
    private String content; // 게시물 내용

    public Post(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
