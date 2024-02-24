package comm.v24comm.web.controller;

import comm.PostUpdateDto;
import comm.TipUpdateDto;
import comm.v24comm.domain.Member;
import comm.v24comm.domain.Post;
import comm.v24comm.domain.Tip;
import comm.v24comm.web.argumentresolver.Login;
import comm.v24comm.web.service.TipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/tip")
@RequiredArgsConstructor
@Slf4j
public class TipController {

    private final TipService tipService;

    @GetMapping
    public String findAll(@Login Member loginMember, Model model){
        List<Tip> tipList = tipService.findAll();
        model.addAttribute("member", loginMember);
        model.addAttribute("tipList", tipList);
        return "tip/tip-posts"; // 게시판 글 조회
    }


    @GetMapping("/{tipId}") // 상세 게시글 조회
    public String findById(@Login Member loginMember,@PathVariable("tipId") Long tipId, Model model){
        Tip findTip = tipService.getTip(tipId);
        model.addAttribute("member", loginMember);
        model.addAttribute("tip", findTip);
        return "tip/tip-post";
    }

    @GetMapping("/save") // 게시글 작성 버튼 - 게시글 작성 시작
    public String savePostForm(@Login Member loginMember, Model model){
        model.addAttribute("member", loginMember);
        return "tip/save"; // 게시글 작성 폼으로 이동
    }

    @GetMapping("/edit/{tipId}") // 게시글 수정 폼
    public String editPost(@Login Member loginMember,@PathVariable("tipId") Long tipId, Model model) {
        Tip findTip = tipService.getTip(tipId);
        log.info("나 호출 됨?");
        model.addAttribute("member", loginMember);

        TipUpdateDto findTipDto = new TipUpdateDto();

        findTipDto.setId(findTip.getId());
        findTipDto.setTitle(findTip.getTitle());
        findTipDto.setContent(findTip.getContent());
        findTipDto.setImagePath(findTip.getImagePath());
        model.addAttribute("findTip", findTipDto);
        return "tip/tip-editForm";
    }

    @PostMapping("/edit/{tipId}") // 게시글 수정
    public String editPost(@Login Member loginMember, @PathVariable("tipId") Long tipId, Model model,
                           @ModelAttribute("findTip") @Valid TipUpdateDto findTip,
                           @RequestParam("imageFile") MultipartFile imageFile
                           ){
        model.addAttribute("member", loginMember);

        // 이미지 파일 업로드 처리
        if (imageFile != null && !imageFile.isEmpty()) {
            // 이미지 파일을 저장하고, 파일 경로를 tipUpdateDto에 설정
            String imagePath = tipService.uploadImage(imageFile);
            findTip.setImagePath(imagePath);
            log.info("이미지 저장했어요");
        }
        tipService.updateTip(tipId, findTip);
        return "redirect:/tip/" + tipId;
    }

    @PostMapping("/save") // 게시글 저장
    public String savePost(@Login Member loginMember,
                           @ModelAttribute("tip") Tip tip,
                           @RequestParam("imageFile") MultipartFile imageFile,
                           Model model) {
        try {
            Tip newTip = new Tip();
            model.addAttribute("member", loginMember);
            newTip.setTitle(tip.getTitle());
            newTip.setContent(tip.getContent());
            newTip.setWriter(loginMember.getName());

            // 이미지 업로드
            String imagePath = tipService.uploadImage(imageFile);
            log.info("imagePath = {}", imagePath);
            newTip.setImagePath(imagePath);

            // 게시글 저장
            tipService.createTip(newTip);

            // 리다이렉트 또는 다른 작업 수행
            return "redirect:/tip";
        } catch (Exception e) {
            // 예외 처리
            model.addAttribute("error", "게시글을 저장하는 중에 오류가 발생했습니다.");
            return "error-page"; // 오류 페이지로 리다이렉트 또는 다른 처리를 수행할 수 있습니다.
        }
    }

    @GetMapping("/delete/{tipId}")
    public String deletePost(@Login Member loginMember, @PathVariable("tipId") Long tipId, Model model){
        tipService.deleteTip(tipId);
        model.addAttribute("member", loginMember);
        return "redirect:/tip";
    }
}
