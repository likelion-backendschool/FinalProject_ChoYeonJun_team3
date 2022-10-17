package com.ll.ebook.post.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    Long id;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name="author_id")
    Long authorId;

    String subject;

    /**
     * name : content
     * 마크다운 텍스트 저장
     */
    String content;


    /**
     * name : contentHtml
     * 토스트 에디터의 렌더링 결과, Html 저장
     */
    String contentHtml;
}
