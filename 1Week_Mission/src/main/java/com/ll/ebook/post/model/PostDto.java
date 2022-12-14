package com.ll.ebook.post.model;

import com.ll.ebook.post.model.entity.PostEntity;
import com.ll.ebook.member.model.MemberEntity;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private MemberEntity author;

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
    private String contentHtml;

    public static PostDto toDto(PostEntity entity){

        return PostDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .author(entity.getAuthor())
                .subject(entity.getSubject())
                .content(entity.getContent())
                .contentHtml(entity.getContentHtml())
                .build();
    }

}
