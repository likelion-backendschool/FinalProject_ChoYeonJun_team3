package com.ll.ebook.service.post;

import com.ll.ebook.base.DataNotFoundException;
import com.ll.ebook.post.PostService;
import com.ll.ebook.post.model.entity.PostEntity;
import com.ll.ebook.member.MemberRepository;
import com.ll.ebook.member.model.MemberEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * 해시태그가 존재 하지 않는 글에 대한 테스트 코드
 *
 * ddl-auto 설정
 *   jpa:
 *     hibernate:
 *       ddl-auto: create
 */

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class PostServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    private MemberRepository memberRepository;

    private final String username = "username";

    /**
     * 매 테스트마다 사용자 새로 생성 및 글 작성
     */
    @BeforeEach
    void create(){
        saveUser();
        postService.write(username, "subject", "content");
    }

    /**
     * 임시 사용자 저장 테스트 코드
     * post의 author 로 사용하기 위함
     */
    @Test
    void saveUser(){
        memberRepository.save(MemberEntity.builder()
                .email("abc@gmail.com")
                .nickname("nickname")
                .username(username)
                .password("password")
                .build()) ;
    }

    @Test
    @DisplayName("글 작성, 게시글 리스트 확인")
    void getList() throws Exception {
        // WHEN
        int before = postService.findAll().size();
        postService.write(username, "subject", "content");
//        userRepository.save(getUser());
//        postService.write("username", "subject", "content");
        //then
        assertThat(postService.findAll().size()).isGreaterThan(before);

    }

    @Test
    @DisplayName("글 작성, 수정 후 확인")
    void modify() throws Exception {
        // WHEN
//        postService.write("username", "subject", "content");
        PostEntity post = postService.findByUserEntity(memberRepository.findByUsername(username).get());
        postService.modify(post.getId(), "new subject", "new content", "new keywords");

        //then
        PostEntity postEntity = postService.findById(post.getId());

        assertThat(postEntity.getSubject()).isEqualTo("new subject");
        assertThat(postEntity.getContent()).isEqualTo("new content");

    }
    @Test
    @DisplayName("글 작성, 삭제 후 확인")
    void delete() throws Exception {
        // when
//        postService.write("username", "subject", "content");
        PostEntity postEntity = postService.findByUserEntity(memberRepository.findByUsername(username).get());
        postService.delete(postEntity.getId());

        // then
        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            postService.findById(postEntity.getId());
        });

        String expectedMessage = "no %d post not found,".formatted(postEntity.getId());
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));


    }

}
