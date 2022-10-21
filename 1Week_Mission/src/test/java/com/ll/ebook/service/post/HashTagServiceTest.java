package com.ll.ebook.service.post;

import com.ll.ebook.hashtag.HashTagService;
import com.ll.ebook.hashtag.model.HashTagEntity;
import com.ll.ebook.keyword.KeywordService;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 해시태그가 존재 하는 글에 대한 테스트 코드
 *
 * ddl-auto 설정
 *   jpa:
 *     hibernate:
 *       ddl-auto: create
 */
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
    private MemberRepository memberRepository;

    private final String username = "username";
    private final String keywords = "#개발 #자바";

    /**
     * 매 테스트마다 사용자 새로 생성 및 글 작성
     */
    @BeforeEach
    void create(){
        saveUser();
        postService.write(username, "subject", "content", "#개발 #자바");
    }

    /**
     * 임시 사용자 저장 테스트 코드
     * post의 author 로 사용하기 위함
     *
     * User 작업 들어가면서 수정 예정
     */
    void saveUser(){
        memberRepository.save(MemberEntity.builder()
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
        PostEntity postEntity = postService.findByUserEntity(memberRepository.findByUsername(username).get());
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
