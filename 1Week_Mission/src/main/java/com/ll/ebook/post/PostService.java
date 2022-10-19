package com.ll.ebook.post;

import com.ll.ebook.hashtag.HashTagService;
import com.ll.ebook.post.model.PostDto;
import com.ll.ebook.post.model.entity.PostEntity;
import com.ll.ebook.member.MemberRepository;
import com.ll.ebook.member.model.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final HashTagService hashTagService;
    private final MemberRepository memberRepository;

    public List<PostDto> getList() {
        List<PostEntity> entities = postRepository.findAll();
        return entities.stream().map(PostDto::toDto).collect(Collectors.toList());
    }

    /**
     * TODO 받아온 username으로 userId를 가져와 setAuthorId(userId) 하도록 수정 필요
     * @param username
     * @param subject
     * @param content
     * @param keywords
     */
    public PostEntity write(String username, String subject, String content, String keywords) {

        MemberEntity memberEntity = memberRepository.findByUsername(username).orElseThrow(() -> new DataNotFoundException("no %s user not found,".formatted(username)));
        PostEntity postEntity = PostEntity.builder()
                .author(memberEntity)
                .content(content)
                .subject(subject)
                .build();

        postRepository.save(postEntity);

        hashTagService.applyHashTags(postEntity, keywords);

        return postEntity;
    }
    public PostEntity write(String username,  String subject, String content) {
        return write(username, subject, content, "");
    }
    public void modify(Long id, String subject, String content, String postKeywordsContents){
        PostEntity postEntity = findById(id);
        postEntity.setSubject(subject);
        postEntity.setContent(content);
        postRepository.save(postEntity);

        hashTagService.applyHashTags(postEntity, postKeywordsContents);
    }

    public void delete(Long id){
        postRepository.deleteById(id);
    }

    public List<PostEntity> findAll(){
        return postRepository.findAll();
    }

    public PostEntity findById(Long id){
        return postRepository.findById(id) .orElseThrow(() -> new DataNotFoundException("no %d post not found,".formatted(id)));
    }

    public PostEntity findByUserEntity(MemberEntity memberEntity){
        return postRepository.findByAuthor(memberEntity).orElseThrow(() -> new DataNotFoundException("no %s's post not found,".formatted(memberEntity.getUsername())));
    }
}
