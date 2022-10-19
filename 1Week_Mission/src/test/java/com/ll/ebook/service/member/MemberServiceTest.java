package com.ll.ebook.service.member;

import com.ll.ebook.member.exception.SignupUsernameDuplicatedException;
import com.ll.ebook.member.service.MemberService;
import com.ll.ebook.post.DataNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원가입이 가능하다.")
    public void join() {
        memberService.join("user2","1234", "user2@email.com", "");
    }
}
