package com.ll.ebook.service.member;

import com.ll.ebook.member.MemberRepository;
import com.ll.ebook.member.exception.SignupEmailDuplicatedException;
import com.ll.ebook.member.exception.SignupUsernameDuplicatedException;
import com.ll.ebook.member.service.MemberService;
import com.ll.ebook.post.DataNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입이 가능하다.")
    public void join() {
        memberService.join("user2","1234", "user2@email.com", "");
    }

    @Test
    @DisplayName("중복된 아이디 예외 발생")
    public void joinUsernameDuplicatedException(){
        memberService.join("user2","1234", "user2@email.com", "");

        Exception exception = assertThrows(SignupUsernameDuplicatedException.class, () -> {
            memberService.join("user2","1234", "user1@email.com", "");
        });

        String expectedMessage = "이미 사용중인 username 입니다.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("중복된 이메일 예외 발생")
    public void joinEmailDuplicatedException(){
        memberService.join("user2","1234", "user2@email.com", "");

        Exception exception = assertThrows(SignupEmailDuplicatedException.class, () -> {
            memberService.join("user1","1234", "user2@email.com", "");
        });

        String expectedMessage = "이미 사용중인 email 입니다.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("저장된 회원의 이메일을 변경한다.")
    public void modifyEmail(){
        memberService.join("user2","1234", "user2@email.com", "");

        memberService.modify("user2", "user1@gmail.com", "");

        assertThat(memberRepository.findByUsername("user2").get().getEmail()).isEqualTo("user1@gmail.com");
    }

    @Test
    @DisplayName("저장된 회원 이메일 변경 후 중복된 이메일 예외 발생")
    public void joinEmailDuplicatedExceptionAfterModify(){
        memberService.join("user2","1234", "user2@email.com", "");
        memberService.join("user1","1234", "user1@email.com", "");
        Exception exception = assertThrows(SignupEmailDuplicatedException.class, () -> {
            memberService.modify("user2","user1@email.com", "");
        });

        String expectedMessage = "이미 사용중인 email 입니다.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
