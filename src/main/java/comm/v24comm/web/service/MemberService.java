package comm.v24comm.web.service;

import comm.v24comm.domain.Member;
import comm.v24comm.web.LoginDto;
import comm.v24comm.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(Member member){ // 회원 등록
        return memberRepository.save(member);
    }

    public Member findById(Long memberId){ // 회원 조회
       return memberRepository.findById(memberId).orElse(null);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }

    public Member login(String loginId, String password) {
        return findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    public List<Member> findAll(){ // 회원 전체 조회
        return memberRepository.findAll();
    }

    public void delete( Long memberId){ // 회원 탈퇴
        Member target = findById(memberId);
        memberRepository.delete(target);
    }
}
