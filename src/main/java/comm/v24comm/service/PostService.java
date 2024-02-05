package comm.v24comm.service;

import comm.PostUpdateDto;
import comm.v24comm.domain.Post;
import comm.v24comm.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post createPost(Post post) {
        // 게시물 저장 로직
        return postRepository.save(post);
    }

    public Post getPost(Long postId) {
        // 게시물 조회 로직
        return postRepository.findById(postId).orElse(null);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void updatePost(Long postId, PostUpdateDto param) {
        // 게시물 수정 로직
        Post findPost = postRepository.findById(postId).orElseThrow();

        findPost.setTitle(param.getTitle());
        findPost.setContent(param.getContent());
    }

    public void deletePost(Long postId){
        postRepository.deleteById(postId);
    }
}
