package com.ll.ebook.hashtag;

import com.ll.ebook.hashtag.model.HashTagEntity;
import com.ll.ebook.post.model.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTagEntity, Long> {
    Optional<HashTagEntity> findByPostEntityAndKeywordEntityId(PostEntity postEntity, Long keywordEntity_id);

    List<HashTagEntity> findAllByPostEntity(PostEntity postEntity);
}
