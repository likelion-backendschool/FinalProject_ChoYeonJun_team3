package com.ll.ebook.member.service;

import com.ll.ebook.email.EmailService;
import com.ll.ebook.member.MemberRepository;
import com.ll.ebook.member.exception.*;
import com.ll.ebook.member.model.MemberEntity;
import com.ll.ebook.post.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final EmailService emailService;
    public void join(String username, String password, String email, String nickname) {

        MemberEntity member = MemberEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .nickname(nickname)
                .build();
        try{
            memberRepository.save(member);
        }catch (DataIntegrityViolationException e) {
            if (memberRepository.existsByUsername(username)) {
                throw new SignupUsernameDuplicatedException("이미 사용중인 username 입니다.");
            } else {
                throw new SignupEmailDuplicatedException("이미 사용중인 email 입니다.");
            }
        }

        emailService.sendWelcomeMessage(member.getEmail());
    }

    public void modify(String username, String email, String nickname) {
        MemberEntity member = memberRepository.findByUsername(username).orElseThrow(() -> new DataNotFoundException("member using %s email not found".formatted(email)));

        member.setEmail(email);
        member.setNickname(nickname);

        try{
            memberRepository.save(member);
        }catch (DataIntegrityViolationException e) {
            if (memberRepository.existsByEmail(email)) {
                throw new SignupEmailDuplicatedException("이미 사용중인 email 입니다.");
            }
        }
    }

    private MemberEntity findByUsername(String username){
        return memberRepository.findByUsername(username).orElseThrow(() -> new DataNotFoundException("member %s  not found".formatted(username)));
    }

    public String getCurrPassword(String username){
        MemberEntity member = findByUsername(username);

        return member.getPassword();
    }

    public String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

    public boolean isSamePassword(String password1, String password2){
        return password1.equals(password2);
    }

    public void modifyPassword(String username, String oldPassword, String password, String passwordConfirm) {
        MemberEntity member = findByUsername(username);
        String currPassword = getCurrPassword(username);
        if (!isSamePassword(password, passwordConfirm))
            throw new PasswordConfirmNotMatchException("입력한 새로운 두 비밀번호가 서로 다릅니다.");
        if(!passwordEncoder.matches(oldPassword, currPassword))
            throw new PasswordNotCorrectException("패스워드가 올바르지 않습니다.");

        if(isSamePassword(password, oldPassword))
            throw new PasswordAlreadyUseException("새로운 패스워드가 현재 사용중인 패스워드와 같습니다.");

        member.setPassword(encodePassword(password));

        memberRepository.save(member);
    }
}
