package com.ll.ebook.controller.post;

import com.ll.ebook.post.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class PostServiceTest {


    @Autowired
    private PostService postService;

    @Test
    @DisplayName("글 작성, 게시글 리스트 확인")
    void getList() throws Exception {
        // WHEN
        postService.write("user", "subject", "content", "keywords");

        //then
        assertThat(postService.findAll().get(0).getId()).isEqualTo(1l);

    }
}
