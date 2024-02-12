package comm.v24comm.web.controller;

import comm.v24comm.domain.Member;
import comm.v24comm.web.LoginDto;
import comm.v24comm.web.SessionConst;
import comm.v24comm.web.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/login")
public class MemberController {

    private final MemberService memberService;

    @GetMapping // 로그인 화면
    public String loginForm() {
        return "/members/loginForm";
    }

    @GetMapping("/add") // 회원가입 화면
    public String addForm() {
        return "/members/addMemberForm";
    }

    @PostMapping("/add") // 회원가입
    public String add(@ModelAttribute("member") Member member) {
        Member newMember = new Member();

        newMember.setLoginId(member.getLoginId());
        newMember.setPassword(member.getPassword());
        newMember.setName(member.getName());

        memberService.save(newMember);

        return "/members/loginForm";
    }

    @PostMapping // 로그인
    public String login(@Valid @ModelAttribute("form") LoginDto form, BindingResult
            bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "/members/loginForm";
        }
        Member loginMember = memberService.login(form.getLoginId(),
                form.getPassword());
        log.info("login? {}", loginMember);

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "/members/loginForm";
        }

        //로그인 성공 처리
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        //세션을 삭제한다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        log.info("세션 제거");
        return "redirect:/";
    }

}
