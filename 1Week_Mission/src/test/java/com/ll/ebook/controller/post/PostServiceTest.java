package com.ll.ebook.controller.post;

import com.ll.ebook.post.DataNotFoundException;
import com.ll.ebook.post.PostService;
import com.ll.ebook.post.model.entity.PostEntity;
import com.ll.ebook.user.UserRepository;
import com.ll.ebook.user.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class PostServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    private final String username = "username";
    @BeforeEach
    void create(){
        saveUser();
        postService.write(username, "subject", "content");
    }
    @Test
    void saveUser(){
        userRepository.save(UserEntity.builder()
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
        PostEntity post = postService.findByUserEntity(userRepository.findByUsername(username).get());
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
        PostEntity postEntity = postService.findByUserEntity(userRepository.findByUsername(username).get());
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
