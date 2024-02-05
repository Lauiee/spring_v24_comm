package comm.v24comm.service;

import comm.v24comm.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

class PostServiceTest {

    private final PostRepository postRepository;
    private final PostService postService;

    PostServiceTest(PostRepository postRepository, PostService postService) {
        this.postRepository = postRepository;
        this.postService = postService;
    }


}