package comm.v24comm;

import comm.v24comm.domain.Member;
import comm.v24comm.web.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        Member member = new Member();
        member.setLoginId("123");
        member.setPassword("123");
        member.setName("테스터");
        memberRepository.save(member);
    }
}
