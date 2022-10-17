package com.ll.ebook.post.model.entity;

import com.ll.ebook.post.model.PostDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    @Column(name="author_id")
    private Long authorId;

    private String subject;

    /**
     * name : content
     * 마크다운 텍스트 저장
     */
    private String content;


    /**
     * name : contentHtml
     * 토스트 에디터의 렌더링 결과, Html 저장
     */
    @Column(name="content_html")
    private String contentHtml;

    public static PostEntity toEntity(PostDto dto){

        return PostEntity.builder()
                .authorId(dto.getAuthorId())
                .subject(dto.getSubject())
                .content(dto.getContent())
                .contentHtml(dto.getContentHtml())
                .build();
    }
}
