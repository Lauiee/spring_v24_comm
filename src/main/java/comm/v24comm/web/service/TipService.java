package comm.v24comm.web.service;

import comm.PostUpdateDto;
import comm.TipUpdateDto;
import comm.v24comm.domain.Post;
import comm.v24comm.domain.Tip;
import comm.v24comm.web.repository.TipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TipService {

    private final TipRepository tipRepository;
    private final String imageUploadPath = "C:/SpringStudy/v24comm/src/main/resources/static/Img"; // 이미지 업로드 경로 설정


    public Tip createTip(Tip tip) {
        // 게시물 저장 로직
        return tipRepository.save(tip);
    }

    public Tip getTip(Long tipId) {
        // 게시물 조회 로직
        return tipRepository.findById(tipId).orElse(null);
    }

    public List<Tip> findAll() {
        return tipRepository.findAll();
    }

    public void updateTip(Long tipId, TipUpdateDto param) {
        // 게시물 수정 로직
        Tip findTip = tipRepository.findById(tipId).orElseThrow();

        findTip.setTitle(param.getTitle());
        findTip.setContent(param.getContent());
        findTip.setImagePath(param.getImagePath());
    }

    public void deleteTip(Long postId) {
        tipRepository.deleteById(postId);
    }

    // 이미지 업로드 로직을 추가할 수 있습니다.
    public String uploadImage(MultipartFile file) {
        try {
            // 파일 이름을 UUID로 생성하여 중복을 피합니다.
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // 업로드할 디렉토리 경로 설정
            Path uploadPath = Paths.get(imageUploadPath);
            log.info("uploadPath= {}", uploadPath);
            // 디렉토리가 존재하지 않으면 생성합니다.
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 파일을 업로드할 경로 설정
            Path filePath = uploadPath.resolve(fileName);
            log.info("filePath= {}", filePath);

            // 파일을 저장합니다.
            Files.copy(file.getInputStream(), filePath);

            // 저장된 파일의 경로를 반환합니다.
            return filePath.toString();
        } catch (IOException ex) {
            log.error("이미지 업로드 중 오류 발생: {}", ex.getMessage());
            throw new RuntimeException("이미지를 업로드하는 중에 오류가 발생했습니다.");
        }
    }
}
