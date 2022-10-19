package com.ll.ebook.member.service;

import com.ll.ebook.member.MemberRepository;
import com.ll.ebook.member.exception.SignupEmailDuplicatedException;
import com.ll.ebook.member.exception.SignupUsernameDuplicatedException;
import com.ll.ebook.member.model.MemberEntity;
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
    public void join(String username, String password, String email, String nickname) {

        MemberEntity member = MemberEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .nickname(nickname)
                .build();
        memberRepository.save(member);

    }
}
