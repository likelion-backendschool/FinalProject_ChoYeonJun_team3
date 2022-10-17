package com.ll.ebook.hashtag;

import com.ll.ebook.hashtag.model.HashTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagRepository extends JpaRepository<HashTagEntity, Long> {
}
