package comm;

import lombok.Data;

@Data
public class PostUpdateDto {

    private String title; // 게시물 제목
    private String content; // 게시물 내용

    public PostUpdateDto() {
    }

    public PostUpdateDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
