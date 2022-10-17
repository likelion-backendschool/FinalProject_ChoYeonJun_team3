package com.ll.ebook.keyword.model;

import com.ll.ebook.base.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Table(name="keywords")
public class KeywordEntity extends BaseEntity {

    /**
     * name : content
     * 마크다운 텍스트 저장
     */
    private String content;
}
