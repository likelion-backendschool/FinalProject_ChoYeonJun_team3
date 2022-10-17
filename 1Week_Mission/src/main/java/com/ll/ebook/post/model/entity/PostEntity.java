package com.ll.ebook.post.model.entity;

import com.ll.ebook.base.entity.BaseEntity;
import com.ll.ebook.post.model.PostDto;
import com.ll.ebook.user.model.UserEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Table(name = "posts")
public class PostEntity extends BaseEntity {

    /**
     * HashTag 에서 UserEntity의 id를 참고하기 위해서는 @Column을 이용해 외래키 필드를 따로 지정해주어야 한다.
     * 외래키가 2중으로 타고 들어가기 때문
     *
     * @Column(name="author_id")
     */
    @ManyToOne
    @JoinColumn(name="author_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity author;

    @Column(name="author_id")
    private Long authorId;

    @Setter
    private String subject;

    /**
     * name : content
     * 마크다운 텍스트 저장
     */
    @Setter
    private String content;


    /**
     * name : contentHtml
     * 토스트 에디터의 렌더링 결과, Html 저장
     */
    @Setter
    @Column(name="content_html")
    private String contentHtml;

    public LocalDateTime getCreatedAt(){
        return super.getCreateDate();
    }

    public LocalDateTime getModifiedAt(){
        return super.getModifyDate();
    }

    public static PostEntity toEntity(PostDto dto){

        return PostEntity.builder()
                .author(dto.getAuthor())
                .subject(dto.getSubject())
                .content(dto.getContent())
                .contentHtml(dto.getContentHtml())
                .build();
    }
}
