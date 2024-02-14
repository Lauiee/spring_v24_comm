package comm.v24comm.web.controller;

import comm.PostUpdateDto;
import comm.v24comm.domain.Member;
import comm.v24comm.domain.Post;
import comm.v24comm.web.argumentresolver.Login;
import comm.v24comm.web.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/discussion")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping
    public String findAll(@Login Member loginMember, Model model){
        List<Post> postList = postService.findAll();
        model.addAttribute("member", loginMember);
        model.addAttribute("postList", postList);
        return "discussion-posts"; // 게시판 글 조회
    }


    @GetMapping("/{postId}") // 상세 게시글 조회
    public String findById(@Login Member loginMember,@PathVariable("postId") Long postId, Model model){
        Post findPost = postService.getPost(postId);
        log.info("게시글 id: "+findPost.getId());
        log.info("게시글 제목: "+findPost.getTitle());
        log.info("게시글 내용: "+findPost.getContent());
        log.info("게시글 작성자: "+findPost.getWriter());

        model.addAttribute("member", loginMember);
        model.addAttribute("post", findPost);
        return "discussion-post";
    }

    @GetMapping("/save") // 게시글 작성 버튼 - 게시글 작성 시작
    public String savePostForm(@Login Member loginMember, Model model){
        model.addAttribute("member", loginMember);
        log.info("작성자: ", loginMember.getName());
        return "save"; // 게시글 작성 폼으로 이동
    }

    @GetMapping("/edit/{postId}") // 게시글 수정 폼
    public String editPost(@Login Member loginMember,@PathVariable("postId") Long postId, Model model) {
        System.out.println("Received postId: " + postId); // postId 출력
        Post findPost = postService.getPost(postId);
        model.addAttribute("member", loginMember);
        model.addAttribute("findPost", findPost);
        return "discussion-editForm";
    }

    @PostMapping("/edit/{postId}") // 게시글 수정
    public String editPost(@Login Member loginMember,@PathVariable("postId") Long postId, Model model,
                           @ModelAttribute("findPost") PostUpdateDto findPost){
        log.info("제목:"+findPost.getTitle());
        log.info("내용:"+findPost.getContent());
        postService.updatePost(postId, findPost);
        Post updated = postService.getPost(postId);
        log.info("변경 게시글 id: "+updated.getId());
        log.info("변경 게시글 제목: "+updated.getTitle());
        log.info("변경 게시글 내용: "+updated.getContent());
        model.addAttribute("member", loginMember);
        return "redirect:/discussion/" + postId;
    }

    @PostMapping("/save") // 게시글 저장
    public String savePost(@Login Member loginMember, @ModelAttribute("post") Post post, Model model) {
        Post newPost = new Post();
        model.addAttribute("member", loginMember);
        newPost.setTitle(post.getTitle());
        newPost.setContent(post.getContent());
        newPost.setWriter(loginMember.getName());
        // PostController의 API 엔드포인트 호출
        postService.createPost(newPost);
        // 리다이렉트 또는 다른 작업 수행
        return "redirect:/discussion";
    }

    @GetMapping("/delete/{postId}")
    public String deletePost(@Login Member loginMember, @PathVariable("postId") Long postId, Model model){
        postService.deletePost(postId);
        model.addAttribute("member", loginMember);
        return "redirect:/discussion";
    }
}
