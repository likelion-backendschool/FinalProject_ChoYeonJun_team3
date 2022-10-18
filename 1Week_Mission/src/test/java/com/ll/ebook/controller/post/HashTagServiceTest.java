package com.ll.ebook.controller.post;

import com.ll.ebook.hashtag.HashTagService;
import com.ll.ebook.hashtag.model.HashTagEntity;
import com.ll.ebook.keyword.KeywordService;
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

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
//@Transactional
@ActiveProfiles("test")
public class HashTagServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private HashTagService hashTagService;
    @Autowired
    private KeywordService keywordService;
    @Autowired
    private UserRepository userRepository;

    private final String username = "username";
    private final String keywords = "#개발 #자바";

    @BeforeEach
    void create(){
        saveUser();
        postService.write(username, "subject", "content", "#개발 #자바");
    }

    void saveUser(){
        userRepository.save(UserEntity.builder()
                .email("abc@gmail.com")
                .nickname("nickname")
                .username(username)
                .password("password")
                .build()) ;
    }

    @Test
    @DisplayName("저장된 해시태그 키워드 확인")
    void save() throws Exception {
        // WHEN
        PostEntity postEntity = postService.findByUserEntity(userRepository.findByUsername(username).get());
        List<HashTagEntity> hashTags = hashTagService.getHashTags(postEntity);

        //then
        for(HashTagEntity hashTag : hashTags){
            assertThat(hashTag.getKeywordEntity().getContent()).isEqualTo(keywordService.findById(hashTag.getKeywordEntity().getId()).getContent());
        }
    }


    @Test
    @DisplayName("글 작성, 삭제 후 해시태그도 같이 삭제")
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
