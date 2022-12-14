package com.ll.ebook.member;

import com.ll.ebook.member.exception.*;
import com.ll.ebook.member.form.MemberFindUsername;
import com.ll.ebook.member.form.MemberJoinForm;
import com.ll.ebook.member.form.MemberModifyInfoForm;
import com.ll.ebook.member.form.MemberModifyPasswordForm;
import com.ll.ebook.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;


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
     *
     * - 회원가입시 축하메일을 발송한다.
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

    /**
     * 회원 정보 수정
     * @param memberModifyInfoForm
     */
    @GetMapping("/modify")
    public void getModify(MemberModifyInfoForm memberModifyInfoForm){

    }

    @PostMapping("/modify")
    public void modify(Principal principal, @Valid MemberModifyInfoForm memberModifyInfoForm, BindingResult bindingResult){
        try{
            memberService.modify(principal.getName(), memberModifyInfoForm.getEmail(), memberModifyInfoForm.getNickname());
        }catch (SignupEmailDuplicatedException e) {
            bindingResult.reject("signupEmailDuplicated", e.getMessage());
            return ;
        }
    }

    /**
     * 비밀번호 변경
     * @param memberModifyPasswordForm
     */

    @GetMapping("/modifyPassword")
    public void getModifyPassword(MemberModifyPasswordForm memberModifyPasswordForm){

    }

    @PostMapping("/modifyPassword")
    public void modifyPassword(Principal principal, @Valid MemberModifyPasswordForm memberModifyPasswordForm, BindingResult bindingResult){
        try{
            memberService.modifyPassword(principal.getName(), memberModifyPasswordForm.getOldPassword(), memberModifyPasswordForm.getPassword(), memberModifyPasswordForm.getPasswordConfirm());
        }catch (PasswordAlreadyUseException e){
            bindingResult.reject("PasswordAlreadyUseException", e.getMessage());
            return ;
        }catch (PasswordConfirmNotMatchException e){
            bindingResult.reject("PasswordConfirmNotMatchException", e.getMessage());
            return ;
        }catch (PasswordNotCorrectException e){
            bindingResult.reject("PasswordNotCorrectException", e.getMessage());
            return ;
        }
    }

    @GetMapping("/login")
    public void login() {
        return ;
    }

    /**
     * 아이디 찾기
     *
     * - 가입 시 입력한 이메일 주소를 입력하여 아이디를 찾을 수 있다.
     * @param memberFindUsername
     */
    @GetMapping("/findUsername")
    public void getFindUsername(MemberFindUsername memberFindUsername){

    }

    @PostMapping("/findUsername")
    public void findUsername(@Valid MemberFindUsername memberFindUsername, Model model){
        String username = memberService.findUsernameByEmail(memberFindUsername.getEmail());

        model.addAttribute("username", username);
    }
}
