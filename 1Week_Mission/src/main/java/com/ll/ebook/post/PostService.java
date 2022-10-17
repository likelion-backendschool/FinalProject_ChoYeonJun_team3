package com.ll.ebook.post;

import com.ll.ebook.post.model.PostDto;
import com.ll.ebook.post.model.PostEntity;
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
}
