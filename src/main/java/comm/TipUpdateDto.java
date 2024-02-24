package comm;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TipUpdateDto {

    private Long id;
    private String title; // 게시물 제목
    private String content; // 게시물 내용
    private String imagePath;
    public TipUpdateDto() {
    }

    public TipUpdateDto(Long id ,String title, String content, String imagePath) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
    }
}
