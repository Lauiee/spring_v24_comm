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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/login")
public class MemberController {

    private final MemberService memberService;

    @GetMapping // 로그인 화면
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginDto());
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

        return "redirect:/login";
    }

    @PostMapping // 로그인
    public String login(@Valid @ModelAttribute("loginForm") LoginDto loginForm, BindingResult bindingResult,
                        @RequestParam(name="redirectURL",defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "/members/loginForm";
        }
        Member loginMember = memberService.login(loginForm.getLoginId(),
                loginForm.getPassword());
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
        log.info("redirectURL= ", redirectURL);
        //redirectURL 적용
        return "redirect:" + redirectURL;
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
