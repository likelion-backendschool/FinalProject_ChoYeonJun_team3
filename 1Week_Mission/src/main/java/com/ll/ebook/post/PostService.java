package com.ll.ebook.post;

import com.ll.ebook.post.model.PostDto;
import com.ll.ebook.post.model.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
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
    public void write(String username, String subject, String content, String keywords) {

        PostEntity postEntity = PostEntity.builder()
                .authorId(1l)
                .content(content)
                .subject(subject)
                .build();

        postRepository.save(postEntity);
    }

    public void modify(Long id, String subject, String content, String postKeywordsContents){
        PostEntity postEntity = findById(id);
        postEntity.setSubject(subject);
        postEntity.setContent(content);
    }
    public List<PostEntity> findAll(){
        return postRepository.findAll();
    }

    public PostEntity findById(Long id){
        return postRepository.findById(id) .orElseThrow(() -> new DataNotFoundException("no %d post not found,".formatted(id)));
    }
}
