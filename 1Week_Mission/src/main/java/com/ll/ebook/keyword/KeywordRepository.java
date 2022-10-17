package com.ll.ebook.keyword;

import com.ll.ebook.keyword.model.KeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<KeywordEntity, Long> {
}
