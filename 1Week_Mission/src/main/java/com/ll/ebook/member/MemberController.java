package com.ll.ebook.member;

import com.ll.ebook.member.exception.SignupEmailDuplicatedException;
import com.ll.ebook.member.exception.SignupUsernameDuplicatedException;
import com.ll.ebook.member.form.MemberJoinForm;
import com.ll.ebook.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


/**
 * - 회원은 일반회원과 작가회원이 있다.
 * - 가입 직후는 일반회원이고, 작가명 등록을 하면 작가회원이 된다.
 * - 작가회원이 되면 상품(책)을 등록할 수 있다.
 * - 별개로 authLevel 의 값이 7이면 관리자의 역할도 수행할 수 있다.
 */

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입
     */
    @GetMapping("/join")
    public void getJoin(MemberJoinForm memberJoinForm){

    }

    @PostMapping("/join")
    public void join(@Valid  MemberJoinForm memberJoinForm, BindingResult bindingResult){
        if(!memberJoinForm.getPassword().equals(memberJoinForm.getPasswordConfirm())){
            bindingResult.rejectValue("password2", "passwordIncorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return;

        }
        try{
            memberService.join(memberJoinForm.getUsername(), memberJoinForm.getPassword(), memberJoinForm.getEmail(), memberJoinForm.getNickname());
        }catch (SignupEmailDuplicatedException e) {
            bindingResult.reject("signupEmailDuplicated", e.getMessage());
            return ;
        } catch (SignupUsernameDuplicatedException e) {
            bindingResult.reject("signupUsernameDuplicated", e.getMessage());
            return ;
        }
    }
}