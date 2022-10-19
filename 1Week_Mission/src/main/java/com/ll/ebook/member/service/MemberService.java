package com.ll.ebook.member.service;

import com.ll.ebook.email.EmailService;
import com.ll.ebook.member.MemberRepository;
import com.ll.ebook.member.exception.SignupEmailDuplicatedException;
import com.ll.ebook.member.exception.SignupUsernameDuplicatedException;
import com.ll.ebook.member.model.MemberEntity;
import com.ll.ebook.post.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
}
