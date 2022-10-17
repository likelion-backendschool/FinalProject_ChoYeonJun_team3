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

    public void write(String username, String subject, String content, String keywords) {

        PostEntity postEntity = PostEntity.builder()
                .authorId(1l)
                .content(content)
                .subject(subject)
                .build();

        postRepository.save(postEntity);
    }

    public List<PostEntity> findAll(){
        return postRepository.findAll();
    }
}
