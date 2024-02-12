package comm.v24comm.web.repository;

import comm.v24comm.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}

